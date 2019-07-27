package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    String[] activities;
    ListView lstView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstView = findViewById(R.id.car_list);
        TextView textView = new TextView(getApplicationContext());
        textView.setText("Please choose your car brand:");

        lstView.addHeaderView(textView);
        lstView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        lstView.setTextFilterEnabled(true);

        activities = getResources().getStringArray(R.array.activities);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, activities);
        // Assign adapter to ListView
        lstView.setAdapter(adapter);
        SharedPreferences myPreference =
                getSharedPreferences("CarInfo", 0);

        final SharedPreferences.Editor prefEditor = myPreference.edit();

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                //String item = (String) lstView.getItemAtPosition(position);
                switch(itemPosition)
                {
                    case 1:
                       intent = new Intent(MenuActivity.this, CityActivity.class);
                       prefEditor.putString("CarBrand","Mercedes");
                        break;

                    case 2:
                       intent = new Intent(MenuActivity.this, CityActivity.class);

                        prefEditor.putString("CarBrand","Toyota");
                        break;
                    case 3:
                        intent = new Intent(MenuActivity.this, CityActivity.class);
                        prefEditor.putString("CarBrand","Honda");
                        break;
                    case 4:
                        intent = new Intent(MenuActivity.this, CityActivity.class);
                        prefEditor.putString("CarBrand","Audi");
                        break;
                    case 5:
                        intent = new Intent(MenuActivity.this, CityActivity.class);
                        prefEditor.putString("CarBrand","Lexus");
                        break;

                }
                // show next activity
                // intent.putExtra("activityName",(String) lstView.getItemAtPosition(position));
                prefEditor.commit();
                startActivity(intent);
            }
        });
    }



}
