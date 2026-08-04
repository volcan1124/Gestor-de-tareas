package com.example.gestordetareasto_dolist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import com.google.firebase.firestore.DocumentSnapshot;

import android.app.DatePickerDialog;
import java.util.Calendar;

import android.widget.Spinner;

public class TareasActivity extends AppCompatActivity {

    private EditText etTarea, etFecha;
    private Spinner spPrioridad;

    private Button btnGuardar;
    private Button btnEditar;
    private Button btnBorrar;
    private Button btnBuscar;
    private Button btnCompletar;
    private Button btnVerTodos;

    private RecyclerView rvTareas;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private ArrayList<Tarea> listaTareas;
    private TareaAdapater adapter;

    private Tarea tareaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTarea = findViewById(R.id.etTarea);
        etFecha = findViewById(R.id.etFecha);
        spPrioridad = findViewById(R.id.spPrioridad);
        etFecha.setOnClickListener(v -> mostrarCalendario());

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVerTodos = findViewById(R.id.btnVerTodos);
        btnCompletar = findViewById(R.id.btnCompletar);

        rvTareas = findViewById(R.id.rvTareas);
        rvTareas.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        listaTareas = new ArrayList<>();

        adapter = new TareaAdapater(listaTareas, tarea -> {

            tareaSeleccionada = tarea;

            etTarea.setText(tarea.getTarea());
            etFecha.setText(tarea.getFecha());

            String prioridad = tarea.getPrioridad();

            if (prioridad != null) {

                for (int i = 0; i < spPrioridad.getCount(); i++) {

                    if (spPrioridad.getItemAtPosition(i)
                            .toString()
                            .equals(prioridad)) {

                        spPrioridad.setSelection(i);
                        break;
                    }
                }
            }

            Toast.makeText(
                    TareasActivity.this,
                    "Tarea seleccionada",
                    Toast.LENGTH_SHORT
            ).show();

        });

        rvTareas.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> guardarTarea());

        btnEditar.setOnClickListener(v -> editarTarea());

        btnBorrar.setOnClickListener(v -> eliminarTarea());

        btnBuscar.setOnClickListener(v -> buscarTarea());

        btnVerTodos.setOnClickListener(v -> cargarTareas());

        btnCompletar.setOnClickListener(v -> completarTarea());
    }
    private void guardarTarea() {

        String titulo = etTarea.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String prioridad = spPrioridad.getSelectedItem().toString();

        if (titulo.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = auth.getCurrentUser().getUid();

        Tarea tarea = new Tarea(
                "",
                titulo,
                fecha,
                false,
                prioridad,
                uid
        );

        db.collection("tareas")
                .add(tarea)
                .addOnSuccessListener(documentReference -> {

                    documentReference.update("id", documentReference.getId());

                    etTarea.setText("");
                    etFecha.setText("");
                    spPrioridad.setSelection(1);

                    Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void cargarTareas() {

        String uid = auth.getCurrentUser().getUid();

        db.collection("tareas")
                .whereEqualTo("uidUsuario", uid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    listaTareas.clear();

                    for (DocumentSnapshot documento : queryDocumentSnapshots) {

                        Tarea tarea = documento.toObject(Tarea.class);

                        if (tarea != null) {

                            tarea.setId(documento.getId());

                            listaTareas.add(tarea);
                        }
                    }

                    // Ordenar por prioridad y estado
                    listaTareas.sort((t1, t2) -> {

                        int prioridad1 = obtenerValorPrioridad(t1.getPrioridad());
                        int prioridad2 = obtenerValorPrioridad(t2.getPrioridad());

                        if (prioridad1 != prioridad2) {
                            return Integer.compare(prioridad1, prioridad2);
                        }

                        if (t1.isCompletada() == t2.isCompletada()) {
                            return 0;
                        }

                        return t1.isCompletada() ? 1 : -1;
                    });

                    adapter.notifyDataSetChanged();

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }

    private void eliminarTarea() {

        if (tareaSeleccionada == null) {
            Toast.makeText(this,
                    "Selecciona una tarea",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("tareas")
                .document(tareaSeleccionada.getId())
                .delete()
                .addOnSuccessListener(unused -> {

                    Toast.makeText(this,
                            "Tarea eliminada",
                            Toast.LENGTH_SHORT).show();

                    etTarea.setText("");
                    etFecha.setText("");

                    tareaSeleccionada = null;

                    cargarTareas();

                })
                .addOnFailureListener(e ->

                        Toast.makeText(this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show()

                );
    }

    private void editarTarea() {

        if (tareaSeleccionada == null) {
            Toast.makeText(this,
                    "Selecciona una tarea",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String titulo = etTarea.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String prioridad = spPrioridad.getSelectedItem().toString();

        if (titulo.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        tareaSeleccionada.setTarea(titulo);
        tareaSeleccionada.setFecha(fecha);
        tareaSeleccionada.setPrioridad(prioridad);

        db.collection("tareas")
                .document(tareaSeleccionada.getId())
                .set(tareaSeleccionada)
                .addOnSuccessListener(unused -> {

                    Toast.makeText(this,
                            "Tarea actualizada",
                            Toast.LENGTH_SHORT).show();

                    etTarea.setText("");
                    etFecha.setText("");
                    spPrioridad.setSelection(1);

                    tareaSeleccionada = null;

                    cargarTareas();

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }

    private void buscarTarea() {

        String texto = etTarea.getText().toString().trim();

        if (texto.isEmpty()) {
            cargarTareas();
            return;
        }

        listaTareas.clear();

        db.collection("tareas")
                .whereEqualTo("uidUsuario", auth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (var documento : queryDocumentSnapshots) {

                        Tarea tarea = documento.toObject(Tarea.class);
                        tarea.setId(documento.getId());

                        if (tarea.getTarea().toLowerCase().contains(texto.toLowerCase())) {
                            listaTareas.add(tarea);
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (listaTareas.isEmpty()) {
                        Toast.makeText(this,
                                "No se encontraron resultados",
                                Toast.LENGTH_SHORT).show();
                    }

                });

    }

    private void completarTarea() {

        if (tareaSeleccionada == null) {
            Toast.makeText(this,
                    "Selecciona una tarea",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Si está pendiente la marca como completada.
        // Si ya está completada la vuelve a pendiente.
        boolean nuevoEstado = !tareaSeleccionada.isCompletada();

        db.collection("tareas")
                .document(tareaSeleccionada.getId())
                .update("completada", nuevoEstado)
                .addOnSuccessListener(unused -> {

                    tareaSeleccionada.setCompletada(nuevoEstado);

                    if (nuevoEstado) {
                        Toast.makeText(this,
                                "Tarea completada",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,
                                "Tarea marcada como pendiente",
                                Toast.LENGTH_SHORT).show();
                    }

                    etTarea.setText("");
                    etFecha.setText("");

                    tareaSeleccionada = null;

                    cargarTareas();

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }

    private void mostrarCalendario() {

        Calendar calendario = Calendar.getInstance();

        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {

                    String fecha = String.format(
                            "%02d/%02d/%04d",
                            dayOfMonth,
                            month + 1,
                            year
                    );

                    etFecha.setText(fecha);

                },
                año,
                mes,
                dia
        );

        datePickerDialog.show();

    }

    private int obtenerValorPrioridad(String prioridad) {

        if (prioridad == null) {
            return 2; // Media por defecto
        }

        switch (prioridad) {

            case "🔴 Alta":
                return 1;

            case "🟡 Media":
                return 2;

            case "🟢 Baja":
                return 3;

            default:
                return 2;
        }
    }

}