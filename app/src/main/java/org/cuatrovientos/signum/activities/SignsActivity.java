package org.cuatrovientos.signum.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.adapters.SignoAdapter;
import org.cuatrovientos.signum.models.Signo;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class SignsActivity extends AppCompatActivity {

    Realm realm;
    RealmResults<Signo> results;
    SignoAdapter adapter ;
    RecyclerView recyclerViewSigno ;

    TextView tvId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerViewSigno = findViewById(R.id.recyclerViewSigno);
        tvId = findViewById(R.id.tvId);



        int id = getIntent().getIntExtra("categoriaId", -1);
        tvId.setText(""+getIntent().getExtras().getString("categoriaNombre"));

        realm = Realm.getDefaultInstance();

        results = realm.where(Signo.class).equalTo("categoriaId", id).findAll();
        adapter = new SignoAdapter(results);
        recyclerViewSigno.setAdapter(adapter);
        recyclerViewSigno.setLayoutManager(new GridLayoutManager(this,1));

        results.addChangeListener(new RealmChangeListener<RealmResults<Signo>>() {
            @Override
            public void onChange(RealmResults<Signo> signos) {
                adapter.notifyDataSetChanged();
            }
        });

    }
}