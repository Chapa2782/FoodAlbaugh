package com.albaugh.foodalbaugh;

import android.graphics.Path;

public class PedidosItems {
    String Dia;
    String Comida;
    String Opcion;

    public PedidosItems(){

    }
    public PedidosItems(String D,String C,String O){
        this.Comida = C;
        this.Opcion = O;
        this.Dia  = D;
    }


    public String getDia(){
        return Dia;
    }
    public String getComida(){
        return Comida;
    }
    public String getOpcion(){
        return Opcion;
    }
    public void setComida(String Comida){
        this.Comida = Comida;
    }
    public void setOpcion(String Opcion){
        this.Opcion = Opcion;
    }
    public void setDia(String Dia){
        this.Dia = Dia;
    }
}
