package com.albaugh.foodalbaugh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.albaugh.foodalbaugh.Usuario;
import com.google.firebase.database.ValueEventListener;
import com.albaugh.foodalbaugh.Datos;



public class MainActivity extends AppCompatActivity {
    //Declaracion de elementos
    public Button Ingresar;
    public EditText DNI;
    public EditText Pass;
    FirebaseDatabase database;
    DatabaseReference ref;
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        Log.d("Boton",Datos.Conectado.toString());

        Ingresar = findViewById(R.id.btnIngresar);
        DNI = findViewById(R.id.textDNI);
        Pass = findViewById(R.id.textPass);

       Ingresar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Log.d("Boton","Ingreso al boton");
                ref = database.getReference("Usuario/" + DNI.getText().toString());
               ref.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       Usuario user = snapshot.getValue(Usuario.class);
                       try{
                           String con = user.pass;
                           String name = user.nombre;
                           if(con.equals(Pass.getText().toString())){
                               Log.d("Boton","Las contraseñas coinciden");
                               Toast.makeText(MainActivity.this,"Bienvenido " + name,Toast.LENGTH_LONG).show();
                               Intent i = new Intent(MainActivity.this,Pedidos.class);
                               startActivity(i);
                               Datos.Conectado = true;
                               Datos.Usuario = name;
                               Datos.DNI = Integer.parseInt(DNI.getText().toString());
                               Log.d("Mensaje",String.valueOf(Datos.DNI));
                           }else {
                               Log.d("Boton", "Las contraaseñas NO coinciden");
                               Log.d("Boton", con + " " + Pass.getText().toString());
                               Toast.makeText(MainActivity.this,"Contraseña incorrecta",Toast.LENGTH_LONG).show();
                           }
                       }catch(Exception e){
                           Log.d("Boton",e.toString());
                           Toast.makeText(MainActivity.this,"El usuario no existe",Toast.LENGTH_LONG).show();

                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
           }
       });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Usuario user = new Usuario();
        user.DNI = 28355727;
        user.nombre = "Sergio Pedroso";
        user.pass = "1234";

        ref = database.getReference("Usuario/" + user.DNI);

        ref.setValue(user);

    }
}