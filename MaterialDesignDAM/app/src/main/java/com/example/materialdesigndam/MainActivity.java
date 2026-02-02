package com.example.materialdesigndam;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fab = findViewById(R.id.fab);

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Iconos en tabs (extras)
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_settings);

        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Acción realizada", Snackbar.LENGTH_LONG)
                    .setAction("Deshacer", v -> {
                        // Acción opcional al pulsar el botón del Snackbar
                    }).show();
        });
    }
}
