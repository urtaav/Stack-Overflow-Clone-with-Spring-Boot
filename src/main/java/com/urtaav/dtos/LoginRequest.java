package com.urtaav.dtos;

public record LoginRequest(
        String email,
        String password
) {
}
