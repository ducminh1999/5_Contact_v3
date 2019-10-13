package com.example.a5_contact_v3;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a5_contact_v3.adapter.Contact;
import com.example.a5_contact_v3.adapter.CustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText edtSearch;
    private FloatingActionButton fabAdd;
    private ImageButton imgbtnSearch;
    private ArrayList<Contact> contacts;
    private ListView lvContact;
    private CustomAdapter<Contact> customAdapter;
    MyDataBase db;
    int vitri;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvContact = (ListView) findViewById(R.id.lv_contact);
        edtSearch=findViewById(R.id.edt_search);
        imgbtnSearch=findViewById(R.id.imgbtn_search);
        contacts = new ArrayList<Contact>();
        db = new MyDataBase(this);
        contacts = db.getAllContact();

        customAdapter = new CustomAdapter<Contact>(this, android.R.layout.simple_list_item_1, contacts);
        lvContact.setAdapter(customAdapter);
        lvContact.deferNotifyDataSetChanged();
        Widget();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchItem(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void searchItem(String search) {
//        for (String item:items){
//            if(!item.contains(search)){
//                contacts.remove(item);
//            }
//        }
        for (Contact item:contacts){
            //item.getmName().toString();
            Log.d("debug1", "iff");
            Log.d("debug1", item.getmName().toString());
            if(!item.getmName().toString().contains(search)){
                contacts.remove(item.getmName().toString());
            }
        }
    }

    public void Widget() {
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainAddContactActivity.class);
                startActivityForResult(intent, 10);
            }
        });
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("debug1", "LV");
                vitri = position;
                Intent intent = new Intent(MainActivity.this, MainEditContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", contacts.get(vitri));
                intent.putExtra("package", bundle);
                startActivityForResult(intent, 100);
            }
        });
    }


    //    public  void Search(){
//        edtSearch = (EditText) findViewById(R.id.edt_search);
//        imgbtnSearch = findViewById(R.id.imgbtn_search);
//        String search = edtSearch.getText().toString();
//        imgbtnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                edtSearch.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        MainActivity.this.customAdapter.getFilter().filter(s);
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                    }
//                });
//            }
//        });
//
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("debug1", "comming back");
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("package");
            Contact contact = (Contact) bundle.getSerializable("contact");
            if (data != null) {
                contacts.add(contact);
                db.addContact(contact);
                db.getAllContact();
                customAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("package");
            Contact contact = (Contact) bundle.getSerializable("contact");
            contact.setmId(vitri + 1);
            db.updateContact(contact);
            db.getAllContact();
            contacts.set(vitri, contact);
            customAdapter.notifyDataSetChanged();
        }
    }
}
