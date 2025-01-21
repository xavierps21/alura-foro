package com.alura.foro.models;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"titulo", "mensaje"}))
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String mensaje;
    private String autor;
    private String curso;

    public Topico() {}

    public Topico(Integer id, String titulo, String mensaje, String autor, String curso) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setMensaje(mensaje);
        this.setAutor(autor);
        this.setCurso(curso);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String message) {
        this.mensaje = message;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String cursor) {
        this.curso = cursor;
    }
}
