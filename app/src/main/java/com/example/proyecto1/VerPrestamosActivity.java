package com.example.proyecto1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VerPrestamosActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.proyecto1.EXTRA_TEXT";
    private LinearLayout lyo_prestamos;
    private List<Prestamo> pres;
    String nomUsuario_str;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_prestamos);
        lyo_prestamos = findViewById(R.id.lyo_listPrestamo);

        Intent intent = getIntent();

        nomUsuario_str = intent.getStringExtra(PrincipalClienteActivity.EXTRA_TEXT);
        setPrestamos(nomUsuario_str);
        fillLayout();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void fillLayout() {
        for (Prestamo prest : pres) {
            generarPrestamo(prest);


        }


    }

    private void generarPrestamo(Prestamo prest) {
        // Crear una fila de tabla para mostrar la información del préstamo
        TableRow tablerow = new TableRow(this);
        tablerow.setBackgroundResource(R.drawable.table_divider);
        // Crear un TextView para mostrar el ID del préstamo
        TextView idTxt = new TextView(this);
        idTxt.setText("ID: " + prest.getId());

        // Crear un TextView para mostrar el monto del crédito del préstamo
        TextView creditoTxt = new TextView(this);
        creditoTxt.setText("Crédito: " + prest.getCredito());

        // Crear un TextView para mostrar el periodo del préstamo
        TextView periodoTxt = new TextView(this);
        periodoTxt.setText("Período: " + prest.getPeriodo());

        // Crear un TextView para mostrar el tipo de préstamo
        TextView tipoTxt = new TextView(this);
        tipoTxt.setText("Tipo: " + prest.getTipo());

        // Crear un TextView para mostrar los meses por pagar del préstamo
        TextView mesesTxt = new TextView(this);
        mesesTxt.setText("Meses: " + prest.getMesesPorPagar());

        // Establecer la propiedad gravity de los TextView
        idTxt.setGravity(Gravity.CENTER);
        creditoTxt.setGravity(Gravity.CENTER);
        periodoTxt.setGravity(Gravity.CENTER);
        tipoTxt.setGravity(Gravity.CENTER);
        mesesTxt.setGravity(Gravity.CENTER);





        // Crear un botón para permitir al usuario pagar el préstamo
        Button btn = new Button(this);
        btn.setText("Pagar");


        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                amortizar(prest);
            }
        });

        // Establecer el ancho fijo y peso para cada columna
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                0, TableRow.LayoutParams.WRAP_CONTENT, 1f
        );
        idTxt.setLayoutParams(layoutParams);

        creditoTxt.setLayoutParams(layoutParams);
        periodoTxt.setLayoutParams(layoutParams);
        tipoTxt.setLayoutParams(layoutParams);
        mesesTxt.setLayoutParams(layoutParams);
        btn.setLayoutParams(layoutParams);

        // Agregar los TextView y el botón a la fila de tabla
        tablerow.addView(idTxt);

        tablerow.addView(creditoTxt);
        tablerow.addView(periodoTxt);
        tablerow.addView(tipoTxt);
        tablerow.addView(mesesTxt);
        tablerow.addView(btn);

        tablerow.setPadding(0, 0, 0, 30);



        // Agregar la fila de tabla al LinearLayout



        lyo_prestamos.addView(tablerow);

        lyo_prestamos.setPadding(0, 35, 0, 0);
        idTxt.setTextColor(0xFFF1C9A0);
        creditoTxt.setTextColor(0xFFF1C9A0);
        periodoTxt.setTextColor(0xFFF1C9A0);
        tipoTxt.setTextColor(0xFFF1C9A0);
        mesesTxt.setTextColor(0xFFF1C9A0);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verificar si el elemento seleccionado es la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            // Volver a la actividad anterior
            Intent intent = new Intent(VerPrestamosActivity.this, PrincipalClienteActivity.class);

            intent.putExtra(EXTRA_TEXT,nomUsuario_str );
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void setPrestamos(String nomUsuario_str) {
        try {
            Dao admin = new Dao(this, DatabaseName.NOMDB, null, 1);
            pres = admin.consultarPrestamosPorCed(nomUsuario_str);

        } catch (Exception e) {
            pres = new ArrayList<>();
        }


    }

    private void amortizar(Prestamo p) {
        try {
            if (p.getMesesPorPagar() == 0){
                Toast.makeText(this, "Este prestamo ya está cancelado", Toast.LENGTH_SHORT).show();
                return;
            }


            double deduccion = p.getMesesPorPagar() * p.cuotaPorMeses();
            Dao admin = new Dao(this, DatabaseName.NOMDB, null, 1);
            admin.actualizaMesesPrestamo(String.valueOf(p.getId()), String.valueOf(p.getMesesPorPagar()-1));
            Toast.makeText(this, "Monto del credito restante: " + (deduccion - p.cuotaPorMeses()) + "\n Meses restantes: " + (p.getMesesPorPagar()-1), Toast.LENGTH_SHORT).show();
            actualizar();
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        }
    }
    private void actualizar(){
        lyo_prestamos.removeAllViews();
        setPrestamos(nomUsuario_str);
        fillLayout();

    }

}