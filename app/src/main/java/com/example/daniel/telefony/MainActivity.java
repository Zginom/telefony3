package com.example.daniel.telefony;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.daniel.telefony.R.id.parent;
import static com.example.daniel.telefony.R.raw.sahti;
import static com.example.daniel.telefony.R.styleable.Toolbar;
import static com.example.daniel.telefony.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String[] telefon;
    private Button wyjdz;
    private Button muzyka;
    private Button stop;
    private MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.telefony);
        wyjdz = (Button)findViewById(R.id.wyjscie);
        muzyka = (Button)findViewById(R.id.muzyka);
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
                    MainActivity.this.finish();
                }
                if(telefon[pos].equals("Samsung Galaxy J5"))
                {
                    Intent intent = new Intent(context,Telefon2.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                if(telefon[pos].equals("Lenovo K5"))
                {
                    Intent intent = new Intent(context,Telefon3.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        });

}


    private void initResourecs() {
        Resources res = getResources();
        telefon = res.getStringArray(R.array.Telefony);
    }

    private void initTelefonyListView(){
        lv.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1,telefon));
    }

    public Dialog exit(View view)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Wyjscie");
        dialogBuilder.setMessage("Na pewno chcesz wyjść?");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Tak", new Dialog.OnClickListener(){
            public void onClick(DialogInterface dialog,int whichButton) {
                Toast.makeText(getApplicationContext(),"Wychodzę",Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }});
        dialogBuilder.setNegativeButton("Nie", new Dialog.OnClickListener(){
            public void onClick(DialogInterface dialog,int whichButton) {
                Toast.makeText(getApplicationContext(),"Anulowane",Toast.LENGTH_SHORT).show();
            }});
        return dialogBuilder.show();
    }

    public Dialog muzyka(View view)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final String[] options = {"Sahti","Viinamaen mies"};
        dialog.setTitle("Korpiklaani");
        dialog.setItems(options,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                Toast.makeText(getApplicationContext(),"Wybrałeś: " + options[pos],Toast.LENGTH_SHORT).show();
                if ( options[pos].equals("Sahti") )
                {
                    playSahti();
                }
                if ( options[pos].equals("Viinamaen mies") )
                {
                    playViinamaen();
                }
                else {}
            }
        });
        return dialog.show();
    }

    private void playSahti()
    {
        if ( media != null) {
            media.release();
        }
        media = MediaPlayer.create(this, R.raw.sahti);
        media.start();
    }

    private void playViinamaen()
    {
        if ( media != null) {
            media.release();
        }
        media = MediaPlayer.create(this, R.raw.hey);
        media.start();
    }

    public void stop ( View v)
    {
        if ( media != null )
        media.stop();
    }

    public void alert(View v )
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Prosty alert dialog");
        dialog.setMessage("w Kosmos prosty\ntak prosty że prościej sie juz nie da");
        dialog.show();
    }


    public Dialog custom(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View layout = getlejaut();
        dialog.setView(layout);
        dialog.setTitle("Moj wlasny dialog");
        dialog.setMessage("Moj wlasny message do lejauta");
        return dialog.show();
    }

    private View getlejaut() {
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.alert,null);
    }

}
