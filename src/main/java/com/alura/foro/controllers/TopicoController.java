package com.alura.foro.controllers;

import com.alura.foro.dtos.ActualizarTopico;
import com.alura.foro.dtos.CrearTopico;
import com.alura.foro.dtos.RespuestaTopico;
import com.alura.foro.repositories.TopicoRepository;
import com.alura.foro.models.Topico;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private final TopicoRepository topicoRepository;

    public TopicoController(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @GetMapping
    public ResponseEntity<List<RespuestaTopico>> getAllTopics(@PageableDefault(size = 10)Pageable page) {
        return ResponseEntity.ok(topicoRepository.findAll(page).map(RespuestaTopico::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopico> getTopicById(@PathVariable Integer id) {
        Topico topico = topicoRepository.findById(id).orElse(null);

        if (topico == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new RespuestaTopico(topico));
    }

    @PostMapping
    public ResponseEntity<Integer> createTopic(@RequestBody @Valid CrearTopico crearTopico) {
        try {
            Topico topico = new Topico();
            topico.setTitulo(crearTopico.titulo());
            topico.setMensaje(crearTopico.mensaje());
            topico.setAutor(crearTopico.autor());
            topico.setCurso(crearTopico.curso());
            return ResponseEntity.ok(topicoRepository.save(topico).getId());
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "Ya existe un tópico con ese mismo título y mensaje.")).build();
            }

            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "Ha ocurrido un error inesperado")).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTopic(@PathVariable Integer id, @RequestBody @Valid ActualizarTopico actualizarTopico) {
        Topico topico = topicoRepository.findById(id).orElse(null);

        if (topico == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            topico.setTitulo(actualizarTopico.titulo());
            topico.setMensaje(actualizarTopico.mensaje());
            topico.setAutor(actualizarTopico.autor());
            topico.setCurso(actualizarTopico.curso());
            topicoRepository.save(topico);
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "Ya existe un tópico con ese mismo título y mensaje.")).build();
            }

            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "Ha ocurrido un error inesperado")).build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Integer id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
