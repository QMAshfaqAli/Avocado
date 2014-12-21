package com.avocado.contacts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactSearchAdapter extends CursorAdapter {

    private ArrayList<String> items;

    private TextView text;

    public ContactSearchAdapter(Context context, Cursor cursor, ArrayList<String> items) {
        super(context, cursor, false);
        this.items = items;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText(items.get(Integer.parseInt(cursor.getString(0))));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item, parent, false);
        text = (TextView) view.findViewById(R.id.item);
        return view;
    }
}
