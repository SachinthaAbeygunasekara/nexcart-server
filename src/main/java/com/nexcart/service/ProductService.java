package com.nexcart.service;

import com.nexcart.dto.product.ProductRequest;
import com.nexcart.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Integer id);

    ProductResponse updateProduct(Integer id, ProductRequest request);

    void deleteProduct(Integer id);
}
