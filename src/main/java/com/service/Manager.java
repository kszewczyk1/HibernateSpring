package com.service;

import com.domain.Provider;

import java.util.List;

public interface Manager {
    //Provider
    Long addProvider(Provider provider);
    void deleteProvider(Provider provider);
    Provider getProviderByNip(String providerNip);
    List<Provider> getAllProviders();
    Provider getProviderById(Long providerId);
}
