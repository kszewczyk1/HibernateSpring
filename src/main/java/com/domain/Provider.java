package com.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "provider.all", query = "select p from Provider p"),
        @NamedQuery(name = "provider.byNip", query = "select p from Provider p where p.providerNip = :providerNip")
})
public class Provider {
    private Long providerId;
    private String providerName;
    private String providerNip;

    private List<Product> products = new ArrayList<Product>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getProviderId() {
        return providerId;
    }
    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Column(nullable = false)
    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderNip() {
        return providerNip;
    }
    public void setProviderNip(String providerNip) {
        this.providerNip = providerNip;
    }
}
