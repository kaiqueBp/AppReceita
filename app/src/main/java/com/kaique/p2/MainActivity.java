package com.kaique.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CaracterList.OnListSelected {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_root, new CaracterList(), "characterList")
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
                .replace(R.id.container_root, fragment, "fragmentDetail")
                .addToBackStack(null)
                .commit();
    }
}