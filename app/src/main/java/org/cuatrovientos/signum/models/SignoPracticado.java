package org.cuatrovientos.signum.models;

import org.cuatrovientos.signum.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SignoPracticado extends RealmObject {
    @PrimaryKey
    private int id;
    private int categoriaId;
    private int signoId;

    public SignoPracticado() {
    }

    public SignoPracticado(int categoriaId, int signoId) {
        this.id = MyApplication.signoPracticadoID.incrementAndGet();
        this.categoriaId = categoriaId;
        this.signoId = signoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getSignoId() {
        return signoId;
    }

    public void setSignoId(int signoId) {
        this.signoId = signoId;
    }
}
