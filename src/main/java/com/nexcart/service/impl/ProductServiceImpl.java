package com.nexcart.service.impl;

import com.nexcart.dto.product.ProductRequest;
import com.nexcart.dto.product.ProductResponse;
import com.nexcart.entity.Product;
import com.nexcart.repository.ProductRepository;
import com.nexcart.repository.ReviewRepository;
import com.nexcart.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = modelMapper.map(request, Product.class);

        Product savedProduct = productRepository.save(product);

        ProductResponse response =
                modelMapper.map(savedProduct, ProductResponse.class);

        response.setAverageRating(0.0);
        response.setTotalReviews(0L);

        return response;
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found with id: " + id
                        ));

        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Integer id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found with id: " + id
                        ));

        modelMapper.map(request, product);

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer id) {

        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Product not found with id: " + id
            );
        }

        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response =
                modelMapper.map(product, ProductResponse.class);

        Double averageRating =
                reviewRepository.getAverageRating(product.getId());

        Long totalReviews =
                reviewRepository.getTotalReviews(product.getId());

        response.setAverageRating(
                averageRating == null ? 0.0 : averageRating
        );

        response.setTotalReviews(
                totalReviews == null ? 0L : totalReviews
        );

        return response;
    }

}