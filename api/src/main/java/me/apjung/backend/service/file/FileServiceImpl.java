package me.apjung.backend.service.file;

import me.apjung.backend.component.RandomStringBuilder.RandomStringBuilder;
import me.apjung.backend.property.AppProps.AppProps;
import me.apjung.backend.property.StorageProps;
import me.apjung.backend.service.file.dto.SavedFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

/**
 * 현재는 AWS S3에만 대응가능하도록 개발했지만, 이후에는 여러 스토리지를 이용할 수 있음, 이를위해서 Dto만을 이용해서 통신할 수 있도록 함
 * 즉 이 클래스는 File Entity에 종속되지 않고 Dto를 사용하도록 하자!
 * 이후 모듈화 가능성도 검토해보아야한다
 */
@Service
public class FileServiceImpl implements FileService {
    private final S3Client s3client;
    private final AppProps appProps;
    private final StorageProps storageProps;

    public FileServiceImpl(S3Client s3Client, AppProps appProps, StorageProps storageProps) {
        this.s3client = s3Client;
        this.appProps = appProps;
        this.storageProps = storageProps;
    }

    @Override
    public SavedFile upload(MultipartFile file) throws IOException {
        String prefix = appProps.getCurrentEnv() + "/public/";
        String originalName = file.getOriginalFilename();
        String originalExtension = Optional.ofNullable(originalName)
                .filter(s -> s.contains("."))
                .map(s -> s.substring(originalName.lastIndexOf(".") + 1))
                .orElse(null);

        String name = Optional.ofNullable(RandomStringBuilder.generateAlphaNumeric(60)).orElseThrow() + "." + originalExtension;
        String publicUrl = storageProps.getS3Public() + "/" + prefix + name;

        PutObjectResponse response = s3client.putObject(
                PutObjectRequest.builder()
                        .key(prefix + name)
                        .bucket(storageProps.getS3Bucket())
                        .build(),
                    RequestBody.fromBytes(file.getBytes()));

        Integer width = null;
        Integer height = null;
        boolean isImage = this.isImage(originalExtension);
        if (isImage) {
            BufferedImage image = ImageIO.read(file.getInputStream());
            width = image.getWidth();
            height = image.getHeight();
        }

        return SavedFile.builder()
                .name(name)
                .extension(originalExtension)
                .originalName(originalName)
                .originalExtension(originalExtension)
                .size(file.getSize())
                .prefix(prefix)
                .publicUrl(publicUrl)
                .isImage(isImage)
                .width(width)
                .height(height)
                .build();
    }

    private boolean isImage(String extension) {
        return Optional.ofNullable(extension)
                .map(s -> s.toLowerCase().matches("png|jpeg|jpg|bmp|gif|svg"))
                .orElse(false);
    }
}
