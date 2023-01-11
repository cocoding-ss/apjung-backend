package me.apjung.backend.repository.shop;

import me.apjung.backend.domain.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {

}