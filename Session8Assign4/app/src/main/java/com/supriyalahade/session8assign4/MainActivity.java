package com.supriyalahade.session8assign4;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView content;
    EditText text;
    Button ok, delete;
    static String FILENAME = "/test.txt";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        content = (TextView) findViewById(R.id.textView1);
        text = (EditText) findViewById(R.id.editText);
        ok = (Button) findViewById(R.id.button);
        delete = (Button) findViewById(R.id.delete_button);

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + FILENAME);
        } else {

            Toast.makeText(getApplicationContext(), "SD Cardd Not Found", Toast.LENGTH_LONG).show();
        }


        try {
            if (file.createNewFile()) {
                Toast.makeText(getApplicationContext(), "File Created", Toast.LENGTH_LONG).show();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button:

                String value = text.getText().toString();
                text.setText(" ");

                readFromFile rf = new readFromFile(file);
                rf.execute(value);
                break;

            case R.id.delete_button:

                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + FILENAME);
                boolean isSuccess=  file.delete(); //This is the command used for delete

                if(isSuccess == true){

                    Toast.makeText(getApplicationContext(),"File Deleted",Toast.LENGTH_LONG).show();
                }

        }

    }


    class readFromFile extends AsyncTask<String, Void, String> {

        File f;

        public readFromFile(File f) {
            super();
            this.f = f;
        }


        @Override
        protected String doInBackground(String... str) {

            String enter = "\n";
            FileWriter writer = null;

            try {

                writer = new FileWriter(f, true);
                writer.append(str[0]);
                writer.append(enter);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();


            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String name;
            StringBuilder sb = new StringBuilder();
            FileReader fr = null;

            try {
                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                while ((name = br.readLine()) != null) {
                    sb.append(name + "\n");
                }
                br.close();
                fr.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.setText(sb.toString());



        }
    }
}


