package com.kaique.p2.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.kaique.p2.R;

public class PerfilActivity extends AppCompatActivity {
    TextView nome;
    TextView email;
    String usuarioId;
    ImageView img;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nome= findViewById(R.id.idnome);
        email= findViewById(R.id.idemail);
        img = findViewById(R.id.imgId);
    }
    @Override
    protected void onStart() {
        super.onStart();
        String em = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    nome.setText(value.getString("nome"));
                    email.setText(em);
                }
            }
        });
    }
    public void acao(View v){
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(it);

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap imagem = (Bitmap) extras.get("data");

                        img.setImageBitmap(imagem);
                    }
                }
            });
}