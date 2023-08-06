package com.example.proyecto1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GestionarAhorrosActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.proyecto1.EXTRA_TEXT";
    TextView nombreNavidenio;
    TextView cuotaNaviden;
    EditText digiteCuotaNaviden;
    Button activarCuotaNaviden;


    TextView nomEscolar;
    TextView cuotaEscolar;
    EditText digiteCuotaEscolar;
    Button activarCuotaEscolar;

    TextView nomMarchamo;
    TextView cuotaMarchamo;
    EditText digiteCuotaMarchamo;
    Button activarCuotaMarchamo;

    TextView nomExtraordinario;
    TextView cuotaExtraordinario;
    EditText digiteCuotaExtraordinario;
    Button activarCuotaExtraordinario;

    String nomUsuario_str;
    TextView nomCliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_ahorros);

        nombreNavidenio = findViewById(R.id.txt_nomNavidenio);
        cuotaNaviden = findViewById(R.id.txt_cantCuotaNavidenio);
        digiteCuotaNaviden = findViewById(R.id.txt_digiteCuotaNavidenio);
        activarCuotaNaviden = findViewById(R.id.btn_activarNavidenio);

        nomEscolar = findViewById(R.id.txt_nomEscolar);
        cuotaEscolar = findViewById(R.id.txt_cantCuotaEscolar);
        digiteCuotaEscolar = findViewById(R.id.txt_digiteCuotaEscolar);
        activarCuotaEscolar = findViewById(R.id.btn_activarEscolar);

        nomMarchamo = findViewById(R.id.txt_nomMarchamo);
        cuotaMarchamo = findViewById(R.id.txt_cantCuotaMarchamo);
        digiteCuotaMarchamo = findViewById(R.id.txt_digiteCuotaMarchamo);
        activarCuotaMarchamo = findViewById(R.id.btn_activarMarchamo);

        nomExtraordinario = findViewById(R.id.txt_nomExtraord);
        cuotaExtraordinario = findViewById(R.id.txt_cantCuotaExtraord);
        digiteCuotaExtraordinario = findViewById(R.id.txt_digiteCuotaExtraord);
        activarCuotaExtraordinario = findViewById(R.id.btn_activarExtraord);

        Intent intent = getIntent();

        nomUsuario_str = intent.getStringExtra(PrincipalClienteActivity.EXTRA_TEXT);
        nomCliente = findViewById(R.id.nomClienteAhorro);
        nomCliente.setText("Ahorros de: " + nomUsuario_str);

       /* setOnFocusChangeListener(digiteCuotaNaviden);
        setOnFocusChangeListener(digiteCuotaEscolar);
        setOnFocusChangeListener(digiteCuotaExtraordinario);
        setOnFocusChangeListener(digiteCuotaMarchamo);*/

        asignarCuotas();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public List<Ahorro>  ahorrosDelCliente(String nomUsuario_str){

        try {
            Dao admin = new Dao(this, DatabaseName.NOMDB, null, 1);
            return admin.consultarAhorros(nomUsuario_str);

        } catch (Exception e) {
            return new ArrayList<>();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verificar si el elemento seleccionado es la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            // Volver a la actividad anterior
            Intent intent = new Intent(GestionarAhorrosActivity.this, PrincipalClienteActivity.class);

            intent.putExtra(EXTRA_TEXT,nomUsuario_str );
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void asignarCuotas(){
        List<Ahorro> listaAhorr = ahorrosDelCliente(nomUsuario_str);
        for (Ahorro ao : listaAhorr){
            if(ao.getTipo_ahorro().equals("Navideno")){
                cuotaNaviden.setText( String.valueOf(ao.getCuota()));
                activarCuotaNaviden.setEnabled(false);
                digiteCuotaNaviden.setEnabled(false);
            }
            if(ao.getTipo_ahorro().equals("Escolar")){
                cuotaEscolar.setText( String.valueOf(ao.getCuota()));
                activarCuotaEscolar.setEnabled(false);
                digiteCuotaEscolar.setEnabled(false);
            }
            if(ao.getTipo_ahorro().equals("Marchamo")){
                cuotaMarchamo.setText( String.valueOf(ao.getCuota()));
                activarCuotaMarchamo.setEnabled(false);
                digiteCuotaMarchamo.setEnabled(false);
            }
            if(ao.getTipo_ahorro().equals("Extraordinario")){
                cuotaExtraordinario.setText( String.valueOf(ao.getCuota()));
                activarCuotaExtraordinario.setEnabled(false);
                digiteCuotaExtraordinario.setEnabled(false);
            }
        }
    }

    public void ingresaNavidennio(View view){

        String cuot = digiteCuotaNaviden.getText().toString();

        if (cuot.isEmpty()){
            Toast.makeText(this, "Debe introducir una cuota", Toast.LENGTH_SHORT).show();
            return;
        }


        if(!validar(Double.parseDouble(cuot))){
                Toast.makeText(this, "Debe introducir monto mayor a 5000", Toast.LENGTH_SHORT).show();
                return;
        }

        Ahorro a1= new Ahorro();
        String nom = "Navideno";
        a1.setTipo_ahorro(nom);
        a1.setCuota(Double.parseDouble(cuot));
        a1.setCedCliente(nomUsuario_str);
        Usuario u = new Usuario();
        u = retornaUsuario(nomUsuario_str);
        double salarioclient = u.getSal();
        double cuotaaux = Double.parseDouble(digiteCuotaNaviden.getText().toString());
        if (ingresaCouta(a1)){
            if (u.getSal() > cuotaaux) {
                if (actualizaSalarioClienteCuota(nomUsuario_str, String.valueOf(salarioclient - cuotaaux))) {
                  cuotaNaviden.setText( String.valueOf(a1.getCuota()));
                  activarCuotaNaviden.setEnabled(false);
                  digiteCuotaNaviden.setText("");
                  digiteCuotaNaviden.setEnabled(false);


                }else {
                    Toast.makeText(this, "Error de act de salario", Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(this, "Cuota mayor a salario", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void ingresaEscolar(View view){

        String cuot = digiteCuotaEscolar.getText().toString();

        if (cuot.isEmpty()){
            Toast.makeText(this, "Debe introducir una cuota", Toast.LENGTH_SHORT).show();
            return;
        }


        if(!validar(Double.parseDouble(cuot))){
            Toast.makeText(this, "Debe introducir monto mayor a 5000", Toast.LENGTH_SHORT).show();
            return;
        }


        Ahorro a1= new Ahorro();
        String nom = "Escolar";
        a1.setTipo_ahorro(nom);
        a1.setCuota(Double.parseDouble(cuot));
        a1.setCedCliente(nomUsuario_str);


        Usuario u = new Usuario();
        u = retornaUsuario(nomUsuario_str);
        double salarioclient = u.getSal();
        double cuotaaux = Double.parseDouble(digiteCuotaEscolar.getText().toString());
        if (ingresaCouta(a1)){
            if (u.getSal() > cuotaaux) {
                if (actualizaSalarioClienteCuota(nomUsuario_str, String.valueOf(salarioclient - cuotaaux))) {
                    cuotaEscolar.setText( String.valueOf(a1.getCuota()));
                    activarCuotaEscolar.setEnabled(false);
                    digiteCuotaEscolar.setText("");
                    digiteCuotaEscolar.setEnabled(false);

                }else {
                    Toast.makeText(this, "Error de act de salario", Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(this, "Cuota mayor a salario", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void ingresaMarchamo(View view){

        String cuot = digiteCuotaMarchamo.getText().toString();

        if (cuot.isEmpty()){
            Toast.makeText(this, "Debe introducir una cuota", Toast.LENGTH_SHORT).show();
            return;
        }



        if(!validar(Double.parseDouble(cuot))){
            Toast.makeText(this, "Debe introducir monto mayor a 5000", Toast.LENGTH_SHORT).show();
            return;
        }

        Ahorro a1= new Ahorro();

        String nom = "Marchamo";
        a1.setTipo_ahorro(nom);
        a1.setCuota(Double.parseDouble(cuot));
        a1.setCedCliente(nomUsuario_str);
        Usuario u = new Usuario();
        u = retornaUsuario(nomUsuario_str);
        double salarioclient = u.getSal();
        double cuotaaux = Double.parseDouble(digiteCuotaMarchamo.getText().toString());
        if (ingresaCouta(a1)){
            if (u.getSal() > cuotaaux) {
                if (actualizaSalarioClienteCuota(nomUsuario_str, String.valueOf(salarioclient - cuotaaux))) {
                    cuotaMarchamo.setText(String.valueOf(a1.getCuota()));
                    activarCuotaMarchamo.setEnabled(false);
                    digiteCuotaMarchamo.setText("");
                    digiteCuotaMarchamo.setEnabled(false);
                }else {
                    Toast.makeText(this, "Error de act de salario", Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(this, "Cuota mayor a salario", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void ingresaExtraordinario(View view){

        String cuot = digiteCuotaExtraordinario.getText().toString();

        if (cuot.isEmpty()){
            Toast.makeText(this, "Debe introducir una cuota", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if(!validar(Double.parseDouble(cuot))){
                Toast.makeText(this, "Debe introducir monto mayor a 5000", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e){
            Toast.makeText(this, "Debe introducir una cuota", Toast.LENGTH_SHORT).show();

        }




        Ahorro a1= new Ahorro();
        String nom = "Extraordinario";
        a1.setTipo_ahorro(nom);
        a1.setCuota(Double.parseDouble(cuot));
        a1.setCedCliente(nomUsuario_str);
        Usuario u = new Usuario();
        u = retornaUsuario(nomUsuario_str);
        double salarioclient = u.getSal();
        double cuotaaux = Double.parseDouble(digiteCuotaExtraordinario.getText().toString());
        if (ingresaCouta(a1)){
            if (u.getSal() > cuotaaux) {
                if (actualizaSalarioClienteCuota(nomUsuario_str, String.valueOf(salarioclient - cuotaaux))) {
                    cuotaExtraordinario.setText( String.valueOf(a1.getCuota()));
                    activarCuotaExtraordinario.setEnabled(false);
                    digiteCuotaExtraordinario.setText("");
                    digiteCuotaExtraordinario.setEnabled(false);
                }else {
                    Toast.makeText(this, "Error de act de salario", Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(this, "Cuota mayor a salario", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean actualizaSalarioClienteCuota(String id, String nuevosal){
        try{
            Dao d1 = new Dao(this, DatabaseName.NOMDB, null, 1);
            d1.actualizaSalario(id,nuevosal);
            return true;
        }catch(Exception e){
            return false;

        }
    }


    private boolean ingresaCouta(Ahorro a){
        try{
            Dao d1 = new Dao(this, DatabaseName.NOMDB, null, 1);
            d1.ingresarCuota(a);
            return true;
        }catch(Exception e){
            return false;

        }
    }
     private boolean validar(double monto){

        if(monto < 5000)
            return false;
        return true;
    }

    private Usuario retornaUsuario(String ced){
        try{
            Dao d1 = new Dao(this, DatabaseName.NOMDB, null, 1);
            Usuario u1 =   d1.consultarPorCédula(ced);

            return u1;
        }catch(Exception e){
            return null;

        }

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

}