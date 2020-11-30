package me.apjung.backend.api;

import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.service.shop.search.SearchShopService;
import me.apjung.backend.service.shop.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    private final SearchShopService searchShopService;

    public ShopController(ShopService shopService, SearchShopService searchShopService) {
        this.shopService = shopService;
        this.searchShopService = searchShopService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ShopResponse.Create create(@Valid ShopRequest.Create request) {
        return shopService.create(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ShopResponse.GET get(@PathVariable Long id) {
        return shopService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ShopResponse.SearchResult> searchByName(@RequestParam ShopRequest.Search request) {
        return searchShopService.search(request);
    }
}
