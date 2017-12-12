package com.example.wanderson.projeto.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanderson.projeto.R;

public class CalculoRentavel extends AppCompatActivity {

    private EditText precoAlcool;
    private EditText precoGasolina;
    private Button calcular;
    private TextView resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_rentavel);

        precoAlcool =(EditText)   findViewById(R.id.editPreAlcool);
        precoGasolina =(EditText) findViewById(R.id.editPreGasolina);
        calcular = (Button) findViewById(R.id.btnCalcular);
        resultado = (TextView)  findViewById(R.id.resultado);

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textPrecoAlcool = precoAlcool.getText().toString();
                String textPrecoGasolina = precoGasolina.getText().toString();

                Double valorAlcool = Double.parseDouble(textPrecoAlcool);
                Double valorGasolina = Double.parseDouble(textPrecoGasolina);



                double result = valorAlcool/valorGasolina;

                if(valorAlcool.toString().isEmpty()){
                    Toast.makeText(CalculoRentavel.this,"Por favor preencher o campo Álcool!",Toast.LENGTH_SHORT).show();
                    precoAlcool.requestFocus();
                }else if (valorGasolina.toString().isEmpty()){
                    Toast.makeText(CalculoRentavel.this,"Por favor preencher o campo Gasolina!",Toast.LENGTH_SHORT).show();
                }else {
                    if (result <= 0.7) {
                        resultado.setText("É melhor utilizar o Álcool!");
                    } else {
                        resultado.setText("É melhor utilizar a Gasolina!");
                    }
                }

            }
        });

    }
}
