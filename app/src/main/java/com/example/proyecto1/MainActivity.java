package com.example.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_Login;
    Button btn_Salir;
EditText usuario;
EditText clave;
public static final String EXTRA_TEXT = "com.example.proyecto1.EXTRA_TEXT";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        btn_Login = findViewById(R.id.btn_Ingresar);
        btn_Salir = findViewById(R.id.btn_Cerrar);
        usuario = findViewById(R.id.txt_NomUsuario);
        clave = findViewById(R.id.txt_Contraseña);
        setOnFocusChangeListener(usuario);
        setOnFocusChangeListener(clave);


        btn_Login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openActivityInicioSesion();
            }
        });
    }
    public void salir(View v) {
            finish(); // Cierra la actividad actual
            System.exit(0); // Cierra la aplicación completamente
    }
    private void setOnFocusChangeListener(TextView textView) {
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String text = textView.getText().toString();
                    if (text.isEmpty()) {
                        textView.setError("Por favor llenar este campo");
                    }
                }
            }
        });
    }

    public void openActivityInicioSesion() {
//        pantallaAdmin();
      try{
            String ced= usuario.getText().toString();
            String contra = clave.getText().toString();
            Dao admin = new Dao(this, DatabaseName.NOMDB, null, 1);
            Usuario usu = admin.consultar(ced,contra);
            if(usu.getTipo() == 1){
               // Toast.makeText(this, "Es un cliente, pantalla por hacer", Toast.LENGTH_SHORT).show();
                pantallaCliente();
            }


            if(usu.getTipo() == 2){
                pantallaAdmin();

           }

        }catch(Exception e){
            Toast.makeText(this, "Datos no coinciden", Toast.LENGTH_SHORT).show();

        }




   }


private void pantallaAdmin(){
    Intent intent = new Intent(MainActivity.this, PrincipalAdministrativaActivity.class);
    String usuario_string= usuario.getText().toString();
    intent.putExtra(EXTRA_TEXT,usuario_string );
    startActivity(intent);
}

    private void pantallaCliente(){
        Intent intent = new Intent(MainActivity.this, PrincipalClienteActivity.class);
        String usuario_string= usuario.getText().toString();
        intent.putExtra(EXTRA_TEXT,usuario_string );
        startActivity(intent);
    }




}