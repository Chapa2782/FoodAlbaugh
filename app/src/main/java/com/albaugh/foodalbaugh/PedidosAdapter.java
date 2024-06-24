package com.albaugh.foodalbaugh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PedidosAdapter extends ArrayAdapter<PedidosItems> {
    private Context context;
    private List<PedidosItems> items;
    FirebaseDatabase database;
    DatabaseReference ref;

    public PedidosAdapter(Context context, List<PedidosItems> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.vista_lista, parent, false);
        }

        PedidosItems item = items.get(position);
        database = FirebaseDatabase.getInstance();

        TextView diaTextView = convertView.findViewById(R.id.txtDia);
        TextView comidaTextView = convertView.findViewById(R.id.txtComida);
        TextView opcionTextView = convertView.findViewById(R.id.textOpcion);
        ImageButton btnEliminar = convertView.findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = database.getReference("Pedidos/semana/"+Datos.DelAlFecha+"/"+Datos.DNI+"/");
                ref.child(item.getDia()).removeValue();
            }
        });


        diaTextView.setText(item.getDia());
        comidaTextView.setText(item.getComida());
        opcionTextView.setText(item.getOpcion());

        return convertView;
    }
}
