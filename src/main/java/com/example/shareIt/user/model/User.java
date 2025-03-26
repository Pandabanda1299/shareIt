package com.example.shareIt.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class User {
    Long id;
    @NotNull
    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна соответствовать шаблону name@domain.xx")
    String email;
    @NotNull
    @NotBlank(message = "Имя не может быть пустым")
    String name;
}
