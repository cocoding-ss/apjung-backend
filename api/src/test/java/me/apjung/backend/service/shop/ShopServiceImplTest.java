package me.apjung.backend.service.shop;

import me.apjung.backend.api.exception.ShopNotFoundException;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.File.FileRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.service.File.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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