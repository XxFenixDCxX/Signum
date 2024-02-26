package org.cuatrovientos.signum.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.adapters.CategoriaAdapter;
import org.cuatrovientos.signum.adapters.FiltroSignosAdapter;
import org.cuatrovientos.signum.models.Categoria;
import org.cuatrovientos.signum.models.Signo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class DiccionaryActivity extends AppCompatActivity {
    Realm realm;
    RealmResults<Categoria> results;
    List<Signo> signoList = new ArrayList<>();

    FiltroSignosAdapter adapterSignos;
    CategoriaAdapter adapter;
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;

    AutoCompleteTextView autoCompleteSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diccionary);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategorias);
        realm = Realm.getDefaultInstance();
        results = realm.where(Categoria.class).findAll();
        autoCompleteSearch = findViewById(R.id.autoCompleteSearch);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.diccionary).setChecked(true);

        // Rellenar palabras
        RealmResults<Signo> signoResults = realm.where(Signo.class).findAll();

        for (Signo signo : signoResults) {
            Signo signoModel = new Signo(signo.getId(),signo.getTitulo());
            signoList.add(signoModel);
        }

        //FILTRAR POR NOMBRE
        adapterSignos = new FiltroSignosAdapter(this, android.R.layout.simple_dropdown_item_1line, signoList);
        autoCompleteSearch.setAdapter(adapterSignos);


        // COnfigurar el click
        autoCompleteSearch.setOnItemClickListener((parent, view, position, id) -> {
            Signo selectedSigno = (Signo) parent.getItemAtPosition(position);
            if (selectedSigno != null) {
                int selectedSignoId = selectedSigno.getId();
                Intent intent = new Intent(DiccionaryActivity.this, OneSignActivity.class);
                intent.putExtra("id", selectedSignoId);
                startActivity(intent);
            }


        });

        // Configuración del Listener para manejar los clics en los elementos del menú

        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    int id = item.getItemId();
                    if (id == R.id.diccionary) {

                        return true;
                    } else if (id == R.id.practise) {
                        overridePendingTransition(0, 0);

                        Intent intent = new Intent(DiccionaryActivity.this , PractiseActivity.class);
                        startActivity(intent);

                        overridePendingTransition(0, 0);

                        return true;
                    } else {
                        return false;
                    }
                }
        );

        adapter = new CategoriaAdapter(results, new CategoriaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Categoria categoria, int position) {
                Intent intent = new Intent(DiccionaryActivity.this, SignsActivity.class);
                int id = categoria.getId();
                intent.putExtra("categoriaId", id);
                intent.putExtra("categoriaNombre", categoria.getNombre());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        results.addChangeListener(new RealmChangeListener<RealmResults<Categoria>>() {
            @Override
            public void onChange(RealmResults<Categoria> categorias) {
                adapter.notifyDataSetChanged();
            }


        });
    }

}