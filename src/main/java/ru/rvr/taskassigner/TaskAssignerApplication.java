package ru.rvr.taskassigner;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.rvr.taskassigner.configuration.InterfaceBasedJpaRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(value = "ru.rvr.taskassigner",
        repositoryFactoryBeanClass = InterfaceBasedJpaRepositoryFactoryBean.class)
public class TaskAssignerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskAssignerApplication.class, args);
    }


}
