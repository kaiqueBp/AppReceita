package com.kaique.p2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CaracterDetalhes extends Fragment {

    public static CaracterDetalhes newInstance() {
        return new CaracterDetalhes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Caracter character = (Caracter) getArguments().getSerializable("detail");

        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(character.getImageResId());

        TextView nameTextView = view.findViewById(R.id.name);
        nameTextView.setText(character.getName());

        TextView descriptionTextView = view.findViewById(R.id.description);
        descriptionTextView.setText(character.getDescription());

        return view;
    }
}

