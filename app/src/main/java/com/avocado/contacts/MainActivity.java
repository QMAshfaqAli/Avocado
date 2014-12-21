package com.avocado.contacts;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements OnQueryTextListener {

    private ArrayList<String> items = new ArrayList<String>();
    private Menu mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items.add("Ashfaq");
        items.add("Ashraf");
        items.add("Aditya");
        items.add("Porosh");
        items.add("Ashad");
        items.add("Adil");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.mainMenu = menu;

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadHistory(String query) {

        String[] columns = new String[]{"_id", "text"};
        Object[] temp = new Object[]{0, ""};

        MatrixCursor cursor = new MatrixCursor(columns);

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).toLowerCase().contains(query)) {
                temp[0] = i;
                temp[1] = items.get(i);
                cursor.addRow(temp);
            }
        }

//        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView search = (SearchView) mainMenu.findItem(R.id.search).getActionView();
//        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setSuggestionsAdapter(new ContactSearchAdapter(this, cursor, items));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        loadHistory(s);
        return true;
    }
}
