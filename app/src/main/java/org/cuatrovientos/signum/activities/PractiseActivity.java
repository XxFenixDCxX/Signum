package org.cuatrovientos.signum.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.adapters.PracticarAdapter;
import org.cuatrovientos.signum.models.Categoria;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class PractiseActivity extends AppCompatActivity {
    Realm realm;
    RealmResults<Categoria> results;
    PracticarAdapter adapter ;
    RecyclerView recyclerView;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        recyclerView = findViewById(R.id.recycerViewPracticar);
        realm = Realm.getDefaultInstance();
        results = realm.where(Categoria.class).findAll();

        adapter = new PracticarAdapter(results, new PracticarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Categoria categoria, int position) {
                Intent intent = new Intent(PractiseActivity.this, OnePractiseActivity.class);
                int id = categoria.getId();
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });
        bottomNavigationView.getMenu().findItem(R.id.practise).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    int id = item.getItemId();
                    if (id == R.id.diccionary) {
                        overridePendingTransition(0, 0);

                        Intent intent = new Intent(PractiseActivity.this , DiccionaryActivity.class);
                        startActivity(intent);

                        overridePendingTransition(0, 0);

                        return true;
                    } else if (id == R.id.practise) {
                        return true;
                    } else {
                        return false;
                    }
                }
        );
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