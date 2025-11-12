package com.example.barcuentajorid; // <-- revisa tu paquete

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.*;
import android.view.View;

import com.example.barcuentajorid.R;

public class MainActivity extends AppCompatActivity {

    EditText editTotal;
    CheckBox checkPropina;
    SeekBar seekPropina;
    TextView textPorcentaje, textResultado;
    RadioGroup groupPago;
    RatingBar ratingServicio;
    Button btnCalcular;
    AutoCompleteTextView autoCamarero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTotal = findViewById(R.id.editTotal);
        checkPropina = findViewById(R.id.checkPropina);
        seekPropina = findViewById(R.id.seekPropina);
        textPorcentaje = findViewById(R.id.textPorcentaje);
        textResultado = findViewById(R.id.textResultado);
        groupPago = findViewById(R.id.groupPago);
        ratingServicio = findViewById(R.id.ratingServicio);
        btnCalcular = findViewById(R.id.btnCalcular);
        autoCamarero = findViewById(R.id.autoCamarero);

        String[] camareros = {"Antonio", "María", "Lucía", "Pedro", "Juan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, camareros);
        autoCamarero.setAdapter(adapter);
        autoCamarero.setThreshold(3);

        seekPropina.setProgress(10);
        textPorcentaje.setText(getString(R.string.propina_text, seekPropina.getProgress()));
        seekPropina.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textPorcentaje.setText(getString(R.string.propina_text, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCuenta();
            }
        });
    }

    private void calcularCuenta() {
        String totalStr = editTotal.getText().toString().trim();
        int colorError = ContextCompat.getColor(this, R.color.error_red);
        int colorDefault = ContextCompat.getColor(this, R.color.text_default);

        if (totalStr.isEmpty()) {
            textResultado.setTextColor(colorError);
            textResultado.setText(getString(R.string.error_faltavalor));
            return;
        }

        double total;
        try {
            total = Double.parseDouble(totalStr);
        } catch (NumberFormatException e) {
            textResultado.setTextColor(colorError);
            textResultado.setText(getString(R.string.error_numero));
            return;
        }

        if (total <= 0) {
            textResultado.setTextColor(colorError);
            textResultado.setText(getString(R.string.error_mayorcero));
            return;
        }

        double propina = 0;
        if (checkPropina.isChecked()) {
            int porcentaje = seekPropina.getProgress();
            propina = total * porcentaje / 100.0;
        }

        double totalFinal = total + propina;

        int idSeleccionado = groupPago.getCheckedRadioButtonId();
        String metodoPago = "—";
        if (idSeleccionado != -1) {
            RadioButton seleccionado = findViewById(idSeleccionado);
            metodoPago = seleccionado.getText().toString();
        }

        float rating = ratingServicio.getRating();

        String camarero = autoCamarero.getText().toString().trim();
        if (camarero.isEmpty()) camarero = "-";

        String resultado = "Camarero: " + camarero +
                "\nTotal sin propina: " + String.format("%.2f", total) + " €" +
                "\nPropina: " + String.format("%.2f", propina) + " €" +
                "\nMétodo de pago: " + metodoPago +
                "\nValoración servicio: " + rating + " estrellas" +
                "\nTOTAL FINAL: " + String.format("%.2f", totalFinal) + " €";

        textResultado.setTextColor(colorDefault);
        textResultado.setText(resultado);
    }
}
