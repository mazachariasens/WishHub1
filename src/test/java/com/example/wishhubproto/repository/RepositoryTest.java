package com.example.wishhubproto.repository;

import com.example.wishhubproto.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RepositoryTest {

    @InjectMocks
    private Repository repository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private RowMapper<User> userRowMapper;

    @Test
    void testAuthenticateUser_Success() {
        User inputUser = new User("john", "hashedPassword", "john@example.com");
        User foundUser = new User("john", "hashedPassword", "john@example.com");

        when(jdbcTemplate.queryForObject(anyString(), eq(userRowMapper), eq("john"))).thenReturn(foundUser);

        User result = repository.authenticateUser(inputUser);

        assertNotNull(result);
        assertEquals("john", result.getUserName());
        assertEquals("hashedPassword", result.getPasswordHash());
    }

    @Test
    void testAuthenticateUser() {
        User inputUser = new User("2", "john", "hashedPassword", "john@example.com", LocalDateTime.of(20, 05, 2025, 14, 23, 12);
        User foundUser = new User("2", "john", "hashedPassword", "john@example.com", LocalDateTime.of(20, 05, 2025, 14, 23, 12);

        when(jdbcTemplate.queryForObject(anyString(), eq(userRowMapper), eq("john"))).thenReturn(foundUser);

        User result = repository.authenticateUser(inputUser);

        assertNotNull(result);
        assertEquals("john", result.getUserName());
        assertEquals("hashedPassword", result.getPasswordHash());
    }
    }
    @Test
    void authenticateUser() {

    }

}