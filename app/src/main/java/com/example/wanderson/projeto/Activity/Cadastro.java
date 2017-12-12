package com.example.wanderson.projeto.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wanderson.projeto.DAO.ConfigFirebase;
import com.example.wanderson.projeto.Entidades.Base64Custom;
import com.example.wanderson.projeto.Entidades.Usuarios;
import com.example.wanderson.projeto.Helper.Preferencias;
import com.example.wanderson.projeto.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class Cadastro extends AppCompatActivity {

    private EditText editCadEmail;
    private EditText editCadNome;
    private EditText editCadSobreNome;
    private EditText editCadSenha;
    private EditText editCadConfirmaSenha;
    private RadioButton rgMasculino;
    private RadioButton rgFeminino;
    private Button btnGravar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editCadEmail = (EditText)findViewById(R.id.edCadEmail);
        editCadNome = (EditText)findViewById(R.id.edCadNome);

        editCadSenha = (EditText)findViewById(R.id.edCadSenha);
        editCadConfirmaSenha = (EditText)findViewById(R.id.edCadConfirmarSenha);
        editCadSobreNome = (EditText)findViewById(R.id.edCadSobreNome);

        rgFeminino = (RadioButton) findViewById(R.id.rgFeminino);
        rgMasculino = (RadioButton) findViewById(R.id.rgMasculino);

        btnGravar = (Button) findViewById(R.id.btnGravar);

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editCadSenha.getText().toString().equals(editCadConfirmaSenha.getText().toString())){
                    usuarios = new Usuarios();
                    usuarios.setNome(editCadNome.getText().toString());
                    usuarios.setEmail(editCadEmail.getText().toString());
                    usuarios.setSenha(editCadSenha.getText().toString());
                    usuarios.setSobrenome(editCadSobreNome.getText().toString());
                 if(rgFeminino.isChecked()){
                     usuarios.setSexo("Feminino");
                 } else{
                     usuarios.setSexo("Masculino");
                 }

                 cadastrarUsuarios();
                }else{
                    Toast.makeText(Cadastro.this,"As senhas não são correspodentes",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private void cadastrarUsuarios(){
        autenticacao = ConfigFirebase.getFirebaseAutentificacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Cadastro.this,"Usuario Cadastrado com Sucesso!",Toast.LENGTH_LONG).show();

                    String idenficadorUsuario = Base64Custom.codificadorBase64(usuarios.getEmail());
                    FirebaseUser usuarioFireBase = task.getResult().getUser();
                    usuarios.setId(idenficadorUsuario);
                    usuarios.Salvar();

                    Preferencias preferencias = new Preferencias(Cadastro.this);
                    preferencias.salvarUsuarioPreferencias(idenficadorUsuario,usuarios.getNome());

                    abrirLoginUsuario();

                }else{
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao ="Digite uma senha mais forte, contendo no minimo 8 caracteres de letras e numeros";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao ="O e-mail digitado é invalido,digite um novo e-mail";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao ="Esse email já está cadastrado no sistema";
                    }catch (Exception e){
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(Cadastro.this,"Erro:" + erroExcecao,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void abrirLoginUsuario(){
        Intent intent = new Intent(Cadastro.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
