package com.example.shareIt.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateDto {
    Long id;
    @Email(message = "Электронная почта должна соответствовать шаблону name@domain.xx")
    String email;
    String name;
}

