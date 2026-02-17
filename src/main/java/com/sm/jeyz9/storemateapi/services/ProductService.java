package com.sm.jeyz9.storemateapi.services;

import com.sm.jeyz9.storemateapi.dto.PaginationDTO;
import com.sm.jeyz9.storemateapi.dto.ProductDTO;
import com.sm.jeyz9.storemateapi.dto.ProductRequestDTO;
import com.sm.jeyz9.storemateapi.dto.ProductWithCategoryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequestDTO request, List<MultipartFile> files);
    ProductWithCategoryDTO getProductsWithCategory();
    PaginationDTO<ProductDTO> searchProducts(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size);
//    Product getProductDetails(Long id);
//    String updateProduct(Long id, Product request);
//    List<Product> getAllProduct();
//    void removeProduct();
}
