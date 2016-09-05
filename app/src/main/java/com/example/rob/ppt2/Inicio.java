package com.example.rob.ppt2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Inicio extends AppCompatActivity {

   boolean interrupted=true;
    Statement st;
    Connection connect;
    ResultSet rs;
    TextView LbMostrar;
    Button BtIniciar;
    int jj,oo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        LbMostrar = (TextView)findViewById(R.id.LbMostrar);
        BtIniciar = (Button)findViewById(R.id.BtIniciar);
        ConexSQL connecti = new ConexSQL();

        connect=connecti.ConnectionHelper();
        try {
            st = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        BtIniciar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new MiTarea().execute();
            }
        });
        LbMostrar.setText("cambiando");


    }






private class MiTarea extends AsyncTask<String,String, Integer> {

    protected Integer doInBackground(String... urls) {
        while (interrupted) {
         //  leerBD();
            publishProgress("ajaaja"); //Actualizamos los valores
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }
    protected void onProgressUpdate (String... update){
        try {
            rs=  st.executeQuery("SELECT J,O,Band FROM dbo.RBD");
            rs.next();
            // Bande = rs.getString("Band");
            jj=Integer.parseInt(rs.getString("J")) ;
            oo=Integer.parseInt(rs.getString("O")) ;
            if (rs.getString("Band").equals("1")){

                  st.executeUpdate("update dbo.RBD set J='2',O='1',Band='0'");
                    LbMostrar.setText(tipo(jj) + " " + tipo(oo));
                   //LbMostrar.setText(tipo(Integer.parseInt(rs.getString("J")+","+tipo(Integer.parseInt(rs.getString("J"))))));


            }else if (rs.getString("Band").equals("5")||rs.getString("Band").equals("6")){
                interrupted=false;
                LbMostrar.setText("El Juego ha terminado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
    void leerBD(){
      //  String Bande;
        try {
          rs=  st.executeQuery("SELECT J,O,Band FROM dbo.RBD");
            rs.next();
           // Bande = rs.getString("Band");

            if (rs.getString("Band").equals("1")){

              rs=  st.executeQuery("update dbo.RBD set J='2',O='1',Band='0'");
             //   LbMostrar.setText(tipo(Integer.parseInt(rs.getString("J")+","+tipo(Integer.parseInt(rs.getString("J"))))));
                LbMostrar.setText("si entro");
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Has " + rs+ " el Juego", Toast.LENGTH_SHORT);

                toast1.show();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String tipo(int n){
     switch (n){
         case 1:
             return "piedra";

         case 2:
             return "papel";

         case 3:
             return "tijera";

         default://se le acabo el timpo punto para oponente
             return "Time Out";

     }

    }

}