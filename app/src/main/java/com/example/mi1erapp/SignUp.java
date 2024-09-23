package com.example.mi1erapp;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.text.InputType;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private EditText editnombre, editedad;
    private Button listo, irmenu, ver;
    private RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editnombre = findViewById(R.id.editnombre);
        editedad = findViewById(R.id.editedad);
        listo = findViewById(R.id.listo);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        irmenu = findViewById(R.id.irmenu);
        ver = findViewById(R.id.ver);

        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), ViewSingUp.class);
                startActivity(i);

            }
        });

        // Listener para el botón irmenu
        irmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para obtener el género seleccionado
    private String getSelectedGender() {
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString() : "No seleccionado";
    }

    // Método para insertar el registro
    public void insertar() {
        try {
            String nombre = editnombre.getText().toString();
            String edadStr = editedad.getText().toString();
            String genero = getSelectedGender();

            if (nombre.isEmpty() || edadStr.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show();
                return;
            }

            int edad = Integer.parseInt(edadStr);
            if (edad < 0) {
                Toast.makeText(this, "La edad no puede ser negativa.", Toast.LENGTH_LONG).show();
                return;
            }

            // Cambia el nombre de la base de datos aquí
            SQLiteDatabase db = openOrCreateDatabase("ingreso", Context.MODE_PRIVATE, null);

            db.execSQL("CREATE TABLE IF NOT EXISTS persona(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR, edad INTEGER, genero VARCHAR)");

            String sql = "INSERT INTO persona(nombre, edad, genero) VALUES(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, nombre);
            statement.bindLong(2, edad);
            statement.bindString(3, genero);

            statement.execute();

            Toast.makeText(this, "Datos agregados satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();

            editnombre.setText("");
            editedad.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese una edad válida.", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Error: no se pudieron guardar los datos: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void borrarDatos() {
        try {
            // Cambia el nombre de la base de datos aquí
            SQLiteDatabase db = openOrCreateDatabase("ingreso", Context.MODE_PRIVATE, null);
            db.execSQL("DELETE FROM persona");

            Toast.makeText(this, "Todos los datos han sido borrados.", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(this, "Error al borrar los datos: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

