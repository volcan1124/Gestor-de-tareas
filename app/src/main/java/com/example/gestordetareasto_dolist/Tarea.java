package com.example.gestordetareasto_dolist;

public class Tarea {

    private String id;
    private String tarea;
    private String fecha;
    private boolean completada;
    private String prioridad;
    private String uidUsuario;

    public Tarea() {
        // Constructor vacío requerido por Firestore
    }

    public Tarea(String id,
                 String tarea,
                 String fecha,
                 boolean completada,
                 String prioridad,
                 String uidUsuario) {

        this.id = id;
        this.tarea = tarea;
        this.fecha = fecha;
        this.completada = completada;
        this.prioridad = prioridad;
        this.uidUsuario = uidUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }
}