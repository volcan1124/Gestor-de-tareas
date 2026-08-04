package com.example.gestordetareasto_dolist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private TextView tvVolverLogin;
    private Button btnRegistrarUsuario;

    private TextInputEditText etRegistrarNombre;
    private TextInputEditText etRegistrarCorreo;
    private TextInputEditText etRegistrarPassword;
    private TextInputEditText etRegistrarPasswordConf;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        tvVolverLogin = findViewById(R.id.tvVolverLogin);
        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);

        etRegistrarNombre = findViewById(R.id.etRegistrarNombre);
        etRegistrarCorreo = findViewById(R.id.etRegistrarCorreo);
        etRegistrarPassword = findViewById(R.id.etRegistrarPassword);
        etRegistrarPasswordConf = findViewById(R.id.etRegistrarPasswordConf);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvVolverLogin.setOnClickListener(v -> finish());

        btnRegistrarUsuario.setOnClickListener(v -> crearCuentaFirebase());
    }

    private void crearCuentaFirebase() {

        String nombre = etRegistrarNombre.getText().toString().trim();
        String correo = etRegistrarCorreo.getText().toString().trim();
        String password = etRegistrarPassword.getText().toString().trim();
        String confirmacion = etRegistrarPasswordConf.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty()
                || password.isEmpty() || confirmacion.isEmpty()) {

            Toast.makeText(this,
                    "Por favor llena todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this,
                    "La contraseña debe tener mínimo 6 caracteres",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmacion)) {
            Toast.makeText(this,
                    "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        btnRegistrarUsuario.setEnabled(false);
        btnRegistrarUsuario.setText("Creando cuenta...");

        mAuth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener(task -> {

                    btnRegistrarUsuario.setEnabled(true);
                    btnRegistrarUsuario.setText("REGISTRARME");

                    if (task.isSuccessful()) {

                        String uid = mAuth.getCurrentUser().getUid();

                        Map<String, Object> perfilUsuario = new HashMap<>();
                        perfilUsuario.put("nombre", nombre);
                        perfilUsuario.put("correo", correo);
                        perfilUsuario.put("rol", "empleado");

                        db.collection("usuarios")
                                .document(uid)
                                .set(perfilUsuario)
                                .addOnSuccessListener(unused -> {

                                    Toast.makeText(
                                            RegistroActivity.this,
                                            "Cuenta creada exitosamente",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    finish();

                                })
                                .addOnFailureListener(e -> {

                                    Toast.makeText(
                                            RegistroActivity.this,
                                            "Error Firestore: " + e.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();

                                });

                    } else {

                        Exception e = task.getException();

                        Toast.makeText(
                                RegistroActivity.this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                        e.printStackTrace();
                    }

                });
    }
}