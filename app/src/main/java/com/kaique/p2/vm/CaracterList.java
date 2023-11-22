package com.kaique.p2.vm;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaique.p2.R;
import com.kaique.p2.model.Caracter;

public class CaracterList extends Fragment {
    private OnListSelected listener;
    private String[] names;
    private String[] descriptions;
    private int[] imageResIds;

    public static CaracterList newInstance() {
        return new CaracterList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        Context activity = getActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new CharacterListAdapter());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Resources resources = context.getResources();
        names = resources.getStringArray(R.array.names);
        descriptions = resources.getStringArray(R.array.descriptions);

        TypedArray typedArray = resources.obtainTypedArray(R.array.images);
        int imageCount = names.length;
        imageResIds = new int[imageCount];
        for (int i = 0; i < imageCount; i++) {
            imageResIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();

        if (context instanceof OnListSelected) {
            listener = (OnListSelected) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnListSelected");
        }
    }

    class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_list, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final Caracter caracter = new Caracter(names[position], descriptions[position], imageResIds[position]);
            viewHolder.bind(caracter);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(caracter);
                }
            });
        }

        @Override
        public int getItemCount() {
            return names.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View itemView) {
                super(itemView);
            }

            void bind(Caracter caracter) {
                ImageView imageView = itemView.findViewById(R.id.list_img);
                imageView.setImageResource(caracter.getImageResId());
                TextView textView = itemView.findViewById(R.id.list_name);
                textView.setText(caracter.getName());
            }
        }
    }

    public interface OnListSelected {
        void onSelected(Caracter caracter);
    }
}
