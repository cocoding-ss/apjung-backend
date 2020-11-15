package me.apjung.backend.service.Shop;

import lombok.AllArgsConstructor;
import me.apjung.backend.domain.Base.ViewStats;
import me.apjung.backend.domain.File.File;
import me.apjung.backend.domain.Shop.Shop;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.vo.Thumbnail;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.File.FileRepository;
import me.apjung.backend.repository.Shop.ShopRepository;
import me.apjung.backend.service.File.FileService;
import me.apjung.backend.service.File.dto.SavedFile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {
    private FileService fileService;
    private ShopRepository shopRepository;
    private FileRepository fileRepository;

    @Override
    @Transactional
    public ShopResponse.Create create(ShopRequest.Create request) {
        Shop shop = null;
        try {
            SavedFile savedFile = fileService.upload(request.getThumbnail());
            shop = shopRepository.save(Shop.builder()
                    .name(request.getName())
                    .overview(request.getOverview())
                    .url(request.getUrl())
                    .thumbnail(fileRepository.save(File.create(savedFile)))
                    .viewStats(new ViewStats())
                    .build()
            );

        } catch (IOException e) {
            // ignore
        }

        return ShopResponse.Create.builder().id(Optional.ofNullable(shop).orElseThrow().getId()).build();
    }

    @Override
    public ShopResponse.GET get(Long shop_id) {
        Shop shop = shopRepository.findById(shop_id).orElseThrow();

        return ShopResponse.GET.builder()
                .id(shop.getId())
                .name(shop.getName())
                .overview(shop.getOverview())
                .thumbnail(
                        Thumbnail.builder()
                            .name(shop.getThumbnail().getName())
                            .extension(shop.getThumbnail().getExtension())
                            .originalName(shop.getThumbnail().getOriginalName())
                            .originalExtension(shop.getThumbnail().getOriginalExtension())
                            .prefix(shop.getThumbnail().getPrefix())
                            .publicUrl(shop.getThumbnail().getPublicUrl())
                            .size(shop.getThumbnail().getSize())
                            .width(shop.getThumbnail().getWidth())
                            .height(shop.getThumbnail().getHeight())
                            .build()
                ).build();
    }
}
