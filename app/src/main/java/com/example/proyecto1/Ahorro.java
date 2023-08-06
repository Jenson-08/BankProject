package com.example.proyecto1;

import java.util.Objects;

public class Ahorro {
    private String cedCliente;
    private String tipo_ahorro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ahorro ahorro = (Ahorro) o;
        return Double.compare(ahorro.cuota, cuota) == 0 && Objects.equals(cedCliente, ahorro.cedCliente) && Objects.equals(tipo_ahorro, ahorro.tipo_ahorro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedCliente, tipo_ahorro, cuota);
    }

    private double cuota;

    public Ahorro() {

    }

    public String getCedCliente() {
        return cedCliente;
    }

    public void setCedCliente(String cedCliente) {
        this.cedCliente = cedCliente;
    }

    public String getTipo_ahorro() {
        return tipo_ahorro;
    }

    public double getCuota() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    public void setTipo_ahorro(String tipo_ahorro) {
        this.tipo_ahorro = tipo_ahorro;
    }
}
