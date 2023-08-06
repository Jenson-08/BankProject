package com.example.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PrincipalClienteActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT ="com.example.proyecto1.EXTRA_TEXT" ;
    Button verPrestamos;
    Button gestAhorros;
    Button calcCuota;
    Button infoPersonal;

    TextView veridcliente;

    ImageButton salir2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_cliente);

        verPrestamos=findViewById(R.id.btn_verPrestamos);
        gestAhorros = findViewById(R.id.btn_gestionarAhorros);
        calcCuota = findViewById(R.id.btn_calculaCuota);
        infoPersonal = findViewById(R.id.btn_infPersonal);
        veridcliente =findViewById(R.id.tv_idcliente);
        salir2 = findViewById(R.id.btn_salir2);
        String nomUsuario_str;
        Intent intent = getIntent();

        nomUsuario_str = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        veridcliente.setText(nomUsuario_str);
        salir2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                salir();
            }
        });

    }


    public void pantallaVerPrestamos(View vista){
        Intent intent = new Intent(PrincipalClienteActivity.this, VerPrestamosActivity.class);
        String usuario_string= veridcliente.getText().toString();
        intent.putExtra(EXTRA_TEXT,usuario_string );
        startActivity(intent);
    }

    public void pantallaCalculaCuota(View vista){
        Intent intent = new Intent(PrincipalClienteActivity.this, CalculaCuotaActivity.class);
        String usuario_string= veridcliente.getText().toString();
        intent.putExtra(EXTRA_TEXT,usuario_string );
        startActivity(intent);
    }
    public void pantallaGestionarAhorros(View vista){
        Intent intent = new Intent(PrincipalClienteActivity.this, GestionarAhorrosActivity.class);
        String usuario_string= veridcliente.getText().toString();
        intent.putExtra(EXTRA_TEXT,usuario_string );
        startActivity(intent);
    }

    public void pantallaInformCliente(View vista){
        Intent intent = new Intent(PrincipalClienteActivity.this, InformPersonalActivity.class);
        String usuario_string= veridcliente.getText().toString();
        intent.putExtra(EXTRA_TEXT,usuario_string );
        startActivity(intent);
    }

    private void salir() {
        //finish(); // Cierra la actividad actual
        //ystem.exit(0); // Cierra la aplicación completamente

        Intent intent = new Intent(PrincipalClienteActivity.this, MainActivity.class);
        startActivity(intent);
    }



}