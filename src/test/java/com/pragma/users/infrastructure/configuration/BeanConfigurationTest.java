package com.pragma.users.infrastructure.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.pragma.users.domain.usecase.UserUseCase;

@SpringBootTest
class BeanConfigurationTest {

    @Autowired private ApplicationContext context;
    @MockBean
    private UserUseCase userUseCase;

    @Test
    void contextLoads() {
        assertNotNull(userUseCase);
        assertNotNull(context.getBean("passwordEncoder"));
    }
}
