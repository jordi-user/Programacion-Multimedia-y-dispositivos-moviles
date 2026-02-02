package com.example.gestionactivityjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnAddEvent;
    ListView listView;
    ArrayList<Evento> eventos = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddEvent = findViewById(R.id.btnAddEvent);
        listView = findViewById(R.id.listViewEvents);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(adapter);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }


        cargarEventos();

        // Clicker: añadir evento
        btnAddEvent.setOnClickListener(v -> mostrarDialogoNombre());

        // Clicker: pulsar evento en la lista
        listView.setOnItemClickListener((parent, view, position, id) -> mostrarToastPersonalizado(eventos.get(position)));
    }

    void actualizarLista() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Evento e : eventos) {
            nombres.add(e.toString());
        }
        adapter.clear();
        adapter.addAll(nombres);
        adapter.notifyDataSetChanged();
    }

    void mostrarDialogoNombre() {
        EditText editText = new EditText(this);
        editText.setHint("Nombre del evento");

        new AlertDialog.Builder(this)
                .setTitle("Nuevo Evento")
                .setView(editText)
                .setPositiveButton("Siguiente", (dialog, which) -> {
                    String nombre = editText.getText().toString();
                    if (!nombre.isEmpty()) {
                        mostrarDatePicker(nombre);
                    } else {
                        Toast.makeText(this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    void mostrarDatePicker(String nombre) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
                    mostrarTimePicker(nombre, fecha);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    void mostrarTimePicker(String nombre, String fecha) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    String hora = String.format("%02d:%02d", hourOfDay, minute);
                    Evento evento = new Evento(nombre, fecha, hora);
                    eventos.add(evento);
                    actualizarLista();
                    guardarEventos(); // Guardar después de añadir
                    mostrarNotificacion(evento);
                    notificacionRetrasada(evento);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show();
    }

    void mostrarNotificacion(Evento evento) {
        String channelId = "evento_channel";
        String channelName = "Eventos";
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Evento creado")
                .setContentText(evento.toString())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }

    void notificacionRetrasada(Evento evento) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> mostrarNotificacion(evento), 5000);
    }

    void mostrarToastPersonalizado(Evento evento) {
        Toast toast = new Toast(this);
        TextView tv = new TextView(this);
        tv.setText(evento.toString());
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16f);
        tv.setPadding(24, 16, 24, 16);
        tv.setBackgroundResource(R.drawable.toast_background);

        toast.setView(tv);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



    private void guardarEventos() {
        SharedPreferences prefs = getSharedPreferences("EVENTOS_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray jsonArray = new JSONArray();
        try {
            for (Evento e : eventos) {
                JSONObject obj = new JSONObject();
                obj.put("nombre", e.getNombre());
                obj.put("fecha", e.getFecha());
                obj.put("hora", e.getHora());
                jsonArray.put(obj);
            }
            editor.putString("eventos", jsonArray.toString());
            editor.apply();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void cargarEventos() {
        SharedPreferences prefs = getSharedPreferences("EVENTOS_PREFS", MODE_PRIVATE);
        String json = prefs.getString("eventos", null);

        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                eventos.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Evento e = new Evento(
                            obj.getString("nombre"),
                            obj.getString("fecha"),
                            obj.getString("hora")
                    );
                    eventos.add(e);
                }
                actualizarLista();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
