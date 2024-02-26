
package org.cuatrovientos.signum.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.models.Categoria;
import org.cuatrovientos.signum.models.Signo;
import org.cuatrovientos.signum.models.SignoPracticado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class OnePractiseActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btnOpcion1, btnOpcion2, btnOpcion3, btnOpcion4 ,btnReset;
    private int posicionCorrecta;
    private  Signo signoCorrecto ;
    private int categoriaId;
    private Realm realm;
    private List<Signo> signosList = new ArrayList<>();

    private TextView txtSignos;
    private TextView txtPuntos;
    private TextView txtProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_practise);

        txtSignos = findViewById(R.id.txtSignos);
        txtPuntos = findViewById(R.id.txtPuntos);
        txtProgreso = findViewById(R.id.txtProgreso);
        
        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            categoriaId = intent.getIntExtra("id", -1);
            actualizarInformacion();
        }

        imageView = findViewById(R.id.imageView);
        btnOpcion1 = findViewById(R.id.btnOpcion1);
        btnOpcion2 = findViewById(R.id.btnOpcion2);
        btnOpcion3 = findViewById(R.id.btnOpcion3);
        btnOpcion4 = findViewById(R.id.btnOpcion4);
        btnReset = findViewById(R.id.btnReseteas);

        cargarYMezclarSignos();

        mostrarSiguientePregunta();

        // Configurar clics en botones
        btnOpcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarRespuesta(1);
            }
        });
        btnOpcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarRespuesta(2);
            }
        });
        btnOpcion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarRespuesta(3);
            }
        });
        btnOpcion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarRespuesta(4);
            }
        });
    }
    //Funcional
    private void actualizarInformacion() {



        RealmResults<Signo> signosTotales = realm.where(Signo.class).equalTo("categoriaId", categoriaId).findAll();
        int totalSignos = signosTotales.size();
        Log.i("Total", ""+totalSignos);

        // Obtener la cantidad de signos practicados
        RealmResults<SignoPracticado> signosPracticados = realm
                .where(SignoPracticado.class)
                .equalTo("categoriaId", categoriaId)
                .findAll();
        int signosPracticadosCount = signosPracticados.size();
        Log.i("Practicadas", ""+signosPracticadosCount);



        // Calcular el progreso
        int porcentajeProgreso = (totalSignos == 0) ? 0 : (signosPracticadosCount * 100 / totalSignos);

        // Actualizar los TextViews
        txtSignos.setText(""+signosPracticadosCount);
        txtPuntos.setText(""+signosPracticadosCount*5 );
        txtProgreso.setText(""+porcentajeProgreso + "%");
        actualizarProgreso(categoriaId,porcentajeProgreso);


    }


    private void procesarRespuesta(int opcionSeleccionada) {


        if (opcionSeleccionada == posicionCorrecta) {
            realm.beginTransaction();
            SignoPracticado signoPracticado = new SignoPracticado(categoriaId,signoCorrecto.getId());
            realm.copyToRealm(signoPracticado);
            realm.commitTransaction();


            actualizarInformacion();
            signosList.remove(signoCorrecto);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(0);
                    mostrarSiguientePregunta();
                }
            }, 50);
        } else {
            imageView.setImageResource(signoCorrecto.getImagen());
            switch (opcionSeleccionada) {
                case 1:
                    btnOpcion1.setEnabled(false);
                    break;
                case 2:
                    btnOpcion2.setEnabled(false);
                    break;
                case 3:
                    btnOpcion3.setEnabled(false);
                    break;
                case 4:
                    btnOpcion4.setEnabled(false);
                    break;
            }
        }

    }

    //Funcional

    private List<Signo> devolverListaSignosPracticados(){
        RealmResults<SignoPracticado> signoResults = realm.where(SignoPracticado.class).equalTo("categoriaId", categoriaId).findAll();
        List<Signo> signosPracticadosList = new ArrayList<>();
        for (SignoPracticado signo : signoResults) {
            signosPracticadosList.add(new Signo(signo.getSignoId(),""));

        }
        return signosPracticadosList ;
    }
    private void cargarYMezclarSignos() {
        RealmResults<Signo> signoResults = realm.where(Signo.class).equalTo("categoriaId", categoriaId).findAll();



        for (Signo signo : signoResults) {
            Signo signoModel = new Signo(signo.getId(), signo.getTitulo(), signo.getImagen());
            signosList.add(signoModel);
        }
        /*
        int limite = Math.min(signoResults.size(), 10);
        for (int i = 0; i < limite; i++) {
            Signo signo = signoResults.get(i);
            Signo signoModel = new Signo(signo.getId(), signo.getTitulo(), signo.getImagen());
            signosList.add(signoModel);
        }*/

        signosList.removeAll(devolverListaSignosPracticados());

        Collections.shuffle(signosList);
    }

    private void mostrarSiguientePregunta() {
        btnOpcion1.setEnabled(true);
        btnOpcion2.setEnabled(true);
        btnOpcion3.setEnabled(true);
        btnOpcion4.setEnabled(true);

        if (signosList != null && !signosList.isEmpty()) {
            signoCorrecto = signosList.get(0);

            actualizarInterfaz(signoCorrecto);
        } else {
            btnOpcion1.setVisibility(View.INVISIBLE);
            btnOpcion2.setVisibility(View.INVISIBLE);
            btnOpcion3.setVisibility(View.INVISIBLE);
            btnOpcion4.setVisibility(View.INVISIBLE);
            btnReset.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.felicidades1);
            Toast.makeText(this, "¡Felicidades, has completado todas las preguntas!", Toast.LENGTH_SHORT).show();

            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(), "Empezar de nuevo", Toast.LENGTH_SHORT).show();
                    realm.beginTransaction();
                    realm.delete(SignoPracticado.class);
                    realm.commitTransaction();
                    btnOpcion1.setVisibility(View.VISIBLE);
                    btnOpcion2.setVisibility(View.VISIBLE);
                    btnOpcion3.setVisibility(View.VISIBLE);
                    btnOpcion4.setVisibility(View.VISIBLE);
                    btnReset.setVisibility(View.GONE);
                    recreate();
                    cargarYMezclarSignos();
                    mostrarSiguientePregunta();

                }
            });
        }
    }


    private void actualizarInterfaz(Signo signo) {

        imageView.setImageResource(signo.getImagen());
        asignarOpciones(signo);

    }

    private void asignarOpciones(Signo signo) {

        List<Integer> posicionesDisponibles = new ArrayList<>(Arrays.asList(1, 2, 3, 4));

        posicionCorrecta = posicionesDisponibles.remove((int) (Math.random() * posicionesDisponibles.size()));
        asignarOpcion(posicionCorrecta, signo.getTitulo());

        for (int i = 1; i <= 4; i++) {
            if (i != posicionCorrecta){
                String respuestaIncorrecta = obtenerRespuestaIncorrecta(signo);
                asignarOpcion(i, respuestaIncorrecta);
            }
        }
    }
    List<Signo> palabrasIncorrectas =new  ArrayList<>();
    private String obtenerRespuestaIncorrecta(Signo signo) {
        List<Signo> signosExceptoActual = new ArrayList<>(signosList);
        signosExceptoActual.remove(signo);
        Signo respuestaIncorrecta ;
        if (signosExceptoActual.size() > 4) {
            int indiceRespuestaIncorrecta;

            do {
                indiceRespuestaIncorrecta = (int) (Math.random() * signosExceptoActual.size());
                 respuestaIncorrecta = signosExceptoActual.get(indiceRespuestaIncorrecta);
            }while (palabrasIncorrectas.contains(respuestaIncorrecta));

            if (palabrasIncorrectas.size()<=4){
                palabrasIncorrectas.add(respuestaIncorrecta);
            }else{
                palabrasIncorrectas.clear();

            }
            return respuestaIncorrecta.getTitulo();
        }else{
            int indiceRespuestaIncorrecta;
            RealmResults resultsSigno = realm.where(Signo.class).equalTo("categoriaId", categoriaId).findAll();
            List<Signo> listResults = new ArrayList<>(resultsSigno);
            listResults.remove(signo);
            do {
                indiceRespuestaIncorrecta = (int) (Math.random() * listResults.size());
                respuestaIncorrecta = listResults.get(indiceRespuestaIncorrecta);
            }while (palabrasIncorrectas.contains(respuestaIncorrecta) || respuestaIncorrecta.equals(signo));
            if (palabrasIncorrectas.size()<=4){
                palabrasIncorrectas.add(respuestaIncorrecta);
            }else{
                palabrasIncorrectas.clear();

            }
            return respuestaIncorrecta.getTitulo();
        }
    }



    private void asignarOpcion(int opcion, String texto) {
        switch (opcion) {
            case 1:
                btnOpcion1.setText(texto);
                break;
            case 2:
                btnOpcion2.setText(texto);
                break;
            case 3:
                btnOpcion3.setText(texto);
                break;
            case 4:
                btnOpcion4.setText(texto);
                break;
        }
    }

    private void actualizarProgreso(int categoriaId , int progreso){


        realm.beginTransaction();
        Categoria categoriaAnterior = realm.where(Categoria.class)
                .equalTo("id", categoriaId)
                .findFirst();
        Categoria nuevaCategoria = new Categoria(categoriaAnterior.getId(), categoriaAnterior.getNombre(),categoriaAnterior.getImagen(),progreso);
        realm.copyToRealmOrUpdate(nuevaCategoria);
        realm.commitTransaction();

    }

}