package com.example.materialdesigndemojordi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ThirdFragment extends Fragment {

    private TextInputLayout textInputLayoutEmail;
    private TextInputEditText editTextEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail);
        editTextEmail = view.findViewById(R.id.editTextEmail);


        editTextEmail.setOnEditorActionListener((v, actionId, event) -> {
            String email = editTextEmail.getText().toString().trim();
            if(!email.isEmpty()){
                Toast.makeText(getContext(), "Email ingresado: " + email, Toast.LENGTH_SHORT).show();
            } else {
                textInputLayoutEmail.setError("El correo no puede estar vacío");
            }
            return true;
        });
    }
}
