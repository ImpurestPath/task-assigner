package ru.rvr.taskassigner.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;

public class InterfaceBasedJpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID> extends JpaRepositoryFactoryBean<T,S,ID> {
    public InterfaceBasedJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    protected @NotNull RepositoryFactorySupport createRepositoryFactory(@NotNull EntityManager entityManager) {
        return new InterfaceBasedJpaRepositoryFactory(entityManager);
    }
}
