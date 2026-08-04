package com.example.gestordetareasto_dolist;

public class Tarea {

    private int id;
    private String tarea;
    private String fecha;

    public Tarea(int id, String tarea, String fecha){
        this.id = id;
        this.tarea = tarea;
        this.fecha = fecha;
    }

    public int getId() { return id; }

    public String getTarea() {
        return tarea;
    }

    public String getFecha() {
        return fecha;
    }
}
