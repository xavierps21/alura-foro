package com.alura.foro.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CrearTopico (
        @NotNull
        @NotBlank
        @Length(max = 128)
        String titulo,
        @NotNull
        @NotBlank
        @Length(max = 128)
        String mensaje,
        @NotNull
        @NotBlank
        @Length(max = 128)
        String autor,
        @NotNull
        @NotBlank
        @Length(max = 128)
        String curso) { }
