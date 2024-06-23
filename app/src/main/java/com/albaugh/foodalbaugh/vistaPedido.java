package com.albaugh.foodalbaugh;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class vistaPedido extends AppCompatActivity {
    Button btnModicar,btnOK;
    ListView Lista;
    FirebaseDatabase database;
    DatabaseReference ref;
    private ArrayList<PedidosItems> itemList;
    private PedidosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vista_pedido);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnModicar = findViewById(R.id.btnModicar);
        btnOK = findViewById(R.id.btnOK);
        Lista = findViewById(R.id.ListaPedidos);
        itemList = new ArrayList<>();
        adapter = new PedidosAdapter(this,itemList);
        Lista.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();

        ref = database.getReference("Pedidos/semana/14-06-24 al 20-06-24/28355727/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Mensaje/rotulo",snapshot.getValue().toString());
                Log.d("Mensaje",snapshot.getChildren().toString());
                itemList.clear();
                for (DataSnapshot snaps : snapshot.getChildren()) {
                    PedidosItems item = snaps.getValue(PedidosItems.class);
                    item.setDia(snaps.getKey().toString());

                    Log.d("Mensaje/dia",snaps.getKey().toString());
                    Log.d("Mensaje",item.Opcion.toString());
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnModicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
}