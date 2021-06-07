package ru.rvr.taskassigner.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class InterfaceBasedJpaRepositoryFactory extends JpaRepositoryFactory {

    private final Map<? extends Class<?>, ? extends Class<?>> interfaceToEntityClassMap;
    private final EntityManager entityManager;
    private boolean allowNonInterfaceTypes = true;

    public InterfaceBasedJpaRepositoryFactory(EntityManager entityManager) {

        super(entityManager);

        this.entityManager = entityManager;

        this.interfaceToEntityClassMap = entityManager.getMetamodel().getEntities().stream()
                .flatMap(et -> Arrays.stream(et.getJavaType().getInterfaces())
                        .map(it -> new AbstractMap.SimpleImmutableEntry<>(it, et.getJavaType())))
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (possibleDuplicateInterface, v) -> v));

    }

    public InterfaceBasedJpaRepositoryFactory(EntityManager entityManager, boolean paramAllowNonInterfaceTypes) {
        this(entityManager);
        this.allowNonInterfaceTypes = paramAllowNonInterfaceTypes;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T, ID> JpaEntityInformation<T, ID> getEntityInformation(@NotNull Class<T> domainClass) {

        JpaEntityInformation<T, ID> ret;
        // Interface entities
        if (!this.allowNonInterfaceTypes) {
            Assert.isTrue(domainClass.isInterface(),
                    "You are using interface based jpa repository support. The entity type used in DAO should be an interface");

            Class<?> entityClass = this.interfaceToEntityClassMap.get(domainClass);

            Assert.notNull(entityClass, String.format("Entity class for a interface %s not found!", domainClass));

            ret = (JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getEntityInformation(entityClass, entityManager);
        }else {
            // Class entities
            ret = super.getEntityInformation(domainClass);
        }
        return ret;
    }

    @NotNull
    @Override
    protected RepositoryMetadata getRepositoryMetadata(@NotNull Class<?> repositoryInterface) {
        RepositoryMetadata ret = super.getRepositoryMetadata(repositoryInterface);
        Class<?> clazz = ret.getClass();
        try {
            Field f = clazz.getDeclaredField("domainType");
            boolean isAccessible = f.isAccessible();
            f.setAccessible(true);
            Class<?> actualValue = (Class<?>) f.get(ret);
            Class<?> newValue = this.interfaceToEntityClassMap.get(actualValue);
            f.set(ret, newValue);
            f.setAccessible(isAccessible);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return ret;
    }

}