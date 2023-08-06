package com.example.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PrincipalAdministrativaActivity extends AppCompatActivity {
        TextView nomUsuario;
        Button  agregarClienteNuevo;
        Button asignarPrestamo;
        ImageButton salir;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_administrativa);

        agregarClienteNuevo = findViewById(R.id.btn_AgregarCliente);
        asignarPrestamo = findViewById(R.id.btn_AsignarPrestamo);
        salir = findViewById(R.id.btn_salir);
        nomUsuario = findViewById(R.id.txt_Admin);
        String nomUsuario_str;
        Intent intent = getIntent();

        nomUsuario_str = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        nomUsuario.setText(nomUsuario_str);

        agregarClienteNuevo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pantallaAgregarCliente();
            }
        });

        asignarPrestamo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pantallaAsignarPrestamo();
            }
        });

        salir.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }

    private void pantallaAgregarCliente(){
        Intent intent = new Intent(PrincipalAdministrativaActivity.this, AgregarClienteActivity.class);
        startActivity(intent);
    }
    private void pantallaAsignarPrestamo(){
        Intent intent = new Intent(PrincipalAdministrativaActivity.this, AsignarPrestamoActivity.class);
        startActivity(intent);
    }


    private void salir() {
        finish(); // Cierra la actividad actual
        System.exit(0); // Cierra la aplicación completamente
    }

}