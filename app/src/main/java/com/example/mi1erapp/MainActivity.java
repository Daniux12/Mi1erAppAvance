package com.example.mi1erapp;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView, consumidas, restantes;
    private EditText sum1, sum2, sum3, sum4, diarias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        consumidas = findViewById(R.id.consumidas);
        restantes = findViewById(R.id.restantes);
        sum1 = findViewById(R.id.sum1);
        sum2 = findViewById(R.id.sum2);
        sum3 = findViewById(R.id.sum3);
        sum4 = findViewById(R.id.sum4);
        diarias = findViewById(R.id.diarias);
        Button calculateButton = findViewById(R.id.calcular);

        // Configura el listener para el botón de calcular
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCalorias();
            }
        });
    }

    private void calcularCalorias() {
        int calorias1 = obtenerValor(sum1);
        int calorias2 = obtenerValor(sum2);
        int calorias3 = obtenerValor(sum3);
        int calorias4 = obtenerValor(sum4);
        int caloriasDiarias = obtenerValor(diarias);

        int totalCalorias = calorias1 + calorias2 + calorias3 + calorias4;

        consumidas.setText("Consumidas: " + totalCalorias);

        if (caloriasDiarias > 0) {
            int restantesCalorias = caloriasDiarias - totalCalorias;
            restantes.setText("Restantes: " + restantesCalorias);
        } else {
            restantes.setText("Por favor, ingresa un valor válido para las calorías diarias.");
        }

    }

    private int obtenerValor(EditText editText) {
        String texto = editText.getText().toString();
        return TextUtils.isEmpty(texto) ? 0 : Integer.parseInt(texto);
    }
}