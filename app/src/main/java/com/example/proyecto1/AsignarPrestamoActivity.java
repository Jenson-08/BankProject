package com.example.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AsignarPrestamoActivity extends AppCompatActivity {

    EditText cedula;
    Button buscarCedula;
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


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_prestamo);
        cedula = findViewById(R.id.txt_BuscadorPorCedula);
        tipocreditoreal = findViewById(R.id.tv_periodocred);


        anadir= findViewById(R.id.btn_anadir);

        digiteCredito = findViewById(R.id.txt_digitecredito);
        digitePeridodo = findViewById(R.id.tv_annios);

        buscarCedula = findViewById(R.id.btn_BuscarCedula);
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


        setOnFocusChangeListener(cedula);
        anadir.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                anadirPrestamo();
            }
        });

        invisible();
        buscarCedula.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                buscarPorCedula();
            }
        });

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

            if (usuariodefinitivo.getSal() * 0.45 <= Double.parseDouble(cre)){

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
                    MostrarCuotasMensuales(p);


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

    private void invisible(){

        tipocreditoreal.setVisibility(View.INVISIBLE);

        anadir.setVisibility(View.INVISIBLE);


        digiteCredito.setVisibility(View.INVISIBLE);
        digitePeridodo.setVisibility(View.INVISIBLE);


        numCredito.setVisibility(View.INVISIBLE);

        spinner_annioscredito.setVisibility(View.INVISIBLE);


        spinner_tipocredito.setVisibility(View.INVISIBLE);

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


    public  void buscarPorCedula(){
        tv_indicmensual.setVisibility(View.INVISIBLE);
        cuotasPorMes.setVisibility(View.INVISIBLE);
        String cedBuscar = cedula.getText().toString();
        try {
            String bd = DatabaseName.NOMDB;
            Dao d1 = new Dao(this, bd, null, 1);
            Usuario usu = d1.consultarPorCédula(cedBuscar);
            String DatosPersona = "Nombre: " + usu.getNom() + "\nCédula: " + usu.getCed() + "\nSalario: " + usu.getSal();
            datospersona.setText(DatosPersona);

            usuariodefinitivo = usu;

            validadorMenor45(numCredito);
            visible();
        }catch (Exception e){
            Toast.makeText(this, "No se encontraron coincidencias", Toast.LENGTH_SHORT).show();
        }

    }


    public void MostrarCuotasMensuales(Prestamo p){
            String valorFormateado = String.format("%.4f", p.cuotaPorMeses());
            String Cuotas = "Cuota mensual: " + valorFormateado + " por " + p.getMesesPorPagar() + " meses";

            cuotasPorMes.setText(Cuotas);

           // visible();

    }

}