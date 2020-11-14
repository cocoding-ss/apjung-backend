package me.apjung.backend.api;

import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.service.Shop.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid ShopRequest.Create request) {
        shopService.create(request);
    }
}
