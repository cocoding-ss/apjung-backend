package me.apjung.backend.service.shop;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.api.exception.ShopFileUploadException;
import me.apjung.backend.api.exception.ShopNotFoundException;
import me.apjung.backend.domain.Base.ViewStats;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.vo.Thumbnail;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.file.FileRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.service.file.FileService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final FileService fileService;
    private final ShopRepository shopRepository;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public ShopResponse.Create create(ShopRequest.Create request) {
        try {
            final var savedFile = fileService.upload(request.getThumbnail());
            final var file = fileRepository.save(File.create(savedFile));
            final var shop = shopRepository.save(Shop.builder()
                    .name(request.getName())
                    .overview(request.getOverview())
                    .url(request.getUrl())
                    .thumbnail(file)
                    .viewStats(new ViewStats())
                    .build());

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
}
