package com.example.proyecto1;

public class Prestamo {
    private int id;
    private String persona;
    private double credito;
    private int periodo;
    private double tipo;

    private int mesesPorPagar;

    public Prestamo() {

    }
    public int getMesesPorPagar() {
        return this.mesesPorPagar;
    }
    public void setMesesPorPagar(int m){
        mesesPorPagar = m;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersona() {
        return this.persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public double getCredito() {
        return this.credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public int getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public double getTipo() {
        return this.tipo;
    }

    public void setTipo(double tipo) {
        this.tipo = tipo;
    }

    public double cuotaPorMeses(){
        double tasaInteresMensual = tipo / 12;
        double cuotaMensual = credito * tasaInteresMensual / (1 - Math.pow(1 + tasaInteresMensual, -(periodo*12)));

        return cuotaMensual;
    }

}
