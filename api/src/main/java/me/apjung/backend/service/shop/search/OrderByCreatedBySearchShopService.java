package me.apjung.backend.service.shop.search;

import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.shop.ShopRepository;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

public class OrderByCreatedBySearchShopService implements SearchShopService {
    private final ShopRepository shopRepository;

    public OrderByCreatedBySearchShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    // TODO: 2020-11-30 현재 Service 내용이 model transform 밖에 없어서 추가 기능이 생길 때 테스트 코드 작성하면 됨
    @Override
    public List<ShopResponse.SearchResult> search(ShopRequest.Search request) {
        return shopRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(request.getPageNum(), request.getPageSize()))
                .stream()
                .map(ShopResponse.SearchResult::from)
                .collect(Collectors.toList());
    }
}