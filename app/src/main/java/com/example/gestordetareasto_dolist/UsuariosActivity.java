package com.example.gestordetareasto_dolist;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UsuariosActivity extends AppCompatActivity {

    private RecyclerView rvUsuarios;

    private FirebaseFirestore db;

    private ArrayList<Usuario> listaUsuarios;

    private UsuariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        rvUsuarios = findViewById(R.id.rvUsuarios);

        rvUsuarios.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        listaUsuarios = new ArrayList<>();

        adapter = new UsuariosAdapter(listaUsuarios, usuario -> cambiarRol(usuario));

        rvUsuarios.setAdapter(adapter);

        cargarUsuarios();
    }

    private void cargarUsuarios() {

        db.collection("usuarios")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    listaUsuarios.clear();

                    queryDocumentSnapshots.forEach(documento -> {

                        Usuario usuario = documento.toObject(Usuario.class);

                        listaUsuarios.add(usuario);

                    });

                    adapter.notifyDataSetChanged();

                })
                .addOnFailureListener(e ->

                        Toast.makeText(this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show()

                );

    }

    private void cambiarRol(Usuario usuario) {

        String nuevoRol;

        if (usuario.getRol().equals("administrador")) {

            nuevoRol = "empleado";

        } else {

            nuevoRol = "administrador";

        }

        db.collection("usuarios")
                .whereEqualTo("correo", usuario.getCorreo())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    queryDocumentSnapshots.forEach(documento ->

                            documento.getReference()
                                    .update("rol", nuevoRol)

                    );

                    Toast.makeText(this,
                            "Rol actualizado",
                            Toast.LENGTH_SHORT).show();

                    cargarUsuarios();

                });

    }

}