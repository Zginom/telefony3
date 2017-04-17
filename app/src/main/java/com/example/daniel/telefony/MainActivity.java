package com.example.daniel.telefony;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.daniel.telefony.R.id.parent;
import static com.example.daniel.telefony.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String[] telefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.telefony);
        initResourecs();
        initTelefonyListView();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                Context context;
                context = getApplicationContext();
                if(telefon[pos].equals("Huawei P9 Lite"))
                {
                    Intent intent = new Intent(context,Telefon1.class);
                    startActivity(intent);
                }
                if(telefon[pos].equals("Samsung Galaxy J5"))
                {
                    Intent intent = new Intent(context,Telefon2.class);
                    startActivity(intent);
                }
                if(telefon[pos].equals("Lenovo K5"))
                {
                    Intent intent = new Intent(context,Telefon3.class);
                    startActivity(intent);
                }
            }
        });

}
    private void initResourecs() {
        Resources res = getResources();
        telefon = res.getStringArray(R.array.Telefony);
        //pharses = res.getStringArray(R.array.Nazwy);
    }

    private void initTelefonyListView(){
        lv.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1,telefon));
    }



}
