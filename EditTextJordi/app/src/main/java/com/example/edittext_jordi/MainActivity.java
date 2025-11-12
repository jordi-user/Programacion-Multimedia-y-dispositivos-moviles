package com.example.edittext_jordi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

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
        setContentView(R.layout.linearseek);
      seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              textViewProgress.setText("Valor: " + progress);
              public void onStartTracckingTouch (SeekBar seekBar){

                  public void onStopTrackingTouch (SeekBar seekBar){

                  }
              }
          }
      }
    }
}
