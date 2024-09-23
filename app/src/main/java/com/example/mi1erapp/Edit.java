package com.example.mi1erapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Edit extends AppCompatActivity {

    private EditText e_id,e_nombre,e_edad,e_genero;
    private Button b_guardar, b_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        e_nombre = findViewById(R.id.edit_nombre);
        e_edad = findViewById(R.id.edit_edad);
        e_genero = findViewById(R.id.edit_genero);
        e_id = findViewById(R.id.edit_id);

        b_guardar = findViewById(R.id.guardar);
        b_eliminar = findViewById(R.id.eliminar);

        //Se utiliza getIntent() para obtener el Intent que inició la actividad actual.
        // Un Intent es una forma de pasar información entre actividades en Android.
        Intent i = getIntent();

        //Se emplean los métodos getStringExtra() para recuperar las variables que fueron enviadas
        //desde otra actividad a través del Intent. Estos métodos permiten obtener datos extras con el
        //tipo de dato String. En este caso, se recuperan cuatro datos: id, nombre, apellido, y edad.
        //El método toString() asegura que los valores sean convertidos a cadenas de texto,
        // aunque no es necesario en este caso porque getStringExtra() ya devuelve un String.
        String edit_id = i.getStringExtra("id").toString();
        String edit_nombre = i.getStringExtra("nombre").toString();
        String edit_edad = i.getStringExtra("edad").toString();
        String edit_genero = i.getStringExtra("genero").toString();

        e_id.setText(edit_id);
        e_nombre.setText(edit_nombre);
        e_edad.setText(edit_edad);
        e_genero.setText(edit_genero);

        b_guardar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                guardar();
            }
        });

        b_eliminar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                eliminar();
            }
        });

    }

    public void eliminar()
    {
        try
        {
            //Se obtiene el valor del campo de texto ed_id, que representa el id de la persona
            // que se desea eliminar. Este valor es convertido a un String utilizando
            // el método getText() seguido de toString().
            String id = e_id.getText().toString();

            //Se abre o crea la base de datos "BD_EJEMPLO" utilizando el método openOrCreateDatabase().
            //La base de datos se abre en modo privado (Context.MODE_PRIVATE), lo que significa que
            // solo la aplicación que la creó puede acceder a ella.
            SQLiteDatabase db = openOrCreateDatabase("ingreso", Context.MODE_PRIVATE,null);

            //Se define una sentencia SQL que elimina un registro de la tabla persona cuyo id
            //coincida con el valor proporcionado. La sentencia SQL utiliza un parámetro para el id,
            //lo que previene la inyección de SQL.
            String sql = "delete from persona where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);

            //El valor del id que el usuario proporcionó en el campo de texto es asignado
            //al parámetro de la sentencia SQL utilizando el método bindString().
            statement.bindString(1,id);

            //Se ejecuta la sentencia preparada mediante statement.execute(),
            //lo que borra el registro de la base de datos correspondiente al id especificado.
            statement.execute();


            Toast.makeText(this,"Datos eliminados de la base de datos.",Toast.LENGTH_LONG).show();

            e_nombre.setText("");
            e_edad.setText("");
            e_genero.setText("");
            e_nombre.requestFocus();

        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }

    public void guardar()
    {
        try
        {
            String nombre = e_nombre.getText().toString();
            String edad = e_edad.getText().toString();
            String genero = e_genero.getText().toString();
            String id = e_id.getText().toString();

            // Se abre o crea la base de datos "BD_EJEMPLO" utilizando el método openOrCreateDatabase().
            //La base de datos se abre en modo privado (Context.MODE_PRIVATE),
            // lo que garantiza que solo la aplicación que la creó pueda acceder a ella.
            SQLiteDatabase db = openOrCreateDatabase("ingreso",Context.MODE_PRIVATE,null);

            //Se define una sentencia SQL que realiza una actualización en la tabla persona.
            //Esta sentencia establece nuevos valores para las columnas nombre, apellido, y edad,
            // y lo hace solo para el registro cuyo id coincida con el proporcionado.
            String sql = "update persona set nombre = ?,edad=?,genero=? where id= ?";

            //Se utilizan los métodos bindString() para asignar los valores capturados desde la interfaz
            // de usuario a los parámetros de la sentencia SQL. Estos valores reemplazarán
            // los campos nombre, apellido, y edad en el registro correspondiente al id proporcionado
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,nombre);
            statement.bindString(2,edad);
            statement.bindString(3,genero);
            statement.bindString(4,id);

            //Se ejecuta la sentencia preparada con statement.execute(), lo que actualiza el
            // registro correspondiente en la tabla persona.
            statement.execute();

            Toast.makeText(this,"Datos actualizados satisfactoriamente en la base de datos.",Toast.LENGTH_LONG).show();

            e_nombre.setText("");
            e_edad.setText("");
            e_genero.setText("");
            e_nombre.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }
}
