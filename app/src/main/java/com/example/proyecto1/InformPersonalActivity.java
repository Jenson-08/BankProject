package com.example.proyecto1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InformPersonalActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.proyecto1.EXTRA_TEXT";
    String[] est_civil = {"Unión libre", "Soltero", "Casado"};
    Spinner spinner_estadoCivil;
    EditText cedula;
    EditText nombre;
    EditText salario;
    EditText telefono;
    EditText fechaNacimiento;
    EditText direccion;

    EditText contraseña;

    String nomUsuario_str;
    private Usuario usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform_personal);
        Intent intent = getIntent();

        nomUsuario_str = intent.getStringExtra(PrincipalClienteActivity.EXTRA_TEXT);

        spinner_estadoCivil = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, est_civil);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estadoCivil.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.elemento_spinner_personalizado); // Elige el layout que quieras usar para las opciones del spinner
        spinner_estadoCivil.setAdapter(adapter);

        cedula = findViewById(R.id.txt_CedulaCliente);
        cedula.setEnabled(false);
        nombre = findViewById(R.id.txt_NomCliente);
        salario = findViewById(R.id.txt_SalarioCliente);
        telefono = findViewById(R.id.txt_telefonoCliente);
        fechaNacimiento = findViewById(R.id.txt_FechaNacimiento);
        direccion = findViewById(R.id.txt_DireccionCliente);
        contraseña = findViewById(R.id.txt_passwordCliente);


        setCliente(nomUsuario_str);
        setText();


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verificar si el elemento seleccionado es la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            // Volver a la actividad anterior
            Intent intent = new Intent(InformPersonalActivity.this, PrincipalClienteActivity.class);
            String usuario_string= usu.getCed();
            intent.putExtra(EXTRA_TEXT,usuario_string );
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setText(){
        cedula.setText(usu.getCed());
        nombre.setText(usu.getNom());
        salario.setText(String.valueOf(usu.getSal()));
        telefono.setText(String.valueOf(usu.getTel()));
        fechaNacimiento.setText(usu.getFecha_nacimiento());
        direccion.setText(usu.getDireccion());
        contraseña.setText(usu.getContraseña());
        if(usu.getEstado_civil().equals("Unión libre"))
            spinner_estadoCivil.setSelection(0);
        if(usu.getEstado_civil().equals("Soltero"))
            spinner_estadoCivil.setSelection(1);
        if(usu.getEstado_civil().equals("Casado"))
            spinner_estadoCivil.setSelection(2);


    }

    private void setCliente(String nomUsuario_str) {
        try {
            Dao admin = new Dao(this, DatabaseName.NOMDB, null, 1);
            usu = admin.consultarPorCédula(nomUsuario_str);

        } catch (Exception e) {
            usu = new Usuario();
        }
    }

    public void actualizar(View view){


        String nom = nombre.getText().toString();
        String sal = salario.getText().toString();
        String tel = telefono.getText().toString();
        String fechNac = fechaNacimiento.getText().toString();
        String estciv = (String) spinner_estadoCivil.getSelectedItem();
        String direcc = direccion.getText().toString();
        String contra = contraseña.getText().toString();
        if(!nom.isEmpty() && !sal.isEmpty()&&!tel.isEmpty() &&!fechNac.isEmpty() &&!estciv.isEmpty() &&!direcc.isEmpty() && !contra.isEmpty() ) {

            try {
                Usuario elUsuario = new Usuario();
                elUsuario.setCed(nomUsuario_str);
                elUsuario.setNom(nom);
                elUsuario.setSal(Double.parseDouble(sal));
                elUsuario.setTel(Integer.parseInt(tel));
                elUsuario.setFecha_nacimiento(fechNac);
                elUsuario.setEstado_civil(estciv);
                elUsuario.setDireccion(direcc);
                elUsuario.setContraseña(contra);
                elUsuario.setTipo(1);
                Dao d1 = new Dao(this, DatabaseName.NOMDB, null, 1);
                d1.actualizarUsuario(elUsuario);
                Toast.makeText(this, "Usuario Actualizado ", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Error ", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(this, "Error: Todos los campos deben de estar llenos ", Toast.LENGTH_SHORT).show();

        }

    }


}