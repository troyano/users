package com.pragma.users.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.pragma.users.application.dto.request.OwnerRequestDto;
import com.pragma.users.domain.model.User;

class IUserRequestMapperImplTest {

    private final IUserRequestMapperImpl mapper = new IUserRequestMapperImpl();

    @Test
    void toUser_shouldMapAllFields() {
        OwnerRequestDto dto = new OwnerRequestDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setIdentityDocument("123456789");
        dto.setPhone("+1234567890");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("john.doe@example.com");
        dto.setPassword("password");

        User user = mapper.toUser(dto);

        assertNotNull(user);
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getIdentityDocument(), user.getIdentityDocument());
        assertEquals(dto.getPhone(), user.getPhone());
        assertEquals(dto.getBirthDate(), user.getBirthDate());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getPassword(), user.getPassword());
    }

    @Test
    void toUser_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toUser(null));
    }
}