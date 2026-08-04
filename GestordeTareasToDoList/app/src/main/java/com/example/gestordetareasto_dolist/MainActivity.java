package com.example.gestordetareasto_dolist;



import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etId, etTarea, etFecha;

    private Button btnGuardar, btnBuscar, btnEditar, btnBorrar, btnVerTodos;

    private RecyclerView rvTareas;

    private TareaAdapater adaptador;

    private List<Tarea> listaTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etTarea = findViewById(R.id.etTarea);
        etFecha = findViewById(R.id.etFecha);

        rvTareas = findViewById(R.id.rvTareas);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnVerTodos = findViewById(R.id.btnVerTodos);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarTarea();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarTarea();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarTarea();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarTarea();
            }
        });

        btnVerTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { cargarListaTareas(); }
        });

        rvTareas.setLayoutManager(new LinearLayoutManager(this));


    }

    private void registrarTarea(){
        String id = etId.getText().toString();
        String tarea = etTarea.getText().toString();
        String fecha = etFecha.getText().toString();

        if (!id.isEmpty() && !tarea.isEmpty() && !fecha.isEmpty()){

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tareas.db", null, 1);
            SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

            ContentValues guardar = new ContentValues();
            guardar.put("id", id);
            guardar.put("tarea", tarea);
            guardar.put("fecha", fecha);


            baseDeDatos.insert("tareas", null, guardar);


            baseDeDatos.close();

            etId.setText("");
            etTarea.setText("");
            etFecha.setText("");

            Toast.makeText(this, "Tarea guardada exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarTarea(){
        String id = etId.getText().toString();

        if (!id.isEmpty()){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tareas.db", null, 1);
            SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

            android.database.Cursor fila = baseDeDatos.rawQuery("SELECT tarea, fecha FROM tareas WHERE id = " + id, null);

            if (fila.moveToFirst()){
                etTarea.setText(fila.getString(0));
                etFecha.setText(fila.getString(1));
                Toast.makeText(this, "Tarea encontrada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe una tarea con ese id", Toast.LENGTH_SHORT).show();
                etTarea.setText("");
                etFecha.setText("");
            }

            baseDeDatos.close();
            fila.close();
        } else {
            Toast.makeText(this, "Debes ingresar el id de la tarea a buscar", Toast.LENGTH_SHORT).show();
        }
    }

    private void borrarTarea(){
        String id = etId.getText().toString();

        if (!id.isEmpty()){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tareas.db", null, 1);
            SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

            int cantidadBorrados = baseDeDatos.delete("tareas", "id=" + id, null);

            baseDeDatos.close();

            etId.setText("");
            etTarea.setText("");
            etFecha.setText("");

            if (cantidadBorrados == 1){
                Toast.makeText(this, "Tarea eliminada exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La tarea no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese el id de la tarea a eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarTarea(){
        String id = etId.getText().toString();
        String tarea = etTarea.getText().toString();
        String fecha = etFecha.getText().toString();

        if (!id.isEmpty() && !tarea.isEmpty() && !fecha.isEmpty()){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tareas.db", null, 1);
            SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

            ContentValues registroNuevo = new ContentValues();
            registroNuevo.put("id", id);
            registroNuevo.put("tarea", tarea);
            registroNuevo.put("fecha", fecha);

            int cantidadActualizados = baseDeDatos.update("tareas", registroNuevo, "id=" + id, null);

            baseDeDatos.close();

            etId.setText("");
            etTarea.setText("");
            etFecha.setText("");

            if (cantidadActualizados == 1){
                Toast.makeText(this, "Tarea actualizada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se encontro tarea para actualizar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarListaTareas(){
        listaTarea = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tareas.db", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        android.database.Cursor fila = bd.rawQuery("SELECT id, tarea, fecha FROM tareas", null);

        while (fila.moveToNext()){
            int id = fila.getInt(0);
            String tarea = fila.getString(1);
            String fecha = fila.getString(2);

            listaTarea.add(new Tarea(id, tarea, fecha));

        }

        bd.close();
        fila.close();

        adaptador = new TareaAdapater(listaTarea);

        rvTareas.setAdapter(adaptador);
    }
}





