package org.cuatrovientos.signum.models;

import org.cuatrovientos.signum.app.MyApplication;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Signo extends RealmObject {
    @PrimaryKey
    private int id;
    private String titulo;

    private int imagen;

    private int categoriaId ;


    public Signo(){

    }
    public Signo(String titulo, int imagen, int categoriaId) {
        this.id = MyApplication.signosId.incrementAndGet();
        this.titulo = titulo;
        this.imagen = imagen;
        this.categoriaId = categoriaId ;

    }
    public Signo(int id , String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Signo(int id , String titulo , int imagen) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
    }


    // Getters y setters
    public int getCategoriaId() {
        return categoriaId;
    }

    // Setter
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signo signo = (Signo) o;
        return id == signo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
/*
    public int getVecesMostrado() {
        return vecesMostrado;
    }

    public void incrementarVecesMostrado() {
        vecesMostrado++;
    }

    public int getVecesCorrectas() {
        return vecesCorrectas;
    }

    public void incrementarVecesCorrectas() {
        vecesCorrectas++;
    }*/
}
