package com.example.tomer.flow4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void easy(View v) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("difficulty","easy");
        startActivity(i);
    }

    public void medium(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("difficulty","medium");
        startActivity(i);
    }

    public void hard(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("difficulty","hard");
        startActivity(i);
    }
}
