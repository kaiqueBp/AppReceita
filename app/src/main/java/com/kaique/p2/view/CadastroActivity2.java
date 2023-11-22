package com.kaique.p2.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaique.p2.R;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity2 extends AppCompatActivity {
    private TextView email;
    private TextView senha;
    private  TextView nome;
    String usuarioId;
    private Button bt;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.idEmail);
        senha=findViewById(R.id.idSenha);
        bt=findViewById(R.id.idCadastro);
        nome= findViewById(R.id.idN);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() || nome.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!!!",Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),senha.getText().toString())
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        salvarNome();
                                        // Sign in success, update UI with the signed-in user's information
                                        // Log.d(TAG, "signInWithCustomToken:success");
                                        Snackbar snackbar = Snackbar.make(view,"Salvo com sucesso", Snackbar.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.BLUE);
                                        snackbar.show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        limpaCampos();
                                        telaloginl();
                                        // updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                        Snackbar snackbar = Snackbar.make(view,"Erro ao salvar", Snackbar.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.RED);
                                        snackbar.show();
                                        // updateUI(null);
                                    }
                                }
                            });
                }
            }
        });
    }
    private void salvarNome(){
        String n = nome.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome",n);
        usuarioId =FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db error", "Falha ao salvar"+e.toString());
            }
        });
    }
    private void  telaloginl(){
        Intent intent= new Intent(this, LoginActivity2.class);
        startActivity(intent);
    }

    public void limpaCampos(){
        email.setText("");
        senha.setText("");
    }
}