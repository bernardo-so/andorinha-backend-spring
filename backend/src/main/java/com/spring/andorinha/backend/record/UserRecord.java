package com.spring.andorinha.backend.record;

import com.spring.andorinha.backend.utils.enums.Role;

public record UserRecord(
        Long id,
        String name,
        String email,
        String password,
        Role role) {
}
