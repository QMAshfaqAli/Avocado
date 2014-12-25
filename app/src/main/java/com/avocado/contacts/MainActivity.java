package com.avocado.contacts;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnQueryTextListener {

    GridView gvContacts;
    private List<String> items = new ArrayList<>();
    private Menu mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvContacts = (GridView) findViewById(R.id.gvContacts);
        getNumber(this.getContentResolver());

    }

    private void getNumber(ContentResolver cr) {

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, " display_name ASC ");

        if (cur.getCount() > 0) {
            List<Contact> contactList = new ArrayList<>();
            while (cur.moveToNext()) {
                String img = "", emailList = "";

                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                img = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));

                if (Integer
                        .parseInt(cur.getString(cur
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    // Query phone here. Covered next

                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        // Do something with phones
                        String phoneNo = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                        Cursor emailCur = cr
                                .query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                                + " = ?", new String[]{id},
                                        null);
                        while (emailCur.moveToNext()) {
                            String email = emailCur
                                    .getString(emailCur
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                            emailList += email + "; ";

                        }
                        emailCur.close();
                        Contact contactModel = new Contact(name, phoneNo, emailList, img);
                        items.add(name);
                        contactList.add(contactModel);
                    }
                    pCur.close();
                }
            }


            gvContacts.setAdapter(new ContactsAdapter(MainActivity.this, contactList));
        }
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

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
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

        final SearchView search = (SearchView) mainMenu.findItem(R.id.search).getActionView();
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
