package me.apjung.backend.service.shop;

import me.apjung.backend.api.exception.ShopFileUploadException;
import me.apjung.backend.api.exception.ShopNotFoundException;
import me.apjung.backend.domain.File.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.file.FileRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.service.File.FileService;
import me.apjung.backend.service.File.dto.SavedFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShopServiceImplTest {
    @InjectMocks
    private ShopServiceImpl shopService;
    @Mock
    private FileService fileService;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private FileRepository fileRepository;

    @Test
    @DisplayName("이미지 있는 쇼핑몰 생성 성공 테스트")
    public void createSuccessTest() throws IOException {
        // given
        final var inputStream = new ClassPathResource("mock/images/440x440.jpg").getInputStream();
        final var multipartFile = new MockMultipartFile("thumbnail", "mock_thumbnail.jpg", "image/jpg", inputStream.readAllBytes());
        final var request = new ShopRequest.Create("test name", "test url", "test overview", multipartFile);
        final var savedFileDto = SavedFile.builder().build();
        final var savedFile = File.builder().build();
        final var savedShop = Shop.builder().build();
        ReflectionTestUtils.setField(savedShop, "id", 1L);

        given(fileService.upload(any(MultipartFile.class)))
                .willReturn(savedFileDto);
        given(fileRepository.save(any(File.class)))
                .willReturn(savedFile);
        given(shopRepository.save(any(Shop.class)))
                .willReturn(savedShop);

        // when
        final var result = shopService.create(request);

        // then
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("이미지 있는 쇼핑몰 생성 실패 테스트(이미지 업로드 실패)")
    public void createFailureWithFileUploadFailureTest() throws IOException {
        // given
        final var inputStream = new ClassPathResource("mock/images/440x440.jpg").getInputStream();
        final var multipartFile = new MockMultipartFile("thumbnail", "mock_thumbnail.jpg", "image/jpg", inputStream.readAllBytes());
        final var request = new ShopRequest.Create("test name", "test url", "test overview", multipartFile);
        final var savedShop = Shop.builder().build();
        ReflectionTestUtils.setField(savedShop, "id", 1L);

        given(fileService.upload(any(MultipartFile.class)))
                .willThrow(IOException.class);

        // when, then
        assertThrows(ShopFileUploadException.class, () -> {
            shopService.create(request);
        });
    }

    @Test
    @DisplayName("쇼핑몰 id로 조회(성공)")
    public void getSuccessTest() {
        // given
        final var shopId = anyLong();
        final var shop = Shop.builder()
                .name("테스트 shop")
                .url("test url")
                .thumbnail(null)
                .overview("테스트 샵은 이러하다.")
                .build();
        final var expected = ShopResponse.GET.from(shop);

        given(shopRepository.findById(shopId))
                .willReturn(Optional.of(shop));

        // when
        final var result = shopService.get(shopId);

        // then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("쇼핑몰 id로 조회(실패. id가 존재하지 않음)")
    public void getFailureTest() {
        // given
        final var shopId = anyLong();

        given(shopRepository.findById(shopId))
                .willReturn(Optional.empty());

        // when, then
        assertThrows(ShopNotFoundException.class, () -> shopService.get(shopId));
    }
}