package com.example.demo.model;

public class Participante {
    private String nombre;
    private String edad;
    private String telefono;
    private String genero;
    private String categoria;
    private String modalidad;
    private String disciplina;
    private String caracteristicas;
    private String estado;

    public Participante(String nombre, String edad, String telefono, String genero,
                        String categoria, String modalidad, String disciplina,
                        String caracteristicas, String estado) {
        this.nombre = nombre;
        this.edad = edad;
        this.telefono = telefono;
        this.genero = genero;
        this.categoria = categoria;
        this.modalidad = modalidad;
        this.disciplina = disciplina;
        this.caracteristicas = caracteristicas;
        this.estado = estado; // Por defecto será "Inscrito"
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEdad() { return edad; }
    public void setEdad(String edad) { this.edad = edad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public String getCaracteristicas() { return caracteristicas; }
    public void setCaracteristicas(String caracteristicas) { this.caracteristicas = caracteristicas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}