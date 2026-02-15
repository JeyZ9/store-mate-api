package com.sm.jeyz9.storemateapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.jeyz9.storemateapi.dto.ProductRequestDTO;
import com.sm.jeyz9.storemateapi.dto.ProductWithCategoryDTO;
import com.sm.jeyz9.storemateapi.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @Operation(
            summary = "เพิ่มสินค้าใหม่",
            description = """
                    ใช้สำหรับเพิ่มข้อมูลสินค้าใหม่เข้าสู่ระบบ พร้อมรองรับการอัปโหลดรูปภาพสูงสุด 5 ภาพ
                    ตัวอย่าง Request:
                    {
                      "productName": "string",
                      "categoryId": 0,
                      "price": 0.1,
                      "statusId": 0,
                      "summary": "string",
                      "description": "string",
                      "stockQuantity": 0
                    }
            """
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(
            @RequestPart(value = "request", required = true) String requestJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ProductRequestDTO request = mapper.readValue(requestJson, ProductRequestDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.addProduct(request, files));
    }

    @Operation(
            summary = "แสดงสินค้าตามหมวดหมู่",
            description = "ใช้สำหรับดึงข้อมูลสินค้าที่แบ่งตามหมวดหมู่ไว้แสดงที่หน้าแรกของเว็บไซต์"
    )
    @GetMapping("/grouped-by-category")
    public ResponseEntity<ProductWithCategoryDTO> getProductsWithCategory() {
        return ResponseEntity.ok(productService.getProductsWithCategory());
    }
}
