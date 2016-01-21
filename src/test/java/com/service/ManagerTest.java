package com.service;

import com.domain.Product;
import com.domain.Provider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional

public class ManagerTest {
    @Autowired
    Manager manager;

    private final String providerName1 = "providerName1";
    private final String providerNip1 = "providerNip1";

    private final String providerName2 = "providerName2";
    private final String providerNip2 = "providerNip2";

    private final String productName1 = "productName1";
    private final double productPrice1 = 1.0;

    private final String productName2 = "prodictName2";
    private final double productPrice2 = 2.0;

    Product product = new Product();
    Provider provider = new Provider();

    @Before
    public void before() {
        provider.setProviderName(providerName1);
        provider.setProviderNip(providerNip1);

        product.setProductName(productName1);
        product.setProductPrice(productPrice1);
        product.setProvider(provider);
        product.setIsDelivered(true);
    }

    @After
    public void after() {
        List<Provider> providers = manager.getAllProviders();
        List<Product> products = manager.getAllProducts();

        for(Product prod : products) {
            if(prod.getId() == product.getId())
                manager.deleteProduct(product);
        }

        for(Provider prov : providers) {
            if(prov.getProviderId() == provider.getProviderId())
                manager.deleteProvider(provider);
        }
    }

    //Provider

    @Test
    public void addProviderCheck() {
        Long id = manager.addProvider(provider);

        Provider retrievedProvider = manager.getProviderById(id);

        assertEquals(providerName1, retrievedProvider.getProviderName());
        assertEquals(providerNip1, retrievedProvider.getProviderNip());
    }

    @Test
    public void getAllProvidersCheck() {
        List<Provider> providers = manager.getAllProviders();
        int count = providers.size();
        manager.addProvider(provider);
        providers = manager.getAllProviders();
        assertEquals(count+1, providers.size());
    }

    @Test
    public void deleteProviderCheck() {
        manager.addProvider(provider);
        int count = manager.getAllProviders().size();
        manager.deleteProvider(provider);
        assertEquals(count-1, manager.getAllProviders().size());
        assertNull(manager.getProviderByNip(provider.getProviderNip()));
    }

    @Test
    public void getProvidersByIdCheck() {
        Long id = manager.addProvider(provider);
        Provider provider = manager.getProviderById(id);
        assertEquals(id, provider.getProviderId());
        assertEquals(providerName1, provider.getProviderName());
        assertEquals(providerNip1, provider.getProviderNip());
    }

    @Test
    public void getProvidersByNipCheck() {
        manager.addProvider(provider);
        String nip = provider.getProviderNip();
        Provider provider = manager.getProviderByNip(nip);
        assertEquals(nip, provider.getProviderNip());
        assertEquals(providerName1, provider.getProviderName());
    }

    @Test
    public void updateProviderCheck() {
        List<Product> products = new ArrayList<Product>();
        products.add(product);
        provider.setProducts(products);
        manager.addProvider(provider);
        manager.updateProvider(provider, providerName2, providerNip2, products);
        assertEquals(providerName2, provider.getProviderName());
        assertEquals(providerNip2, provider.getProviderNip());
        assertEquals(products, provider.getProducts());

        List<Provider> providers = manager.getAllProviders();
        for(Provider p : providers) {
            if(!p.equals(provider)) {
                assertEquals(p.getProviderName(), not(providerName2));
                assertEquals(p.getProviderNip(), not(providerNip2));
                assertEquals(p.getProducts(), not(products));
            }
        }
    }

    //Product

    @Test
    public void addProductCheck() {
        manager.addProvider(provider);
        Long productId = manager.addProduct(product);

        Product retrievedProduct = manager.getProductById(productId);

        assertEquals(productName1, retrievedProduct.getProductName());
        assertEquals(productPrice1, retrievedProduct.getProductPrice(), 0);
        assertEquals(provider, retrievedProduct.getProvider());
        assertEquals(true, retrievedProduct.getIsDelivered());
    }

    @Test
    public void getAllProductsCheck() {
        List<Product> products = manager.getAllProducts();
        int count = products.size();
        manager.addProvider(provider);
        manager.addProduct(product);
        products = manager.getAllProducts();
        assertEquals(count+1, products.size());
    }

    @Test
    public void deleteProductCheck() {
        manager.addProvider(provider);
        manager.addProduct(product);
        int count = manager.getAllProducts().size();
        manager.deleteProduct(product);
        assertEquals(count-1, manager.getAllProducts().size());
        assertNull(manager.getProductById(product.getId()));
    }

    @Test
    public void getProductsByIdCheck() {
        manager.addProvider(provider);
        Long id = manager.addProduct(product);
        Product product = manager.getProductById(id);
        assertEquals(id, product.getId());
        assertEquals(productName1, product.getProductName());
        assertEquals(productPrice1, product.getProductPrice(), 0);
        assertEquals(true, product.getIsDelivered());
    }

    @Test
    public void getDeliveredProductsCheck() {
        int count = manager.getDeliveredProducts().size();
        manager.addProvider(provider);
        manager.addProduct(product);
        assertEquals(count + 1, manager.getDeliveredProducts().size());
        List<Product> deliveredProducts = manager.getDeliveredProducts();
        for(Product product : deliveredProducts)
            assertEquals(true, product.getIsDelivered());
    }

    @Test
    public void getProductsByProviderCheck() {
        List<Product> products = new ArrayList<Product>();
        products.add(product);
        provider.setProducts(products);
        manager.addProvider(provider);

        List<Product> retrievedProducts = manager.getProductsByProvider(provider);
        assertEquals(1, retrievedProducts.size());
    }

    @Test
    public void updateProductCheck() {
        manager.addProvider(provider);
        manager.addProduct(product);
        manager.updateProduct(product, productName2, productPrice2, true, provider);
        assertEquals(productName2, product.getProductName());
        assertEquals(productPrice2, product.getProductPrice(), 0);
        assertEquals(true, product.getIsDelivered());
        assertEquals(provider, product.getProvider());

        List<Product> products = manager.getAllProducts();
        for(Product p : products) {
            if(!p.equals(product)) {
                assertEquals(p.getProductName(), not(providerName2));
                assertEquals(p.getProductPrice(), not(providerNip2));
                assertEquals(p.getProvider(), not(provider));
            }
        }
    }
}
