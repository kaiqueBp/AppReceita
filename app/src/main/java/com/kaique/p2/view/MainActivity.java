package com.kaique.p2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaique.p2.R;
import com.kaique.p2.model.Caracter;
import com.kaique.p2.vm.CaracterDetalhes;
import com.kaique.p2.vm.CaracterList;

public class MainActivity extends AppCompatActivity implements CaracterList.OnListSelected {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.idDrawer);
        navigationView= findViewById(R.id.navView);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.fechar,R.string.abrir);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.Frame, new CaracterList(), "characterList")
                    .commit();
        }
    }

    @Override
    public void onSelected(Caracter caracter) {
        Bundle args = new Bundle();
        args.putSerializable("detail", caracter);

        CaracterDetalhes fragment = new CaracterDetalhes();
        fragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Frame, fragment, "fragmentDetail")
                .addToBackStack(null)
                .commit();
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    public void Pp(MenuItem item) {
        Intent intent= new Intent(MainActivity.this, PerfilActivity.class);
        startActivity(intent);
    }
    public void Deslogar(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent(MainActivity.this, LoginActivity2.class);
        startActivity(intent);
        finish();
    }

}