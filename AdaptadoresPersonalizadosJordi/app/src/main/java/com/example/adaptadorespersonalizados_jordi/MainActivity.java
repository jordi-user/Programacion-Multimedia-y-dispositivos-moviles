package com.example.adaptadorespersonalizados_jordi;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Datos[] datos=new Datos[] {
                new Datos("Linea principal 1","Linea inferior 1"),
                new Datos("Linea principal 2","Linea inferior 2"),
                new Datos("Linea principal 3","Linea inferior 3"),
                new Datos("Linea principal 4","Linea inferior 4")};
        }
    }
}