package com.alura.foro.repositories;

import com.alura.foro.models.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Integer> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
