package org.cuatrovientos.signum.models;

import org.cuatrovientos.signum.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Categoria extends RealmObject {
    @PrimaryKey
    private int id;
    private String nombre;
    private int imagen;

    private int progreso ;


    // Constructor
    public Categoria(){

    }
    public Categoria(String nombre, int imagen) {
        this.id = MyApplication.categoriasId.incrementAndGet();
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Categoria(String nombre, int imagen,int progreso) {
        this.id = MyApplication.categoriasId.incrementAndGet();
        this.nombre = nombre;
        this.imagen = imagen;
        this.progreso = progreso;
    }
    public Categoria(int id ,String nombre, int imagen,int progreso) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.progreso = progreso;
    }


    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
