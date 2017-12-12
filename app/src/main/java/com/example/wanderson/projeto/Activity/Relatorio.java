package com.example.wanderson.projeto.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Map;


import com.example.wanderson.projeto.R;

import org.w3c.dom.Text;

import java.util.HashMap;
/**
 * Created by Pedro Wege on 08/12/2017.
 */

public class Relatorio extends AppCompatActivity {
    private EditText dataFinal;
    private EditText dataInicial;
    private Button gerarRelatorio;
    private TextView resultado;

    //dados simulados de banco de dados, apenas para proposito de testes
    private Map<Date, Integer> resultadoDict = new HashMap<Date, Integer>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        try{
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            resultadoDict.put(df.parse("11/12/2017"),5);
            resultadoDict.put(df.parse("10/12/2017"),4);
            resultadoDict.put(df.parse("09/12/2017"),3);
            resultadoDict.put(df.parse("08/12/2017"),2);
            resultadoDict.put(df.parse("07/12/2017"),1);
            resultadoDict.put(df.parse("06/12/2017"),5);
        }
        catch (ParseException e) {
            Toast.makeText(Relatorio.this, "Erro: "+ e.toString(), Toast.LENGTH_SHORT).show();
        }


        dataFinal = (EditText) findViewById(R.id.editFim);
        dataInicial = (EditText) findViewById(R.id.editInicio);
        gerarRelatorio = (Button) findViewById(R.id.btnGerarRelatorio);
        resultado = (TextView) findViewById(R.id.resultado);

        gerarRelatorio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String textFim = dataFinal.getText().toString();
                String textInicio = dataInicial.getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date diaFim = null;
                Date diaInicio = null;
                try{
                    diaFim = df.parse(textFim);
                    diaInicio = df.parse(textInicio);
                }
                catch(Exception e){
                    Toast.makeText(Relatorio.this, "Erro1: "+ e.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                int i = 0;
                //int somaNotas = 0;

                Calendar c = Calendar.getInstance();

                c.setTime(diaFim);
                c.add(Calendar.DATE, -1);
                diaFim = c.getTime();

                c.setTime(diaInicio);
                c.add(Calendar.DATE, 1);
                diaInicio = c.getTime();

                String resul ="Data              Nota";

                for (Map.Entry<Date, Integer> entry : resultadoDict.entrySet() ) {
                    Date dia = entry.getKey();
                    int nota = entry.getValue();
                   // if(true){
                    if(dia.before(diaInicio) && dia.after(diaFim)){
                        //Toast.makeText(Relatorio.this, dia.toString(), Toast.LENGTH_SHORT).show();

                        i += 1;
                        resul = resul + "\n" + df.format(dia) + ": " + String.valueOf(nota);
                    }
                }
//                if (i==0){
//                    resultado.setText("Nenhuma nota encontrada");
                resultado.setText(resul);
//                }
//                else{
//                    double media = somaNotas/i;
//                    resultado.setText("Sua média é: "+String.valueOf(media));
//                }



//                if (valorFim.toString().isEmpty()) {
//                    Toast.makeText(Relatorio.this, "Por favor preencher o campo 'Data Final'", Toast.LENGTH_SHORT).show();
//                    dataFinal.requestFocus();
//                } else if (valorInicio.toString().isEmpty()) {
//                    Toast.makeText(Relatorio.this, "Por favor preencher o campo 'Data Inicial'", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (result <= 0.7) {
//                        resultado.setText("É melhor utilizar o Álcool!");
//                    } else {
//                        resultado.setText("É melhor utilizar a Gasolina!");
//                    }
//                }

            }
        });
    }
}
