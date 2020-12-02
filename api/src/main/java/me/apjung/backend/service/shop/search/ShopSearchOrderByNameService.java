package me.apjung.backend.service.shop.search;

import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.shop.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopSearchOrderByNameService implements ShopSearchService {
    private final ShopRepository shopRepository;

    public ShopSearchOrderByNameService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public List<ShopResponse.SearchResult> search(ShopRequest.Search request) {
        return shopRepository.findAllDynamicQueryOrderByName(request.getFilter().getName(), request.getPageNum(), request.getPageSize())
                .stream()
                .map(ShopResponse.SearchResult::from)
                .collect(Collectors.toList());
    }

    @Override
    public boolean identify(ShopSearchOrderByStrategy strategy) {
        return strategy == ShopSearchOrderByStrategy.NAME;
    }
}