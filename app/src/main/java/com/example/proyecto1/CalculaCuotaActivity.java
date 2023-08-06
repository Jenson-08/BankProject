package com.example.proyecto1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CalculaCuotaActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.proyecto1.EXTRA_TEXT";
    TextView datospersona;
    EditText numCredito;
    TextView digiteCredito;
    TextView digitePeridodo;
    String[] annios = {"3", "5", "10"};
    String[] tipocredito = {"Hipotecario", "Educacion", "Personal","Viajes"};
    Spinner spinner_annioscredito;
    Spinner spinner_tipocredito;
    TextView tipocreditoreal;

    Button anadir;

    TextView tv_indicmensual;
    TextView cuotasPorMes;


    Usuario usuariodefinitivo;
    String nomUsuario_str;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula_cuota);

        tipocreditoreal = findViewById(R.id.tv_periodocred);


        anadir= findViewById(R.id.btn_anadir);

        digiteCredito = findViewById(R.id.txt_digitecredito);
        digitePeridodo = findViewById(R.id.tv_annios);


        datospersona = findViewById(R.id.txt_VisorDatosPersona);
        numCredito = findViewById(R.id.txt_MontoCredito);

        tv_indicmensual = findViewById(R.id.tv_indicamensual);
        cuotasPorMes = findViewById(R.id.tv_cuotamensual);

        spinner_annioscredito =findViewById(R.id.spinner_annioscredito);


        spinner_tipocredito = findViewById(R.id.spinner_tipocredito);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,annios );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_annioscredito.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.elemento_spinner_personalizado); // Elige el layout que quieras usar para las opciones del spinner
        spinner_annioscredito.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tipocredito );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipocredito.setAdapter(adapter2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(R.layout.elemento_spinner_personalizado); // Elige el layout que quieras usar para las opciones del spinner
        spinner_tipocredito.setAdapter(adapter2);

        anadir.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MostrarCuotasMensuales();
            }
        });


        invisible();

        Intent intent = getIntent();

        nomUsuario_str = intent.getStringExtra(PrincipalClienteActivity.EXTRA_TEXT);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setOnFocusChangeListener(numCredito);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verificar si el elemento seleccionado es la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            // Volver a la actividad anterior
            Intent intent = new Intent(CalculaCuotaActivity.this, PrincipalClienteActivity.class);

            intent.putExtra(EXTRA_TEXT,nomUsuario_str );
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void validadorMenor45(TextView textView) {
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String text = textView.getText().toString();
                    if (text.isEmpty()) {
                        textView.setError("Valor númerico obligatorio");
                    } else {
                        double number = Double.parseDouble(text);
                        if ( ((usuariodefinitivo.getSal() * 0.45) <= number)) {
                            textView.setError("El valor maximo permitido es de "+ (usuariodefinitivo.getSal() * 0.45));
                        } else {
                            textView.setError(null);
                        }
                    }
                }
            }
        });
    }
    public void anadirPrestamo(){
        try{


            String cre = numCredito.getText().toString();
            String periodo = (String)spinner_annioscredito.getSelectedItem();
            String tipo = (String)spinner_tipocredito.getSelectedItem();

            Prestamo p = new Prestamo();
            p.setCredito(Double.parseDouble(cre));
            p.setPeriodo(Integer.parseInt(periodo));


            if(tipo == "Hipotecario")
                tipo="0.075";
            else if(tipo == "Educacion")
                tipo="0.08";
            else if(tipo == "Personal")
                tipo="0.10";
            else if(tipo == "Viajes")
                tipo="0.12";

            p.setTipo(Double.parseDouble(tipo));

            if (usuariodefinitivo.getSal() * 0.45 < Double.parseDouble(cre)){

                Toast.makeText(this, ("El valor maximo permitido es de "+ (usuariodefinitivo.getSal() * 0.45)), Toast.LENGTH_SHORT).show();
                return;

            }


            if(!cre.isEmpty()  && !periodo.isEmpty() &&!tipo.isEmpty() ){
                try {
                    int mesesp_pagar = Integer.parseInt(periodo) *12;
                    String mesespor_pagar = String.valueOf(mesesp_pagar);
                    p.setMesesPorPagar(mesesp_pagar);
                    String bd = DatabaseName.NOMDB;
                    Dao admin = new Dao(this, bd, null, 1);
                    admin.ingresarPrestamo(usuariodefinitivo.getCed(),cre,periodo,tipo,mesespor_pagar);


                    Toast.makeText(this, "Insercion Correcta!", Toast.LENGTH_SHORT).show();
                    tv_indicmensual.setVisibility(View.VISIBLE);
                    cuotasPorMes.setVisibility(View.VISIBLE);
                   // MostrarCuotasMensuales(p);


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



        }catch(Exception e){}


    }

    public void invisible(){


        tv_indicmensual.setVisibility(View.INVISIBLE);
        cuotasPorMes.setVisibility(View.INVISIBLE);

    }
    private void visible(){

        numCredito.setVisibility(View.VISIBLE);
        spinner_annioscredito.setVisibility(View.VISIBLE);
        tipocreditoreal.setVisibility(View.VISIBLE);
        digiteCredito.setVisibility(View.VISIBLE);
        digitePeridodo.setVisibility(View.VISIBLE);
        spinner_tipocredito.setVisibility(View.VISIBLE);
        anadir.setVisibility(View.VISIBLE);


    }


    private void MostrarCuotasMensuales(){

        String cred = numCredito.getText().toString();

        if (cred.isEmpty()){
            Toast.makeText(this, "Debe introducir un monto de crédito", Toast.LENGTH_SHORT).show();
            return;
        }

        //String valorFormateado = String.format("%.4f", p.cuotaPorMeses());
        String periodostr = (String)spinner_annioscredito.getSelectedItem();
        int periodo = Integer.parseInt(periodostr) *12;

        String tipostr = (String)spinner_tipocredito.getSelectedItem();

        if(tipostr == "Hipotecario")
            tipostr="0.075";
        else if(tipostr == "Educacion")
            tipostr="0.08";
        else if(tipostr == "Personal")
            tipostr="0.10";
        else if(tipostr == "Viajes")
            tipostr="0.12";

        double tipo = Double.parseDouble(tipostr);

        double credito = Double.parseDouble(cred);

        double tasaInteresMensual = tipo / 12;
        double cuotaMensual = credito * tasaInteresMensual / (1 - Math.pow(1 + tasaInteresMensual, -(periodo)));
        String valorFormateado = String.format("%.4f", cuotaMensual);
        String Cuotas = "Cuota mensual: " + valorFormateado + " por " + periodo + " meses";

        cuotasPorMes.setText(Cuotas);
        tv_indicmensual.setVisibility(View.VISIBLE);
        cuotasPorMes.setVisibility(View.VISIBLE);
        // visible();

    }

}