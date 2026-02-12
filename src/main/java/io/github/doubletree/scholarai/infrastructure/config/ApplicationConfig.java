package io.github.doubletree.scholarai.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.repository")
@EnableTransactionManagement
public class ApplicationConfig {
}
