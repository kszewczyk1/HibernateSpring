package com.service;

import com.domain.Product;
import com.domain.Provider;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ManagerImpl implements Manager {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long addProvider(Provider provider) {
        Long id = (Long) sessionFactory.getCurrentSession().save(provider);
        provider.setProviderId(id);
        return id;
    }

    @Override
    public Provider getProviderByNip(String providerNip) {
        return (Provider) sessionFactory.getCurrentSession().getNamedQuery("provider.byNip").setString("providerNip", providerNip).uniqueResult();
    }

    @Override
    public List<Provider> getAllProviders() {
        return sessionFactory.getCurrentSession().getNamedQuery("provider.all").list();
    }

    @Override
    public void deleteProvider(Provider provider) {
        provider = (Provider) sessionFactory.getCurrentSession().get(Provider.class, provider.getProviderId());

        for (Product product : provider.getProducts()) {
            product.setIsDelivered(false);
            sessionFactory.getCurrentSession().update(product);
        }
        sessionFactory.getCurrentSession().delete(provider);
    }

    @Override
    public Provider getProviderById(Long providerId) {
        return (Provider) sessionFactory.getCurrentSession().get(Provider.class, providerId);
    }

    @Override
    public void updateProvider(Provider provider, String providerName, String providerNip, List<Product> products) {
        provider = (Provider) sessionFactory.getCurrentSession().get(Provider.class, provider.getProviderId());
        provider.setProviderName(providerName);
        provider.setProviderNip(providerNip);
        provider.setProducts(products);
        sessionFactory.getCurrentSession().update(provider);
    }

    @Override
    public Long addProduct(Product product) {
        Long id = (Long) sessionFactory.getCurrentSession().save(product);
        product.setId(id);
        return id;
    }

    @Override
    public List<Product> getAllProducts() {
        return sessionFactory.getCurrentSession().getNamedQuery("product.all").list();
    }

    @Override
    public void deleteProduct(Product product) {
        product = (Product) sessionFactory.getCurrentSession().get(Product.class, product.getId());
        Provider provider = (Provider) sessionFactory.getCurrentSession().get(Provider.class, product.getProvider().getProviderId());
        provider.getProducts().remove(product);
        sessionFactory.getCurrentSession().delete(product);
    }

    @Override
    public Product getProductById(Long productId) {
        return (Product) sessionFactory.getCurrentSession().get(Product.class, productId);
    }

    @Override
    public void updateProduct(Product product, String productName, double productPrice, boolean isDelivered, Provider provider) {
        product = (Product) sessionFactory.getCurrentSession().get(Product.class, product.getId());
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setIsDelivered(isDelivered);
        product.setProvider(provider);
        sessionFactory.getCurrentSession().update(product);
    }
}
