package com.example.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AgregarClienteActivity extends AppCompatActivity {
    String[] est_civil = {"Unión libre", "Soltero", "Casado"};
    Spinner spinner_estadoCivil;

    EditText cedula;
    EditText nombre;
    EditText salario;
    EditText telefono;
    EditText fechaNacimiento;
    EditText direccion;

    EditText contraseña;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);
        Intent intent = getIntent();
        cedula = findViewById(R.id.txt_CedulaCliente);
        nombre = findViewById(R.id.txt_NomCliente);
        salario = findViewById(R.id.txt_SalarioCliente);
        telefono = findViewById(R.id.txt_telefonoCliente);
        fechaNacimiento = findViewById(R.id.txt_FechaNacimiento);
        direccion = findViewById(R.id.txt_DireccionCliente);
        contraseña = findViewById(R.id.txt_passwordCliente);

        spinner_estadoCivil = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, est_civil);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estadoCivil.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.elemento_spinner_personalizado); // Elige el layout que quieras usar para las opciones del spinner
        spinner_estadoCivil.setAdapter(adapter);
        setOnFocusChangeListener(cedula);
        setOnFocusChangeListener(nombre);
        setOnFocusChangeListener(salario);
        setOnFocusChangeListener(telefono);
        setOnFocusChangeListener(fechaNacimiento);
        setOnFocusChangeListener(direccion);
        setOnFocusChangeListener(contraseña);
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
    public void insertar(View vista){

        String ced = cedula.getText().toString();
        String nom = nombre.getText().toString();
        String sal = salario.getText().toString();
        String tel = telefono.getText().toString();
        String fechNac = fechaNacimiento.getText().toString();
        String estciv = (String) spinner_estadoCivil.getSelectedItem();
        String direcc = direccion.getText().toString();
        String contra = contraseña.getText().toString();

        if(!ced.isEmpty() && !nom.isEmpty() && !sal.isEmpty()&&!tel.isEmpty() &&!fechNac.isEmpty() &&!estciv.isEmpty() &&!direcc.isEmpty() && !contra.isEmpty() ){
            try {
                String bd = DatabaseName.NOMDB;
                Dao admin = new Dao(this, bd, null, 1);
                admin.ingresar(ced,nom,sal,tel,fechNac,estciv,direcc,contra);
                cedula.setText("");
                nombre.setText("");
                salario.setText("");
                telefono.setText("");
                fechaNacimiento.setText("");
                direccion.setText("");
                contraseña.setText("");
                Toast.makeText(this, "Insercion Correcta!", Toast.LENGTH_SHORT).show();
            }
            catch (SQLiteConstraintException e) {
                Toast.makeText(this, "Error: La cedula que usted esta poniendo ya se encuentra registrada", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){
                Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show();

            }
            }
        else{
            Toast.makeText(this, "Debe llenar todos los datos", Toast.LENGTH_SHORT).show();
        }
    }





}