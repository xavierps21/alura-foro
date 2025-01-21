package com.alura.foro.dtos;

import com.alura.foro.models.Topico;

public record RespuestaTopico (Integer id, String titulo, String mensaje, String autor, String curso) {
    public RespuestaTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso());
    }
}
