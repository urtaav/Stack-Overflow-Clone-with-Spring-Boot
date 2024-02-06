package com.urtaav.dtos;

public record LoginResponse(
        String jwt,
        Long userId
) {
}