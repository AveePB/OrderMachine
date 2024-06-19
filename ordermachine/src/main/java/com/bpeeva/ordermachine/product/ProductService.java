package com.bpeeva.ordermachine.product;

import com.bpeeva.ordermachine.product.db.Product;
import com.bpeeva.ordermachine.product.db.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;

    public List<ProductDTO> getAll() {
        return productRepo.findAll().stream()
                .map(p -> new ProductDTO(p.getName(), p.getPrice()))
                .toList();
    }

    public boolean add(String productName, Double productPrice) {
        Product product = new Product(null, productName, productPrice, null);
        if (productPrice <= 0) return false;

        try {
            Product savedProduct = productRepo.save(product);
            Integer id = savedProduct.getId();
        }
        catch (DataAccessException ex) {
            return false;
        }
        return true;
    }

    public boolean updatePrice(String productName, Double newPrice) {
        Optional<Product> product = productRepo.findByName(productName);
        if (product.isEmpty()) return false;
        if (newPrice <= 0) return false;

        product.get().setPrice(newPrice);
        productRepo.save(product.get());
        return true;
    }

    public void remove(String productName) {
        productRepo.deleteByName(productName);
    }
}
