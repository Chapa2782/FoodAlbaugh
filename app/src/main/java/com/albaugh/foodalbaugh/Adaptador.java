package com.albaugh.foodalbaugh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context contexto;
    String dias;
    String comidas;

    public Adaptador(Context conexto,String dias,String comidas){
        this.contexto = conexto;
        this.dias = dias;
        this.comidas = comidas;
        inflater = (LayoutInflater)contexto.getSystemService(conexto.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.vista_lista,null);
        TextView dia = vista.findViewById(R.id.txtDia);
        TextView comida = vista.findViewById(R.id.txtComida);

        dia.setText(dias.toString());
        comida.setText(comidas.toString());
        return vista;
    }

    @Override
    public int getCount() {
        return 0;//return dias.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
