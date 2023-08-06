package com.example.proyecto1;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Dao extends SQLiteOpenHelper {

    public Dao(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tipo 1: CLiente
        // Tipo 2: Admin
        db.execSQL("create table usuarios(cedula text primary key, nombre text, salario real," +
                "telefono int,fecha_nacimiento text,estado_civil text, direccion text,tipo int, contrasena text)");

        db.execSQL("CREATE TABLE prestamos (id INTEGER PRIMARY KEY AUTOINCREMENT,cedula_cliente text ,cant_credito real,periodo_credito int,tipo_credito real, mesespor_pagar int)");

        db.execSQL("CREATE TABLE ahorros (id INTEGER PRIMARY KEY AUTOINCREMENT,cedula_cliente text ,tipo_ahorro txt,cuota real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void ingresar(String ced, String nom, String sal, String tel, String fechNac, String estciv, String direcc, String contra) throws SQLiteConstraintException {
        SQLiteDatabase baseDatos = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("cedula", ced);
        registro.put("nombre", nom);
        registro.put("salario", sal);
        registro.put("telefono", tel);
        registro.put("fecha_nacimiento", fechNac);
        registro.put("estado_civil", estciv);
        registro.put("direccion", direcc);
        registro.put("tipo", 1);
        registro.put("contrasena", contra);


        baseDatos.insertOrThrow("usuarios", null, registro);

        baseDatos.close();
    }

    public Usuario consultar(String ced, String contra) throws Exception {

        SQLiteDatabase baseDatos = this.getWritableDatabase();
        Usuario usu = null;

        try {
            String consulta = "SELECT cedula, nombre, salario, telefono, fecha_nacimiento, estado_civil, direccion, tipo, contrasena FROM usuarios WHERE cedula = ? AND contrasena = ?";
            Cursor fila = baseDatos.rawQuery(consulta, new String[]{ced, contra});

            if (fila.moveToFirst()) {
                usu = new Usuario();
                usu.setCed(fila.getString(0));
                usu.setNom(fila.getString(1));
                usu.setSal(fila.getDouble(2));
                usu.setTel(fila.getInt(3));
                usu.setFecha_nacimiento(fila.getString(4));
                usu.setEstado_civil(fila.getString(5));
                usu.setDireccion(fila.getString(6));
                usu.setTipo(fila.getInt(7));
                usu.setContraseña(fila.getString(8));
            } else {
                throw new Exception();
            }
        } finally {
            baseDatos.close();
        }

        return usu;
    }

    public void ingresarPrestamo(String ced_cliente, String cred, String period, String tipo, String mesesppagar) throws SQLiteConstraintException {
        SQLiteDatabase baseDatos = this.getWritableDatabase();
        ContentValues prestamo = new ContentValues();
        prestamo.put("cedula_cliente", ced_cliente);
        prestamo.put("cant_credito", cred);
        prestamo.put("periodo_credito", period);
        prestamo.put("tipo_credito", tipo);
        prestamo.put("mesespor_pagar",mesesppagar);


        baseDatos.insertOrThrow("prestamos", null, prestamo);

        baseDatos.close();
    }

    public Usuario consultarPorCédula(String ced) throws Exception {

        SQLiteDatabase baseDatos = this.getWritableDatabase();
        Usuario usu = null;

        try {
            String consulta = "SELECT cedula, nombre, salario, telefono, fecha_nacimiento, estado_civil, direccion, tipo, contrasena FROM usuarios WHERE cedula = ? AND tipo = 1";
            Cursor fila = baseDatos.rawQuery(consulta, new String[]{ced});

            if (fila.moveToFirst()) {
                usu = new Usuario();
                usu.setCed(fila.getString(0));
                usu.setNom(fila.getString(1));
                usu.setSal(fila.getDouble(2));
                usu.setTel(fila.getInt(3));
                usu.setFecha_nacimiento(fila.getString(4));
                usu.setEstado_civil(fila.getString(5));
                usu.setDireccion(fila.getString(6));
                usu.setTipo(fila.getInt(7));
                usu.setContraseña(fila.getString(8));
            } else {
                throw new Exception();
            }
        } finally {
            baseDatos.close();
        }

        return usu;
    }

    public List<Prestamo> consultarPrestamosPorCed(String ced) throws Exception {

        SQLiteDatabase baseDatos = this.getWritableDatabase();
        Prestamo p = null;
        List<Prestamo> listaprestamos = new ArrayList<>();

        try {
            String consulta = "SELECT id, cedula_cliente, cant_credito, periodo_credito, tipo_credito,mesespor_pagar FROM prestamos WHERE cedula_cliente = ?";
            Cursor fila = baseDatos.rawQuery(consulta, new String[]{ced});

            while (fila.moveToNext()) {
                p = new Prestamo();
                p.setId(fila.getInt(0));
                p.setPersona(fila.getString(1));
                p.setCredito(fila.getDouble(2));
                p.setPeriodo(fila.getInt(3));
                p.setTipo(fila.getDouble(4));
                p.setMesesPorPagar(fila.getInt(5));
                listaprestamos.add(p);
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar préstamos: " + e.getMessage());
        } finally {
            if (baseDatos != null && baseDatos.isOpen()) {
                baseDatos.close();
            }
        }
        return listaprestamos;
    }

    public void actualizaMesesPrestamo(String id, String mppagar) throws Exception {

        SQLiteDatabase baseDatos = this.getWritableDatabase();
        Prestamo p = null;

        try {
            String consulta = "UPDATE prestamos set mesespor_pagar = ? where id = ?";
            baseDatos.execSQL(consulta, new String[]{mppagar, id});

        } finally {
            baseDatos.close();
        }

    }


    public void ingresarCuota(Ahorro a) throws SQLiteConstraintException {
        SQLiteDatabase baseDatos = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("cedula_cliente", a.getCedCliente());
        registro.put("tipo_ahorro", a.getTipo_ahorro());
        registro.put("cuota", a.getCuota());

        baseDatos.insertOrThrow("ahorros", null, registro);
        baseDatos.close();
    }

    public List<Ahorro> consultarAhorros(String ced) throws Exception {

        SQLiteDatabase baseDatos = this.getWritableDatabase();
        Usuario usu = null;
        List<Ahorro> ahorros = new ArrayList<>();
        try {
            String consulta = "SELECT cedula_cliente, tipo_ahorro, cuota FROM ahorros WHERE cedula_cliente = ?";
            Cursor fila = baseDatos.rawQuery(consulta, new String[]{ced});

            while (fila.moveToNext()) {
                Ahorro p = new Ahorro();

                p.setCedCliente(fila.getString(0));
                p.setTipo_ahorro(fila.getString(1));
                p.setCuota(fila.getDouble(2));

                ahorros.add(p);
            }
            return ahorros;
        }  catch (SQLException e) {
            throw new Exception("Error al consultar préstamos: " + e.getMessage());

        } finally {
            if (baseDatos != null && baseDatos.isOpen()) {
                baseDatos.close();
            }
        }


    }

    public void actualizaSalario(String id, String sal) throws Exception {

        SQLiteDatabase baseDatos = this.getWritableDatabase();
        Prestamo p = null;

        try {
            String consulta = "UPDATE usuarios set salario = ? where cedula = ?";
            baseDatos.execSQL(consulta, new String[]{sal, id});

        } finally {
            baseDatos.close();
        }

    }


    public void actualizarUsuario(Usuario usu) throws Exception {
        SQLiteDatabase baseDatos = this.getWritableDatabase();
        try {
            String consulta = "UPDATE usuarios SET nombre = ?, salario = ?, telefono = ?, fecha_nacimiento = ?, estado_civil = ?, direccion = ?, tipo = ?, contrasena = ? WHERE cedula = ?";
            String[] argumentos = {usu.getNom(), Double.toString(usu.getSal()), Integer.toString(usu.getTel()), usu.getFecha_nacimiento(), usu.getEstado_civil(), usu.getDireccion(), Integer.toString(usu.getTipo()), usu.getContraseña(), usu.getCed()};

            baseDatos.execSQL(consulta, argumentos);
        } finally {
            baseDatos.close();
        }
    }


}


















