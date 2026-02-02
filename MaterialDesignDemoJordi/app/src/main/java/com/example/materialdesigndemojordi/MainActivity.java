package com.example.materialdesigndemojordi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstFragment(), "Primera Pestaña");
        adapter.addFragment(new SecondFragment(), "Segunda Pestaña");
        adapter.addFragment(new ThirdFragment(), "Tercera Pestaña");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {

            Toast.makeText(MainActivity.this, "Toast: FAB pulsado", Toast.LENGTH_SHORT).show();


            Snackbar.make(v, "FAB clickeado", Snackbar.LENGTH_LONG)
                    .setAction("DESHACER", view -> {
                        Snackbar.make(v, "Acción deshecha", Snackbar.LENGTH_SHORT).show();
                    })
                    .show();
        });
    }
}
