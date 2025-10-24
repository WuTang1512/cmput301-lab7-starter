package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    TextView cityNameView;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        cityNameView = findViewById(R.id.cityName);
        backButton = findViewById(R.id.backButton);

        Intent intent = getIntent();
        String cityName = intent.getStringExtra(MainActivity.selectedCity);
        cityNameView.setText(cityName);

//        // This implementation creates a new instance of MainActivity and resets the dataList
//        // functionally, this means that any cities that we go back to a blank page
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent new_intent = new Intent(ShowActivity.this, MainActivity.class);
//                startActivity(new_intent);
//            }
//        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
