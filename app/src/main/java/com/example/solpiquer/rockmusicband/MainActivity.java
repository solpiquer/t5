package com.example.solpiquer.rockmusicband;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_nombre, et_genero, et_descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_nombre = (EditText)findViewById(R.id.txt_nombre);
        et_genero = (EditText)findViewById(R.id.txt_genero);
        et_descripcion = (EditText)findViewById(R.id.txt_descripcion);

    }

    //Méotdo para dar de alta Bandas
    public void Registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String nombre = et_nombre.getText().toString();
        String genero = et_genero.getText().toString();
        String descripcion = et_descripcion.getText().toString();


        if(!codigo.isEmpty() && !nombre.isEmpty() && !genero.isEmpty() && !descripcion.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("genero", genero);
            registro.put("descripcion", descripcion);


            BaseDeDatos.insert("banda", null, registro);

            BaseDeDatos.close();
            et_codigo.setText("");
            et_nombre.setText("");
            et_genero.setText("");
            et_descripcion.setText("");


            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para consultar una banda
    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select nombre, genero, descripcion from banda where codigo =" + codigo, null);

            if(fila.moveToFirst()){
                et_nombre.setText(fila.getString(0));
                et_genero.setText(fila.getString(1));
                et_descripcion.setText(fila.getString(2));

                BaseDeDatabase.close();
            } else {
                Toast.makeText(this,"No existe la banda", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }

        } else {
            Toast.makeText(this, "Debes introducir el código de la banda", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para eliminar una banda
    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){

            int cantidad = BaseDatabase.delete("banda", "codigo=" + codigo, null);
            BaseDatabase.close();

            et_codigo.setText("");
            et_nombre.setText("");
            et_genero.setText("");
            et_descripcion.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Banda eliminada exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La banda no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes de introducir el código de la banda", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para modificar un artículo o producto
    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String nombre = et_nombre.getText().toString();
        String genero = et_genero.getText().toString();
        String descripcion = et_descripcion.getText().toString();


        if(!codigo.isEmpty() && !nombre.isEmpty() && !genero.isEmpty() && !descripcion.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("genero", genero);
            registro.put("descripcion", descripcion);

            int cantidad = BaseDatabase.update("banda", registro, "codigo=" + codigo, null);
            BaseDatabase.close();

            if(cantidad == 1){
                Toast.makeText(this, "Banda modificada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La banda no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
