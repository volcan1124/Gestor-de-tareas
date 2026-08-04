package com.example.gestordetareasto_dolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MenuPrincipalActivity extends AppCompatActivity {

    private TextView tvBienvenido;

    private Button btnTareas;
    private Button btnInventario;
    private Button btnUsuarios;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        tvBienvenido = findViewById(R.id.tvBienvenido);

        btnTareas = findViewById(R.id.btnTareas);
        btnUsuarios = findViewById(R.id.btnUsuarios);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);

        String nombre = prefs.getString("nombre", "Usuario");
        String rol = prefs.getString("rol", "empleado");

        tvBienvenido.setText("Bienvenido " + nombre);

        // Solo el administrador puede acceder al módulo de usuarios
        if (!rol.equalsIgnoreCase("administrador")) {
            btnUsuarios.setVisibility(View.GONE);
        }

        // Módulo de tareas
        btnTareas.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalActivity.this, TareasActivity.class);
            startActivity(intent);
        });


        // Usuarios
        btnUsuarios.setOnClickListener(v -> {

            Intent intent = new Intent(MenuPrincipalActivity.this,
                    UsuariosActivity.class);

            startActivity(intent);

        });

        // Cerrar sesión
        btnCerrarSesion.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            prefs.edit().clear().apply();

            Intent intent = new Intent(MenuPrincipalActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        });
    }
}