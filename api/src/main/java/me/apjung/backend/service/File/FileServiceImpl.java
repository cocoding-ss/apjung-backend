package me.apjung.backend.service.File;

import lombok.AllArgsConstructor;
import me.apjung.backend.component.RandomStringBuilder.RandomStringBuilder;
import me.apjung.backend.property.AppProps.AppProps;
import me.apjung.backend.property.StorageProps;
import me.apjung.backend.service.File.dto.SavedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 현재는 AWS S3에만 대응가능하도록 개발했지만, 이후에는 여러 스토리지를 이용할 수 있음, 이를위해서 Dto만을 이용해서 통신할 수 있도록 함
 * 즉 이 클래스는 File Entity에 종속되지 않고 Dto를 사용하도록 하자!
 * 이후 모듈화 가능성도 검토해보아야한다
 */
@Service
public class FileServiceImpl implements FileService {
    private S3Client s3;

    @Autowired
    private AppProps appProps;

    @Autowired
    private StorageProps storageProps;

    @PostConstruct
    private void FileServiceImpl() {
        s3 = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }

    @Override
    public SavedFile upload(MultipartFile file) throws IOException {
        String prefix = "/" + appProps.getCurrentEnv() + "/public/";
        String originalName = file.getOriginalFilename();
        String originalExtension =  originalName.substring(originalName.lastIndexOf(".") + 1);

        String name = RandomStringBuilder.generateAlphaNumeric(60) + "." + originalExtension;
            String publicUrl = storageProps.getS3Public() + prefix + name;

        PutObjectResponse response = s3.putObject(
                PutObjectRequest.builder().key(prefix + name).bucket(storageProps.getS3Bucket()).build(),
                    RequestBody.fromBytes(file.getBytes())
        );

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
        return extension.toLowerCase().matches("png|jpeg|jpg|bmp|gif|svg");
    }
}
