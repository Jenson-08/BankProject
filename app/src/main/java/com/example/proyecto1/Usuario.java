package com.example.proyecto1;

import java.util.Objects;

public class Usuario {
        private String nom;
        private String ced;
        private int tel;
        private double sal;
        private String fecha_nacimiento;
        private String estado_civil;
        private String direccion;
        private int tipo;

        private String contraseña;

        // Constructor por defecto
        public Usuario() {
        }


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Usuario usuario = (Usuario) o;
                return tel == usuario.tel && Double.compare(usuario.sal, sal) == 0 && tipo == usuario.tipo && Objects.equals(nom, usuario.nom) && Objects.equals(ced, usuario.ced) && Objects.equals(fecha_nacimiento, usuario.fecha_nacimiento) && Objects.equals(estado_civil, usuario.estado_civil) && Objects.equals(direccion, usuario.direccion) && Objects.equals(contraseña, usuario.contraseña);
        }

        @Override
        public int hashCode() {
                return Objects.hash(nom, ced, tel, sal, fecha_nacimiento, estado_civil, direccion, tipo, contraseña);
        }

        // Getters
        public String getNom() {
                return nom;
        }

        public String getCed() {
                return ced;
        }

        public int getTel() {
                return tel;
        }

        public double getSal() {
                return sal;
        }

        public String getFecha_nacimiento() {
                return fecha_nacimiento;
        }

        public String getEstado_civil() {
                return estado_civil;
        }

        public String getDireccion() {
                return direccion;
        }

        public String getContraseña() {
                return contraseña;
        }
        public int getTipo() {
                return tipo;
        }

        // Setters
        public void setNom(String nom) {
                this.nom = nom;
        }

        public void setCed(String ced) {
                this.ced = ced;
        }

        public void setTel(int tel) {
                this.tel = tel;
        }

        public void setSal(double sal) {
                this.sal = sal;
        }

        public void setFecha_nacimiento(String fecha_nacimiento) {
                this.fecha_nacimiento = fecha_nacimiento;
        }

        public void setEstado_civil(String estado_civil) {
                this.estado_civil = estado_civil;
        }

        public void setDireccion(String direccion) {
                this.direccion = direccion;
        }

        public void setTipo(int tipo) {
                this.tipo = tipo;
        }

        public void setContraseña(String contraseña) {
                this.contraseña = contraseña;
        }

        public boolean verifica(double credid){
                if ((sal * 0.45) <= credid)
                        return true;
                return false;
        }
}

