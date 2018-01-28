package com.example.varni.mybudgie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final EditText date = (EditText)findViewById(R.id.date_editText);
        final EditText source = (EditText)findViewById(R.id.source_editText);
        final EditText value = (EditText)findViewById(R.id.value_editText);
        final EditText name = (EditText)findViewById(R.id.name_editText);
        final EditText desp = (EditText)findViewById(R.id.desp_editText);
        final Spinner type = (Spinner)findViewById(R.id.type_spinner);
        type.setAdapter(ArrayAdapter.createFromResource(this, R.array.type, R.layout.item_spinner));

        Button submit = (Button)findViewById(R.id.submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateText = date.getText().toString();
                String sourceText = source.getText().toString();
                String valueText = value.getText().toString();
                String nameText = name.getText().toString();
                String despText = desp.getText().toString();
                String typeText = type.getSelectedItem().toString();
                onBackPressed();
            }
        });
    }
}
