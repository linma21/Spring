package kr.co.ch07.repository.shop.custom;

import kr.co.ch07.entity.shop.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepositoryCustom {

    public List<Product> selectProducts();
    public Product selectProduct(int productId);
}
