package com.service;

import com.domain.Provider;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
}
