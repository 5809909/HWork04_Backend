package com.github.a5809909.hwork04_backend;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boss.myapplication.backend.studentApi.StudentApi;
import com.example.boss.myapplication.backend.studentApi.model.CollectionResponseStudent;
import com.example.boss.myapplication.backend.studentApi.model.Student;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView studentTextView;
    Button getStudentButton;
    Button setStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentTextView = (TextView) findViewById(R.id.student_textview);
         final EditText idEditText = (EditText) findViewById(R.id.id_edittext);
         final EditText nameEditText = (EditText) findViewById(R.id.name_edittext);
         final EditText lastNameEditText = (EditText) findViewById(R.id.lastName_edittext);
        getStudentButton = (Button) findViewById(R.id.getStudent_button);
        setStudentButton = (Button) findViewById(R.id.setStudent_button);
        idEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if (length > 0) {
                    setStudentButton.setEnabled(true);
                } else {
                    setStudentButton.setEnabled(false);
                }
            }
        });

        getStudentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View View) {
                new GetEndpointsAsyncTask().execute();
            }
        });

        setStudentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View View) {
                String id = idEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                new SetEndpointsAsyncTask().execute(id, name, lastName);
            }
        });
    }

    class SetEndpointsAsyncTask extends AsyncTask<String, Void, Student> {
        private StudentApi myApiService = null;
        public static final String NO_DATA = "No data";

        @Override
        protected Student doInBackground(String... pVoids) {
            if (myApiService == null) {  // Only do this once
                myApiService = ApiBuilder.buildApi();
            }
            try {
                Long id = Long.parseLong(pVoids[0]);
                String name = pVoids[1];
                String lastName = pVoids[2];
                return myApiService.insert(id, name, lastName).execute();
            } catch (IOException e) {
                e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Student result) {
            Toast.makeText(getApplicationContext(), result.getId() + " Saved", Toast.LENGTH_SHORT).show();

        }
    }

    class GetEndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private StudentApi myApiService = null;

        @Override
        protected void onPreExecute() {
            getStudentButton.setEnabled(false);
        }

        @Override
        protected String doInBackground(Void... pVoids) {
            if (myApiService == null) {  // Only do this once
                myApiService = ApiBuilder.buildApi();
            }

            try {
                CollectionResponseStudent list = myApiService.list().execute();
                List<Student> students = list.getItems();
                String result = "";
                for (Student s : students) {
                    result += s.getId().toString() + " " + s.getName() + " "+ s.getLastName() + "\n";
                }
                return result;
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            studentTextView.setText(result);
            getStudentButton.setEnabled(true);
        }
    }


}
