package com.example.practica5_materialdesign;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practica5_materialdesign.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Fragment1 extends Fragment {

    private TextInputLayout inputLayout;
    private TextInputEditText editText;
    private Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1_layout, container, false);


        inputLayout = view.findViewById(R.id.inputLayout);
        editText = view.findViewById(R.id.editText);
        btnSubmit = view.findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(v -> {
            String text = editText.getText().toString();


            if (TextUtils.isEmpty(text)) {
                inputLayout.setError("El campo no puede estar vacío");
            } else {
                inputLayout.setError(null);
                Toast.makeText(getContext(),
                        "Formulario enviado: " + text,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
