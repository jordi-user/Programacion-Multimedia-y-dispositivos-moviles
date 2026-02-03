package com.example.practica5_materialdesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    private int contador = 0;
    private TextView tvContador;
    private Button btnSumar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);

        tvContador = view.findViewById(R.id.tvContador);
        btnSumar = view.findViewById(R.id.btnSumar);

        btnSumar.setOnClickListener(v -> {
            contador++;
            tvContador.setText("Contador: " + contador);
        });

        return view;
    }
}
