package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class CityActivity extends AppCompatActivity {

    private String mapSateLite="No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);


        final Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text_sel = (TextView)spinner.getSelectedView();

                if(text_sel.getText().equals("Downtown Toronto"))
                {
                    Intent intent = new Intent(view.getContext(),MapsActivity.class);
                    intent.putExtra("sateliteView",mapSateLite);
                    intent.putExtra("city","Downtown Toronto");
                    startActivity(intent);
                }
                else if(text_sel.getText().equals("Scarborough"))
                {
                    Intent intent = new Intent(view.getContext(),MapsActivity.class);
                    intent.putExtra("sateliteView",mapSateLite);
                    intent.putExtra("city","Scarborough");
                    startActivity(intent);
                }
                else if(text_sel.getText().equals("North York"))
                {
                    Intent intent = new Intent(view.getContext(),MapsActivity.class);
                    intent.putExtra("sateliteView",mapSateLite);
                    intent.putExtra("city","North York");
                    startActivity(intent);
                }
                else if(text_sel.getText().equals("Mississauga"))
                {
                    Intent intent = new Intent(view.getContext(),MapsActivity.class);
                    intent.putExtra("city","Mississauga");
                    intent.putExtra("sateliteView",mapSateLite);
                    startActivity(intent);
                }
                else if(text_sel.getText().equals("Markham"))
                {
                    Intent intent = new Intent(view.getContext(),MapsActivity.class);
                    intent.putExtra("city","Markham");
                    intent.putExtra("sateliteView",mapSateLite);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onCheckboxClicked(View view)
    {
        CheckBox checkBox = findViewById(R.id.checkBox);
        if(checkBox.isChecked())
            mapSateLite = "Yes";
        else
            mapSateLite = "No";
    }



}
