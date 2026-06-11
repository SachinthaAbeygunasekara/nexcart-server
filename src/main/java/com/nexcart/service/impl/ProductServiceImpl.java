package com.nexcart.service.impl;

import com.nexcart.dto.product.ProductRequest;
import com.nexcart.dto.product.ProductResponse;
import com.nexcart.entity.Product;
import com.nexcart.repository.ProductRepository;
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
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = modelMapper.map(request, Product.class);

        Product savedProduct =
                productRepository.save(product);

        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(
                        product,
                        ProductResponse.class
                ))
                .toList();
    }

    @Override
    public ProductResponse getProductById(Integer id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Product not found with id: " + id
                                ));

        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public ProductResponse updateProduct(Integer id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found with id: " + id
                        ));

        modelMapper.map(request, product);

        Product updatedProduct =
                productRepository.save(product);

        return modelMapper.map(
                updatedProduct,
                ProductResponse.class
        );
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

}
