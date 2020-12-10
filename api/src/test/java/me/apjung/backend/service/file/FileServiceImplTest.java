package me.apjung.backend.service.file;

import me.apjung.backend.property.appprops.AppProps;
import me.apjung.backend.property.StorageProps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.Objects;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {
    @InjectMocks
    private FileServiceImpl fileService;
    @Mock
    private S3Client s3client;
    @Mock
    private AppProps appProps;
    @Mock
    private StorageProps storageProps;

    @Mock
    private MultipartFile multipartFile;

    @Test
    @DisplayName("이미지 파일 업로드 성공 테스트")
    public void imageFileUploadSuccessTest() throws IOException {
        // given
        given(multipartFile.getOriginalFilename())
                .willReturn("440x440.jpg");
        given(multipartFile.getBytes())
                .willReturn(new byte[0]);
        given(multipartFile.getInputStream())
                .willReturn(new ClassPathResource("mock/images/440x440.jpg").getInputStream());

        // when
        final var savedFile = fileService.upload(multipartFile);

        // then
        assertEquals("440x440.jpg", savedFile.getOriginalName());
        assertEquals("jpg", savedFile.getOriginalExtension());
        assertEquals(440, savedFile.getWidth());
        assertEquals(440, savedFile.getHeight());
    }

    @Test
    @DisplayName("파일을 바이트 배열로 변환하는데 실패해서 파일 업로드 실패 테스트")
    public void fileUploadFailureForConversionFailureFileToByteArrayTest() throws IOException {
        // given
        given(multipartFile.getBytes())
                .willThrow(IOException.class);

        // when, then
        assertThrows(IOException.class, () -> fileService.upload(multipartFile));
    }

    @Test
    @DisplayName("이미지 파일의 inputStream을 가져오는데 실패해서 파일 업로드 실패 테스트")
    public void imageFileUploadFailureForGetInputStreamFailureTest() throws IOException {
        // given
        given(multipartFile.getOriginalFilename())
                .willReturn("test.jpg");
        given(multipartFile.getBytes())
                .willReturn(new byte[0]);
        given(multipartFile.getInputStream())
                .willThrow(IOException.class);

        // when, then
        assertThrows(IOException.class, () -> fileService.upload(multipartFile));
    }

    @Test
    @DisplayName("확장자가 이미지인지 판별하는 테스트")
    public void isImageTest() {
        final var imageExtension = "jpg";
        final var notImageExtension = "txt";

        final var resultWithImageExtension = ReflectionTestUtils.invokeMethod(fileService, "isImage", imageExtension);
        final var resultWithNotImageExtension = ReflectionTestUtils.invokeMethod(fileService, "isImage", notImageExtension);

        assertTrue(((boolean) Objects.requireNonNull(resultWithImageExtension)));
        assertFalse((boolean) Objects.requireNonNull(resultWithNotImageExtension));
    }
}