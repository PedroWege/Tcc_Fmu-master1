package com.example.wanderson.projeto.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wanderson on 15/11/2017.
 */

public class ConfigFirebase {
    private static DatabaseReference referenciaFireBase;
    private static FirebaseAuth autentificacao;

    public static DatabaseReference getFirebase(){
        if(referenciaFireBase == null){
            referenciaFireBase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFireBase;
    }
    public static FirebaseAuth getFirebaseAutentificacao(){
        if(autentificacao == null){
            autentificacao = FirebaseAuth.getInstance();
        }
        return autentificacao;
    }
}
