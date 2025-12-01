package com.example.practica3_listadelacompra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<com.example.practica3_listadelacompra.Item> items;

    public ShoppingListAdapter(Context context, ArrayList<com.example.practica3_listadelacompra.Item> items) {
        this.context = context;
        this.items = items;
    }

    public ShoppingListAdapter(MainActivity context, ArrayList<com.example.practica3_listadelacompra.Item> shoppingList) {
    }

    @Override
    public int getCount() { return items.size(); }

    @Override
    public Object getItem(int position) { return items.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_shopping, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.imgItem);
        TextView name = convertView.findViewById(R.id.txtName);
        TextView quantity = convertView.findViewById(R.id.txtQuantity);
        Button delete = convertView.findViewById(R.id.btnDelete);

        com.example.practica3_listadelacompra.Item currentItem = items.get(position);

        img.setImageResource(currentItem.getImageResId());
        name.setText(currentItem.getName());
        quantity.setText("Cantidad: " + currentItem.getQuantity());

        delete.setOnClickListener(v -> {
            items.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}