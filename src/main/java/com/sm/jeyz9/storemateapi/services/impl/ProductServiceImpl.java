package com.sm.jeyz9.storemateapi.services.impl;

import com.sm.jeyz9.storemateapi.dto.ProductDTO;
import com.sm.jeyz9.storemateapi.dto.ProductRequestDTO;
import com.sm.jeyz9.storemateapi.dto.ProductWithCategoryDTO;
import com.sm.jeyz9.storemateapi.exceptions.WebException;
import com.sm.jeyz9.storemateapi.models.Category;
import com.sm.jeyz9.storemateapi.models.Product;
import com.sm.jeyz9.storemateapi.models.ProductImage;
import com.sm.jeyz9.storemateapi.models.ProductStatus;
import com.sm.jeyz9.storemateapi.models.ProductStatusName;
import com.sm.jeyz9.storemateapi.models.ProductStock;
import com.sm.jeyz9.storemateapi.repository.CategoryRepository;
import com.sm.jeyz9.storemateapi.repository.ProductRepository;
import com.sm.jeyz9.storemateapi.repository.ProductStatusRepository;
import com.sm.jeyz9.storemateapi.repository.ProductStockRepository;
import com.sm.jeyz9.storemateapi.services.ProductService;
import com.sm.jeyz9.storemateapi.services.SupabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ProductStatusRepository productStatusRepository;
    private final CategoryRepository categoryRepository;
    private final ProductStockRepository productStockRepository;
    private final SupabaseService supabaseService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductStatusRepository productStatusRepository, CategoryRepository categoryRepository, ProductStockRepository productStockRepository, SupabaseService supabaseService) {
        this.productRepository = productRepository;
        this.productStatusRepository = productStatusRepository;
        this.categoryRepository = categoryRepository;
        this.productStockRepository = productStockRepository;
        this.supabaseService = supabaseService;
    }
    
    @Override
    @Transactional
    public String addProduct(ProductRequestDTO request, List<MultipartFile> files) {
        try{
            ProductStatus status = productStatusRepository.findById(request.getStatusId()).orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Product Status not found."));
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Category not found."));
            Product product = Product.builder()
                    .id(null)
                    .name(request.getProductName())
                    .description(request.getDescription())
                    .productStatus(status)
                    .category(category)
                    .summary(request.getSummary())
                    .price(request.getPrice())
                    .createdAt(LocalDateTime.now())
                    .build();
            productRepository.save(product);

            ProductStock productStock = ProductStock.builder()
                    .id(null)
                    .product(product)
                    .stockQuantity(request.getStockQuantity())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            productStockRepository.save(productStock);
            
            supabaseService.saveProductImages(product.getId(), files);
            
            return "Add product success.";
        }catch(WebException e) {
            throw e;
        }
        catch (Exception e) {
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public ProductWithCategoryDTO getProductsWithCategory(){
        try {
            List<Product> productList = productRepository.findAll();
            List<ProductDTO> products = mapToProductDTO(productList);
            products = products.stream().filter(a -> a.getStatus().equalsIgnoreCase(ProductStatusName.ACTIVE.toString())).toList();
            return ProductWithCategoryDTO.builder()
                    .promotion(
                            products.stream().filter(pp -> pp.getCategoryName().equalsIgnoreCase("Promotion")).limit(4).toList()
                    )
                    .soap(
                            products.stream().filter(pp -> pp.getCategoryName().equalsIgnoreCase("Soap")).limit(4).toList()
                    )
                    .drinks(
                            products.stream().filter(pp -> pp.getCategoryName().equalsIgnoreCase("Drinks")).limit(4).toList()
                    )
                    .shampoo(
                            products.stream().filter(pp -> pp.getCategoryName().equalsIgnoreCase("Shampoo")).limit(4).toList()
                    )
                    .build();
        }catch (Exception e) {
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }
    
    private List<ProductDTO> mapToProductDTO(List<Product> products) {
        return products.stream().map(p -> 
                    ProductDTO.builder()
                            .id(p.getId())
                            .productName(p.getName())
                            .categoryName(p.getCategory().getName())
                            .imageUrl(p.getProductImage().stream().findFirst().map(ProductImage::getImageUrl).orElse(null))
                            .status(p.getProductStatus().getStatus())
                            .summary(p.getSummary())
                            .price(p.getPrice())
                            .createdAt(p.getCreatedAt())
                            .build()
        ).sorted(Comparator.comparing(ProductDTO::getCreatedAt)).toList();
    }
}
