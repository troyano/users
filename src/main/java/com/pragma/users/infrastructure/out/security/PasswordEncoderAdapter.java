package com.pragma.users.infrastructure.out.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.pragma.users.domain.spi.IPasswordEncoderPort;

public class PasswordEncoderAdapter implements IPasswordEncoderPort {
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
