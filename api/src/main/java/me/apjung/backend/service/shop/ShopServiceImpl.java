package me.apjung.backend.service.shop;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.api.exception.ShopFileUploadException;
import me.apjung.backend.api.exception.ShopNotFoundException;
import me.apjung.backend.domain.base.ViewStats;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopSafeLevel;
import me.apjung.backend.domain.shop.ShopSafeLog;
import me.apjung.backend.domain.tag.Tag;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.vo.Thumbnail;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.file.FileRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.repository.shop.ShopSafeLogRepository;
import me.apjung.backend.repository.tag.TagRepository;
import me.apjung.backend.service.file.FileService;
import me.apjung.backend.service.file.dto.SavedFile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final TagRepository tagRepository;
    private final ShopRepository shopRepository;
    private final ShopSafeLogRepository shopSafeLogRepository;

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
                    .viewStats(new ViewStats())
                    .safeAt(LocalDateTime.now())
                    .safeLevel(Optional.ofNullable(request.getSafeLevel()).orElse(ShopSafeLevel.FAKE))
                    .build());

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

    @Override
    public ShopResponse.GET get(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(ShopNotFoundException::new);

        return ShopResponse.GET.builder()
                .id(shop.getId())
                .name(shop.getName())
                .overview(shop.getOverview())
                .url(shop.getUrl())
                .thumbnail(Thumbnail.from(shop.getThumbnail()))
                .build();
    }

    @Override
    @Transactional
    public void safe(Long shopId, ShopSafeLevel level) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();

        LocalDateTime now = LocalDateTime.now();
        shop.setSafeAt(now);
        shop.setSafeLevel(level);

        ShopSafeLog log = ShopSafeLog.builder()
                .shop(shop)
                .safeAt(now)
                .safeLevel(level)
                .build();

        shopRepository.save(shop);
        shopSafeLogRepository.save(log);
    }
}
