package com.pragma.users.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.pragma.users.application.dto.request.OwnerRequestDto;
import com.pragma.users.application.mapper.IUserRequestMapper;
import com.pragma.users.application.mapper.IUserRequestMapperImpl;
import com.pragma.users.domain.model.User;

class UserRequestMapperTest {

	IUserRequestMapper mapper = new IUserRequestMapperImpl();

	@Test
	void testOwnerDtoToUser() {
		OwnerRequestDto dto = new OwnerRequestDto();
		dto.setFirstName("Juan");
		dto.setLastName("Perez");
		dto.setIdentityDocument("123456789");
		dto.setPhone("+573005698325");
		dto.setBirthDate(java.time.LocalDate.of(1990, 1, 1));
		dto.setEmail("juan@test.com");
		dto.setPassword("clave");

		User user = mapper.toUser(dto);

		assertEquals("Juan", user.getFirstName());
		assertEquals("Perez", user.getLastName());
		assertEquals("juan@test.com", user.getEmail());
		assertEquals("123456789", user.getIdentityDocument());
		assertEquals("+573005698325", user.getPhone());
		assertEquals(java.time.LocalDate.of(1990, 1, 1), user.getBirthDate());
		assertEquals("clave", user.getPassword());
	}
}