package me.apjung.backend.service.shop.search;

import lombok.AllArgsConstructor;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.shop.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShopSearchServiceImpl implements ShopSearchService {
    private final ShopRepository shopRepository;

    @Override
    public List<ShopResponse.SearchResult> search(ShopRequest.Search request) {
        return shopRepository.findAllBySearch(request).stream().map(ShopResponse.SearchResult::from).collect(Collectors.toList());
    }
}
