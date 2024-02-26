package org.cuatrovientos.signum.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.cuatrovientos.signum.R;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final ImageView logo = findViewById(R.id.logo);
        final TextView signum = findViewById(R.id.signum);

        // Crea la animación para el logo
        Animation logoAnimation = new AlphaAnimation(1, 0);
        logoAnimation.setDuration(1200);
        logoAnimation.setStartOffset(100);
        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.GONE);
                signum.setVisibility(View.VISIBLE);
                signum.setText("");
                final String signumText = "Signum";
                for (int i = 0; i < signumText.length(); i++) {
                    final char letter = signumText.charAt(i);
                    final int index = i;
                    signum.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            signum.append(Character.toString(letter));
                            if (index != signumText.length() - 1) {
                                signum.append("_");
                            }
                        }
                    }, i * 300); // Añade una letra cada 500 milisegundos

                    if (i != signumText.length() - 1) {
                        signum.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                signum.setText(signum.getText().toString().substring(0, signum.getText().length() - 1));
                            }
                        }, i * 300 + 150); // Elimina el "_" 250 milisegundos después
                    }
                }

// Navega a la actividad principal después de la animación
                signum.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoadingActivity.this, DiccionaryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, signumText.length() * 350);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        logo.startAnimation(logoAnimation);
    }
}