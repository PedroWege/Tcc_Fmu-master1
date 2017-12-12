package com.example.wanderson.projeto.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wanderson.projeto.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Principal extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    Button btnCalculo;
    Button btnSignOut;
    Button btnRelatorio;
    Button btnCheckPosto;

    boolean isCheckPostoChecked;//Checa se usuário é Posto

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mFirebaseAuth = FirebaseAuth.getInstance();

        btnCalculo = (Button)findViewById(R.id.btnCalculo);
        inicializarButton();
        //conectarGoogleApi();    //da pau quando chama esse metodo pois já chamado em LoginActivity.java
        clickButton();


        btnCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this,CalculoRentavel.class);
                startActivity(intent);
            }
        });
        btnRelatorio = (Button)findViewById(R.id.btnRelatorio);
        inicializarButton();
        //conectarGoogleApi(); //da pau quando chama esse metodo pois já chamado em LoginActivity.java
        clickButton();


        btnRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this,Relatorio.class);
                startActivity(intent);
            }
        });
        btnCheckPosto = (Button)findViewById(R.id.btnCheckPosto);
        inicializarButton();
        clickButton();
        btnCheckPosto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isCheckPostoChecked) {
                    alert("Botão Checado");
                    isCheckPostoChecked = true;
                } else {
                    alert("Botão Não Checado");
                    isCheckPostoChecked = false;
                }
            }
        });

    }
    private  void inicializarButton(){
        btnSignOut = (Button)findViewById(R.id.btnSignOut);
    }
    private void clickButton(){
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });
    }

    private void SignOut() {
        mFirebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                alert("Conta desconectada");
                finish();
            }
        });
    }


    //da pau quando chama esse metodo pois já chamado em LoginActivity.java
    private void conectarGoogleApi(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        alert("Falha na conexão");
    }

    private void alert(String s) {
        Toast.makeText(Principal.this,s,Toast.LENGTH_SHORT).show();
    }
}
