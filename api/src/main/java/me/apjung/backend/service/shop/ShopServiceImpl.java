package me.apjung.backend.service.Shop;

import lombok.AllArgsConstructor;
import me.apjung.backend.domain.Base.ViewStats;
import me.apjung.backend.domain.File.File;

import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.tag.Tag;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.vo.Thumbnail;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.File.FileRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.repository.tag.TagRepository;
import me.apjung.backend.service.File.FileService;
import me.apjung.backend.service.File.dto.SavedFile;
import me.apjung.backend.service.shop.ShopService;
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
    private TagRepository tagRepository;

    @Override
    @Transactional
    public ShopResponse.Create create(ShopRequest.Create request) {
        Shop shop = null;
        try {
            SavedFile savedFile = fileService.upload(request.getThumbnail());
            shop = Shop.builder()
                    .name(request.getName())
                    .overview(request.getOverview())
                    .url(request.getUrl())
                    .thumbnail(fileRepository.save(File.create(savedFile)))
                    .viewStats(new ViewStats())
                    .build();

            for (String name : request.getTags()) {
                Tag tag = tagRepository.findTagByName(name).orElse(Tag.builder().icon(null).name(name).build());
                shop.addTag(tag);
            }

            shopRepository.save(shop);

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
                .url(shop.getUrl())
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
