package me.apjung.backend.api;

import me.apjung.backend.api.locator.ShopSearchServiceLocator;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.service.security.CurrentUser;
import me.apjung.backend.service.security.CustomUserDetails;
import me.apjung.backend.service.shop.ShopService;
import me.apjung.backend.service.shop.search.ShopSearchOrderByStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/shop")
//@Validated
public class ShopController {
    private final ShopService shopService;
    private final ShopSearchServiceLocator shopSearchServiceLocator;

    public ShopController(ShopService shopService, ShopSearchServiceLocator shopSearchServiceLocator) {
        this.shopService = shopService;
        this.shopSearchServiceLocator = shopSearchServiceLocator;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ShopResponse.Create create(@Valid ShopRequest.Create request) {
        return shopService.create(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ShopResponse.GET get(@PathVariable Long id, @CurrentUser CustomUserDetails customUserDetails) {
        return shopService.get(id, customUserDetails.getUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ShopResponse.SearchResult> search(@Valid ShopRequest.Search request) {
        // TODO: 2020-12-18 view_log 인덱스 걸기, 성능 테스트, 동시성 테스트
        return shopSearchServiceLocator.getSearchShopService(ShopSearchOrderByStrategy.from(request.getOrderType()))
                .search(request.getFilter(), (request.getPageNum() - 1) * request.getPageSize(), request.getPageSize());
    }
}