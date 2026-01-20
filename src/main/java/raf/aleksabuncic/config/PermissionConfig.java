package raf.aleksabuncic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import raf.aleksabuncic.security.CustomPermissionEvaluator;

@Configuration
public class PermissionConfig {
    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new CustomPermissionEvaluator();
    }
}
