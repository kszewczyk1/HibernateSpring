package com.service;

import com.domain.Product;
import com.domain.Provider;

import java.util.List;

public interface Manager {
    //Provider
    Long addProvider(Provider provider);
    void deleteProvider(Provider provider);
    Provider getProviderByNip(String providerNip);
    List<Provider> getAllProviders();
    Provider getProviderById(Long providerId);
    void updateProvider(Provider provider, String providerName, String providerNip, List<Product> products);

    //Product
    Long addProduct(Product product);
    void deleteProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    void updateProduct(Product product, String productName, double productPrice, boolean isDelivered, Provider provider);
}
