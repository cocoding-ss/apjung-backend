package me.apjung.backend.service.shop;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.api.exception.ShopFileUploadException;
import me.apjung.backend.api.exception.ShopNotFoundException;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopViewLog;
import me.apjung.backend.domain.shop.ShopViewStats;
import me.apjung.backend.domain.tag.Tag;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.vo.Thumbnail;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.file.FileRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.repository.shop_view_stats.ShopViewStatsRepository;
import me.apjung.backend.repository.shopviewlog.ShopViewLogRepository;
import me.apjung.backend.repository.tag.TagRepository;
import me.apjung.backend.service.file.FileService;
import me.apjung.backend.service.file.dto.SavedFile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final FileService fileService;
    private final ShopRepository shopRepository;
    private final FileRepository fileRepository;
    private final TagRepository tagRepository;
    private final ShopViewStatsRepository shopViewStatsRepository;
    private final ShopViewLogRepository shopViewLogRepository;

    @Override
    @Transactional
    public ShopResponse.Create create(ShopRequest.Create request) {
        try {
            final SavedFile savedFile = fileService.upload(request.getThumbnail());
            final File file = fileRepository.save(File.create(savedFile));
            final Shop shop = shopRepository.save(Shop.builder()
                    .name(request.getName())
                    .overview(request.getOverview())
                    .url(request.getUrl())
                    .thumbnail(file)
//                    .viewStats(new ViewStats())
                    .build());
            // TODO: 2020-12-11 ViewStats 처리

            for (String tagName : request.getTags()) {
                Tag tag = tagRepository.findTagByName(tagName).orElse(Tag.builder().icon(null).name(tagName).build());
                shop.addTag(tag);
            }

            return ShopResponse.Create.builder()
                    .id(shop.getId())
                    .build();
        } catch (IOException e) {
            throw new ShopFileUploadException();
        }
    }

    @Transactional
    @Override
    public ShopResponse.GET get(Long shopId, User user) {
        final var shop = shopRepository.findById(shopId)
                .orElseThrow(ShopNotFoundException::new);
        final var shopViewStats = shopViewStatsRepository.findShopViewStatsByShopId(shopId)
                .orElseThrow(); // 뭔가 예기치 못한 오류(shop을 생성할때 자동으로 shop_view_stats를 생성하기 때문)

        final var today = LocalDate.now();

        final var shopViewLog = shopViewLogRepository.findShopViewLogByUserIdAndShopIdAndAccessedAt(user.getId(), shopId, LocalDate.now())
                .orElse(ShopViewLog.builder()
                        .shop(shop)
                        .user(user)
                        .accessedAt(today)
                        .build());

        processShopVisit(shopViewStats, shopViewLog);

        shopViewLogRepository.save(shopViewLog);
        shopViewStatsRepository.save(shopViewStats);

        return ShopResponse.GET.builder()
                .id(shop.getId())
                .name(shop.getName())
                .overview(shop.getOverview())
                .url(shop.getUrl())
                .thumbnail(Thumbnail.from(shop.getThumbnail()))
                .build();
    }

    private void processShopVisit(ShopViewStats shopViewStats, ShopViewLog shopViewLog) {
        if (shopViewLog.getAccessedCount() > 0) {
            shopViewLog.increaseAccessedCount();
            shopViewStats.visit();
        } else {
            shopViewStats.firstVisit();
        }
    }
}
