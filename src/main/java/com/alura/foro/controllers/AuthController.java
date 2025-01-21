package com.alura.foro.controllers;

import com.alura.foro.dtos.IniciarSesion;
import com.alura.foro.dtos.JwtToken;
import com.alura.foro.dtos.RegistrarUsuario;
import com.alura.foro.models.Usuario;
import com.alura.foro.repositories.UsuarioRepository;
import com.alura.foro.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public AuthController(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("register")
    public ResponseEntity<ProblemDetail> register(@RequestBody @Valid RegistrarUsuario registrarUsuario) {
        if (usuarioRepository.existsByCorreoElectronico(registrarUsuario.correoElectronico())) {
            var problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "Correo no disponible");
            return ResponseEntity.of(problem).build();
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registrarUsuario.nombre());
        usuario.setApellido(registrarUsuario.apellido());
        usuario.setCorreoElectronico(registrarUsuario.correoElectronico());
        usuario.setContrasena(passwordEncoder.encode(registrarUsuario.contrasena()));
        usuarioRepository.save(usuario);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("login")
    public ResponseEntity<JwtToken> login(@RequestBody @Valid IniciarSesion iniciarSesion) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(iniciarSesion.correoElectronico());

        if (usuario == null) {
            return ResponseEntity.status(401).build();
        }

        if (!passwordEncoder.matches(iniciarSesion.contrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(new JwtToken(tokenService.generateToken(usuario)));
    }
}