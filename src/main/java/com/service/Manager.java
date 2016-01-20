package com.service;

import com.domain.Provider;

public interface Manager {
    //Provider
    Long addProvider(Provider provider);
    Provider getProviderByNip(String providerNip);
}
