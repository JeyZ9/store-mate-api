package com.sm.jeyz9.storemateapi.services;

import com.sm.jeyz9.storemateapi.dto.ProductRequestDTO;
import com.sm.jeyz9.storemateapi.dto.ProductWithCategoryDTO;
import com.sm.jeyz9.storemateapi.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequestDTO request, List<MultipartFile> files);
    ProductWithCategoryDTO getProductsWithCategory();
//    List<Product> searchProducts(String keyword, String categoryName, Double minPrice, Double maxPrice, int page, int size);
//    Product getProductDetails(Long id);
//    String updateProduct(Long id, Product request);
//    List<Product> getAllProduct();
//    void removeProduct();
}
