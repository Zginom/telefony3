package com.example.daniel.telefony;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String[] telefon;
    private Button record;
    private Button done;
    private Button play;
    private MediaPlayer media;
    private MediaRecorder recorder;
    private String mFileName;
    private Button wyjdz;
    private Button muzyka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.telefony);
        wyjdz = (Button)findViewById(R.id.wyjscie);
        muzyka = (Button)findViewById(R.id.muzyka);
        initResourecs();
        initTelefonyListView();

        record = (Button)findViewById(R.id.record);
        done = (Button)findViewById(R.id.stop_record);
        play = (Button)findViewById(R.id.play_record);

        done.setEnabled(false);
        play.setEnabled(false);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";





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

    ///---------///

    public void record( View v )
    {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(mFileName);

        record.setEnabled(false);
        done.setEnabled(true);
        play.setEnabled(false);
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();
    }

    public void stop_record ( View v )
    {
        record.setEnabled(true);
        play.setEnabled(true);
        done.setEnabled(false);
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public void play_record ( View v )
    {
        done.setEnabled(false);
        media = new MediaPlayer();

        try {
            media.setDataSource(mFileName);
            media.prepare();
            media.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void Save ( View view)
//    {
//        try {
//            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("mojplik.txt", MODE_APPEND));
//            EditText ET = (EditText) findViewById(R.id.edycja);
//            String text = ET.getText().toString();
//            out.write(text);
//            out.write('\n');
//            out.close();
//            Toast.makeText(this, "Text Saved !", Toast.LENGTH_SHORT).show();
//        }
//        catch (java.io.IOException e)
//        {
//            Toast.makeText(this,"Sorry cos sie ewidentnie ... nieudało",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void View ( View view)
//    {
//        StringBuilder text = new StringBuilder();
//        try{
//            InputStream instream = openFileInput("mojplik.txt");
//            if ( instream != null)
//            {
//                InputStreamReader inputreader = new InputStreamReader(instream);
//                BufferedReader buffreader = new BufferedReader(inputreader);
//                String line = null;
//                while(( line = buffreader.readLine() ) != null)
//                {
//                    text.append(line);
//                    text.append('\n');
//                }
//            }
//        }
//        catch ( IOException e)
//        {
//            e.printStackTrace();
//        }
//        TextView tv = (TextView)findViewById(R.id.tekst);
//        tv.setText(text);
//    }
//
//    public void SaveSD ( View view)
//    {
//        File sd = Environment.getExternalStorageDirectory();
//        File dir = new File(sd.getAbsolutePath() + "/MojePliki/");
//        dir.mkdir();
//        File file = new File(dir,"mojplik.txt");
//        EditText et = (EditText)findViewById(R.id.edycja);
//        String text = et.getText().toString();
//        try{
//            FileOutputStream os = new FileOutputStream(file,true);
//            os.write(text.getBytes());
//            os.write('\n');
//            os.close();
//            Toast.makeText(this, "Text Saved on SD card !", Toast.LENGTH_SHORT).show();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void ViewSD ( View view)
//    {
//        File sd = Environment.getExternalStorageDirectory();
//        File dir = new File(sd.getAbsolutePath() + "/MojePliki/");
//        File file = new File(dir,"mojplik.txt");
//        int length = (int)file.length();
//        byte[] bytes = new byte[length];
//        FileInputStream in;
//        try{
//            in = new FileInputStream(file);
//            in.read(bytes);
//            in.close();
//        }
//        catch ( FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch ( IOException e) {
//            e.printStackTrace();
//        }
//        String contents = new String(bytes);
//        TextView tv = (TextView) findViewById(R.id.tekst);
//        tv.setText(contents);
//    }
//
//    public void Clear ( View view)
//    {
//        TextView tv = (TextView) findViewById(R.id.tekst);
//        tv.setText(null);
//
//        File sd = Environment.getExternalStorageDirectory();
//        File dir = new File(sd.getAbsolutePath() + "/MojePliki/");
//        File file = new File(dir,"mojplik.txt");
//        file.delete();
//
//        String dir2 = getFilesDir().getAbsolutePath();
//        File file2 = new File(dir2,"mojplik.txt");
//        file2.delete();
//
//    }

}

