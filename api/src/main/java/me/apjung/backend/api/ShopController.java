package me.apjung.backend.api;

import lombok.AllArgsConstructor;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.service.security.CurrentUser;
import me.apjung.backend.service.security.CustomUserDetails;
import me.apjung.backend.service.shop.ShopService;
import me.apjung.backend.service.shop.search.ShopSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    private final ShopSearchService shopSearchService;

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
    @PutMapping("/{id}/safe")
    public ShopResponse.Safe safe(@PathVariable Long id, @RequestBody ShopRequest.Safe request) {
        return shopService.safe(id, request.getSafeLevel());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ShopResponse.SearchResult> search(@Valid ShopRequest.Search request) {
        return shopSearchService.search(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/pin")
    public ShopResponse.CreatePin createPin(@PathVariable Long id, @CurrentUser CustomUserDetails currentUser) throws Exception {
        return shopService.createPin(id, currentUser.getUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/pin")
    public ShopResponse.DeletePin deletePin(@PathVariable Long id, @CurrentUser CustomUserDetails currentUser) {
        return shopService.deletePin(id, currentUser.getUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pinned")
    public List<ShopResponse.GET> getMyPinnedShops(@CurrentUser CustomUserDetails currentUser) {
        return shopService.getMyPinnedShops(currentUser.getUser());
    }
}