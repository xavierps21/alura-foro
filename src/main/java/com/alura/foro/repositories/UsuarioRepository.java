package com.alura.foro.repositories;

import com.alura.foro.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreoElectronico(String correoElectronico);
    Usuario findByCorreoElectronico(String correoElectronico);
}
