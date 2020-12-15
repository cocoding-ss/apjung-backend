package me.apjung.backend.service.shop;

import me.apjung.backend.api.exception.ShopFileUploadException;
import me.apjung.backend.api.exception.ShopNotFoundException;
import me.apjung.backend.domain.base.ViewStats;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopViewLog;
import me.apjung.backend.domain.shop.ShopViewStats;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.file.FileRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.repository.shop_view_stats.ShopViewStatsRepository;
import me.apjung.backend.repository.shopviewlog.ShopViewLogRepository;
import me.apjung.backend.repository.tag.TagRepository;
import me.apjung.backend.service.file.FileService;
import me.apjung.backend.service.file.dto.SavedFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShopServiceImplTest {

    @Spy
    @InjectMocks
    private ShopServiceImpl shopService;
    @Mock
    private FileService fileService;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ShopViewStatsRepository shopViewStatsRepository;
    @Mock
    private ShopViewLogRepository shopViewLogRepository;

    @Test
    @DisplayName("이미지 있는 쇼핑몰 생성 성공 테스트")
    public void createSuccessTest() throws IOException {
        // given
        final var inputStream = new ClassPathResource("mock/images/440x440.jpg").getInputStream();
        final var multipartFile = new MockMultipartFile("thumbnail", "mock_thumbnail.jpg", "image/jpg", inputStream.readAllBytes());
        final var request = new ShopRequest.Create("test name", "test url", "test overview", multipartFile, new HashSet<>());
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
        final var request = new ShopRequest.Create("test name", "test url", "test overview", multipartFile, new HashSet<>());

        given(fileService.upload(any(MultipartFile.class)))
                .willThrow(IOException.class);

        // when, then
        assertThrows(ShopFileUploadException.class, () -> shopService.create(request));
    }

    @Test
    @DisplayName("쇼핑몰 id로 오늘 최초로 조회(성공)")
    public void getSuccessTest() {
        // given
        final var shop = Shop.builder()
                .name("테스트 shop")
                .url("test url")
                .thumbnail(null)
                .overview("테스트 샵은 이러하다.")
                .build();
        final var shopViewStats = spy(ShopViewStats.builder()
                .shop(shop)
                .build());
        final var expected = ShopResponse.GET.from(shop);

        given(shopRepository.findById(anyLong()))
                .willReturn(Optional.of(shop));
        given(shopViewStatsRepository.findShopViewStatsByShopId(anyLong()))
                .willReturn(Optional.of(shopViewStats));
        given(shopViewLogRepository.findShopViewLogByUserIdAndShopIdAndAccessedAt(anyLong(), anyLong(), any(LocalDate.class)))
            .willReturn(Optional.empty());

        // when
        final var result = shopService.get(1L, mock(User.class));

        // then
        verify(shopViewStats, times(1)).firstVisit();
        verify(shopViewStats, never()).visit();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("쇼핑몰 id로 오늘 여러번 조회(성공)")
    public void getSuccessMultipleViewsTest() {
        // given
        final var shop = Shop.builder()
                .name("테스트 shop")
                .url("test url")
                .thumbnail(null)
                .overview("테스트 샵은 이러하다.")
                .build();
        final var shopViewStats = spy(ShopViewStats.builder()
                .shop(shop)
                .build());
        final var shopViewLog = spy(ShopViewLog.builder()
                .shop(shop)
                .accessedAt(LocalDate.now())
                .build());
        final var expected = ShopResponse.GET.from(shop);

        given(shopRepository.findById(anyLong()))
                .willReturn(Optional.of(shop));
        given(shopViewStatsRepository.findShopViewStatsByShopId(anyLong()))
                .willReturn(Optional.of(shopViewStats));
        given(shopViewLogRepository.findShopViewLogByUserIdAndShopIdAndAccessedAt(anyLong(), anyLong(), any(LocalDate.class)))
                .willReturn(Optional.of(shopViewLog));
        given(shopViewStats.getViewStats())
                .willReturn(new ViewStats(1L, 1L));

//        ReflectionTestUtils.setField(shopViewStats, "viewStats", new ViewStats(1L, 1L));

        // when
        final var result = shopService.get(1L, mock(User.class));

        // then
        verify(shopViewStats, times(1)).visit();
        verify(shopViewStats, never()).firstVisit();
        verify(shopViewLog, times(1)).increaseAccessedCount();
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
        assertThrows(ShopNotFoundException.class, () -> shopService.get(shopId, mock(User.class)));
    }

    @Test
    @DisplayName("해당 쇼핑몰 shop_view_stats가 없을 경우(시스템적 문제로 db 내용 확인 필요)")
    public void getFailureTestByNoShopViewStats() {
        // given
        final var shop = Shop.builder()
                .name("테스트 shop")
                .url("test url")
                .thumbnail(null)
                .overview("테스트 샵은 이러하다.")
                .build();
        final var shopId = anyLong();

        given(shopRepository.findById(shopId))
                .willReturn(Optional.of(shop));
        given(shopViewStatsRepository.findShopViewStatsByShopId(anyLong()))
                .willReturn(Optional.empty());

        // when, then
        assertThrows(RuntimeException.class, () -> shopService.get(shopId, mock(User.class)));
    }
}