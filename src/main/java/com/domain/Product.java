package com.domain;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "product.all", query = "SELECT p FROM Product p")
})
public class Product implements java.io.Serializable{
    private Long id;
    private Provider provider;
    private String productName;
    private double productPrice;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "providerId", nullable = false)
    public Provider getProvider() {
        return provider;
    }
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Column(nullable = false)
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(nullable = false)
    public double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}