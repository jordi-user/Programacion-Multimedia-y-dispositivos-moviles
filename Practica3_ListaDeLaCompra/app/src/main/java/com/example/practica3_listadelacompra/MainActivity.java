package com.example.practica3_listadelacompra;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
//import android.view.ViewGroup;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<com.example.practica3_listadelacompra.Item> shoppingList;
    private ShoppingListAdapter adapter;


    int[] images = { R.drawable.apple, R.drawable.bread, R.drawable.milk };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Pasa por aqui");
        Spinner spinner = findViewById(R.id.spinnerImages);
        EditText edtName = findViewById(R.id.edtName);
        EditText edtQuantity = findViewById(R.id.edtQuantity);
        Button btnAdd = findViewById(R.id.btnAdd);
        ListView listView = findViewById(R.id.listView);
        System.out.println("Pasa por aqui 2");
        ArrayAdapter<Integer> imgAdapter = new ArrayAdapter<Integer>(this, R.layout.simple_spinner_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(getItem(position));
                imageView.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(getItem(position));
                imageView.setLayoutParams(new ViewGroup.LayoutParams(120, 120));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        };

        spinner.setAdapter(imgAdapter);

        shoppingList = new ArrayList<>();
        System.out.println("Pasa por aqui 3");
        adapter = new ShoppingListAdapter(this, shoppingList);
        System.out.println("Pasa por aqui 4");
        //listView.setAdapter(adapter);
        System.out.println("Pasa por aqui 5");
        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String qtyText = edtQuantity.getText().toString();

            if (name.isEmpty() || qtyText.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int qty = Integer.parseInt(qtyText);
            int img = images[spinner.getSelectedItemPosition()];

            shoppingList.add(new com.example.practica3_listadelacompra.Item(name, qty, img));
            adapter.notifyDataSetChanged();

            edtName.setText("");
            edtQuantity.setText("");
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Añadir nuevo artículo");
        menu.add(0, 2, 0, "Eliminar artículo");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case 1:
                shoppingList.add(new com.example.practica3_listadelacompra.Item("Nuevo", 1, images[0]));
                adapter.notifyDataSetChanged();
                return true;
            case 2:
                if (info != null) {
                    shoppingList.remove(info.position);
                    adapter.notifyDataSetChanged();
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
}