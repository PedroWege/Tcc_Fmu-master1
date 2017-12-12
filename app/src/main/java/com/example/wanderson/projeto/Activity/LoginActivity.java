package com.example.wanderson.projeto.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanderson.projeto.DAO.ConfigFirebase;
import com.example.wanderson.projeto.Entidades.Usuarios;
import com.example.wanderson.projeto.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText edtEmail;
    private EditText edtSenha;
    private TextView edAbreCadastro;
    private Button btnLogar;
    private TextView edResetSenha;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    private SignInButton btnSignIn;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtEmail = (EditText)findViewById(R.id.edEmail);
        edtSenha = (EditText)findViewById(R.id.edSenha);
        edResetSenha = (TextView)findViewById(R.id.edResetSenha);
        edAbreCadastro = (TextView)findViewById(R.id.tvAbreCadastro);
        btnLogar = (Button) findViewById(R.id.btnLogin);

        inicializarComponentes();
        inicializarFirebase();
        conectarGoogleApi();
        clickButton();


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){
                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());
                    validarLogin();
                }else{
                    Toast.makeText(LoginActivity.this,"Preencha os campos de e-mail e senha", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetSenha.class);
                startActivity(intent);
            }
        });
        edAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        });
    }

    private void clickButton() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    private void SignIn() {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseLogin(account);
            }
        }
    }

    private void firebaseLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        autenticacao.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(LoginActivity.this,Principal.class);
                            startActivity(i);
                        }else{
                            alert("Falha na autentificação");
                        }
                    }
                });
    }

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

    private  void inicializarFirebase(){
        autenticacao = FirebaseAuth.getInstance();
    }
    private void inicializarComponentes(){
        btnSignIn = (SignInButton) findViewById(R.id.btnSignIn);
    }

    private void validarLogin(){

        autenticacao = ConfigFirebase.getFirebaseAutentificacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this,"Login Realizado com Sucesso!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"Senha está errada!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void abrirTelaPrincipal(){
        Intent intentAbrirTelaPrincipal = new Intent(LoginActivity.this,Principal.class);
        startActivity(intentAbrirTelaPrincipal);
    }
    private void abreCadastroUsuario(){
        Intent intent = new Intent(LoginActivity.this,Cadastro.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        alert("Falha na conexao");
    }

    private void alert(String s) {
        Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
    }
}
