package com.albaugh.foodalbaugh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.time.*;


public class Pedidos extends AppCompatActivity {
    TextView usuario,txtDia,txtSemana,txtDesc;
    RadioButton Op1,Op2,Op3;
    Button btnAtras,btnSiguiente,btnPedir,btnFinalizar;
    DatabaseReference ref,refOp1,refOp2,refOp3,refInicio,refFinal;
    FirebaseDatabase database;
    String vInicio,vFinal;
    String valor = "";
    String Desc1,Desc2,Desc3 = "";
    int Pos = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pedidos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usuario = findViewById(R.id.textUsu);
        Op1 = findViewById(R.id.opOp1);
        Op2 = findViewById(R.id.opOp2);
        Op3 = findViewById(R.id.opOp3);
        btnAtras = findViewById(R.id.btnAtras);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnPedir = findViewById(R.id.btnPedir);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        database = FirebaseDatabase.getInstance();
        txtSemana = findViewById(R.id.txtSemana);
        txtDesc = findViewById(R.id.txtDesc);
        txtDia = findViewById(R.id.txtDia);


        refOp1 = database.getReference("Dia1/op1");
        refOp2 = database.getReference("Dia1/op2");
        refOp3 = database.getReference("Dia1/op3");
        refInicio = database.getReference("Inicio");
        refFinal = database.getReference("Final");



        usuario.setText(Datos.Usuario);
        Op1.setChecked(true);
        btnAtras.setEnabled(false);
        refInicio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vInicio = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        refFinal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vFinal = snapshot.getValue().toString();
                txtSemana.setText("Semana del " + vInicio + " al " + vFinal);
                txtDia.setText(getFecha(Pos));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        refOp1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtDesc.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pos++;
                if(Pos>1)btnAtras.setEnabled(true);
                if(Pos == 7)btnSiguiente.setEnabled(false);
                txtDia.setText(getFecha(Pos));
                refOp1 = database.getReference("Dia"+Pos+"/op1");
                refOp2 = database.getReference("Dia"+Pos+"/op2");
                refOp3 = database.getReference("Dia"+Pos+"/op3");
                Op1.setChecked(true);
                refOp1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            txtDesc.setText(snapshot.getValue().toString());
                            btnPedir.setEnabled(true);
                        }catch(Exception e){
                            txtDesc.setText("NULL");
                            btnPedir.setEnabled(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pos--;
                if(Pos<7)btnSiguiente.setEnabled(true);
                if(Pos == 1)btnAtras.setEnabled(false);
                txtDia.setText(getFecha(Pos));
                refOp1 = database.getReference("Dia"+Pos+"/op1");
                refOp2 = database.getReference("Dia"+Pos+"/op2");
                refOp3 = database.getReference("Dia"+Pos+"/op3");
                Op1.setChecked(true);
                refOp1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            txtDesc.setText(snapshot.getValue().toString());
                            btnPedir.setEnabled(true);
                        }catch(Exception e){
                            txtDesc.setText("NULL");
                            btnPedir.setEnabled(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        Op1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    refOp1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                txtDesc.setText(snapshot.getValue().toString());
                                btnPedir.setEnabled(true);
                            }catch(Exception e){
                                txtDesc.setText("NULL");
                                btnPedir.setEnabled(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        Op2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    refOp2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                txtDesc.setText(snapshot.getValue().toString());
                                btnPedir.setEnabled(true);
                            }catch(Exception e){
                                txtDesc.setText("NULL");
                                btnPedir.setEnabled(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        Op3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    refOp3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            txtDesc.setText(snapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DelAl = vInicio + " al " + vFinal;
                DelAl = DelAl.replaceAll("/","-");
                ref = database.getReference("Pedidos/semana/"+DelAl+"/"+Datos.DNI+"/dia"+Pos+"/");
                APedir aPedir = new APedir();
                String sel = "";
                if(Op1.isChecked()){
                    sel = "Opcion 1";
                    aPedir.Opcion = sel;
                }
                if(Op2.isChecked()){
                    sel = "Opcion 2";
                    aPedir.Opcion = sel;
                }
                if(Op3.isChecked()){
                    sel = "Opcion 3";
                    aPedir.Opcion = sel;
                }
                aPedir.Comida = txtDesc.getText().toString();
                ref.setValue(aPedir);
                Toast.makeText(Pedidos.this, "En el dia" + Pos + " pediste la " + sel,Toast.LENGTH_LONG).show();
                btnPedir.setEnabled(false);
            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrir = new Intent(Pedidos.this,vistaPedido.class);
                startActivity(abrir);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        /*Datos.Usuario = "";
        Datos.Conectado = false;
        finish();*/
    }

    public String getFecha(int pos){
        int position  = pos - 1;
        String Fecha = "";

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

            // Convertir el string a un objeto Date
            Date date = dateFormat.parse(vInicio);

            // Crear un objeto Calendar y establecer la fecha
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(calendar.DAY_OF_YEAR,position);

            // Obtener el día de la semana en texto (ej. "martes")
            String dayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));

            // Obtener el día, mes y año
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1; // Sumar 1 porque Enero es 0
            int year = calendar.get(Calendar.YEAR);
            Fecha = dayOfWeek +" "+ day+"/"+month+"/"+ year;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Fecha;
    }
    private static String getDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Domingo";
            case Calendar.MONDAY:
                return "Lunes";
            case Calendar.TUESDAY:
                return "Martes";
            case Calendar.WEDNESDAY:
                return "Miércoles";
            case Calendar.THURSDAY:
                return "Jueves";
            case Calendar.FRIDAY:
                return "Viernes";
            case Calendar.SATURDAY:
                return "Sábado";
            default:
                return "";
        }
    }
}