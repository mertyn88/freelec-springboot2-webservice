package com.jojoldu.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  //Application.java에 있던 JPA활성화를 JpaConfig파일로 분리하여 처리
public class JpaConfig {
}
