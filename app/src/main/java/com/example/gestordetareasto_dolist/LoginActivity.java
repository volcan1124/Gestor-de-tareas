package com.example.gestordetareasto_dolist;




import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class  LoginActivity extends AppCompatActivity {

    private TextView tvIrRegistro;
    private Button btnLogin;
    private TextInputEditText etLoginCorreo, etLoginPassword;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvIrRegistro = findViewById(R.id.tvIrRegistro);
        btnLogin = findViewById(R.id.btnLogin);
        etLoginCorreo = findViewById(R.id.etLoginCorreo);
        etLoginPassword = findViewById(R.id.etLoginPassword);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MenuPrincipalActivity.class));
            finish();
            return;
        }

        tvIrRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));

        btnLogin.setOnClickListener(v -> iniciarSesion());
    }

    private void iniciarSesion() {

        String correo = etLoginCorreo.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingresa correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);
        btnLogin.setText("Verificando...");

        mAuth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(task -> {

                    btnLogin.setEnabled(true);
                    btnLogin.setText("INICIAR SESIÓN");

                    if (task.isSuccessful()) {

                        String uid = mAuth.getCurrentUser().getUid();

                        db.collection("usuarios")
                                .document(uid)
                                .get()
                                .addOnSuccessListener(document -> {

                                    if (document.exists()) {

                                        SharedPreferences.Editor editor =
                                                getSharedPreferences("SesionUsuario", MODE_PRIVATE).edit();

                                        editor.putString("nombre", document.getString("nombre"));
                                        editor.putString("rol", document.getString("rol"));
                                        editor.apply();

                                        startActivity(new Intent(this, MenuPrincipalActivity.class));
                                        finish();
                                    }

                                });

                    } else {

                        Toast.makeText(this,
                                "Usuario o contraseña incorrectos",
                                Toast.LENGTH_SHORT).show();

                    }

                });
    }
}