package org.cuatrovientos.signum.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.adapters.CardViewSignoAdapter;
import org.cuatrovientos.signum.models.Signo;

import java.util.List;

import io.realm.Realm;

public class OneSignActivity extends AppCompatActivity {

    private Realm realm;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_sign);
        realm = Realm.getDefaultInstance();

        recyclerView = findViewById(R.id.recyclerViewItems);

        int signoId = getIntent().getIntExtra("id", -1);

        if (signoId != -1) {
            Signo signo = realm.where(Signo.class).equalTo("id", signoId).findFirst();

            if (signo != null) {
                updateUI(signo);
                setupRecyclerView(signo.getCategoriaId(), signoId);
            } else {
                Toast.makeText(this, "No se pudo encontrar el signo con ID: " + signoId, Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "ID de signo no vÃ¡lido", Toast.LENGTH_SHORT).show();
            finish();
        }

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(view -> finish());
    }

    private void updateUI(Signo signo) {
        ImageView imageViewSign = findViewById(R.id.imageViewSign);
        TextView textViewSign = findViewById(R.id.textViewSign);

        imageViewSign.setImageResource(signo.getImagen());
        textViewSign.setText(signo.getTitulo());
    }

    private void setupRecyclerView(int categoriaId, int excludeSignoId) {
        List<Signo> signos = realm.where(Signo.class)
                .equalTo("categoriaId", categoriaId)
                .notEqualTo("id", excludeSignoId)
                .findAll();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CardViewSignoAdapter adapter = new CardViewSignoAdapter(signos);
        recyclerView.setAdapter(adapter);
    }

}