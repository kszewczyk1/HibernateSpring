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
        manager.addProvider(provider);

        Provider retrievedProvider = manager.getProviderByNip(providerNip1);

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



}
