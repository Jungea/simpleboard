package kr.kro.simpleboard.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    // 추후 Spring Security 붙이면 여기서 AuditorAware 설정도 할 예정
}