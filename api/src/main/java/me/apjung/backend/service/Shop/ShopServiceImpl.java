package me.apjung.backend.service.Shop;

import lombok.AllArgsConstructor;
import me.apjung.backend.domain.Base.ViewStats;
import me.apjung.backend.domain.File.File;
import me.apjung.backend.domain.Shop.Shop;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.repository.File.FileRepository;
import me.apjung.backend.repository.Shop.ShopRepository;
import me.apjung.backend.service.File.FileService;
import me.apjung.backend.service.File.dto.SavedFile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {
    private FileService fileService;
    private ShopRepository shopRepository;
    private FileRepository fileRepository;

    @Override
    @Transactional
    public void create(ShopRequest.Create request) {
        try {
            SavedFile savedFile = fileService.upload(request.getThumbnail());
            shopRepository.save(Shop.builder()
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
    }
}
