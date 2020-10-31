package me.apjung.backend.repository.Shop;

import me.apjung.backend.domain.Shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {
}
