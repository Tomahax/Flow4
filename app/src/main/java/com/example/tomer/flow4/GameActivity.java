package com.example.tomer.flow4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttons[][] = new ImageButton[5][5];
    private Spot spots[][] = new Spot[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buttons = populateArray(buttons);

        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                char x = (char) (i);
                char y = (char) (k);
                String str = "ib" + x + y;

                //doesn't work for some reason..
//                int resId = getResources().getIdentifier(str, "id", getPackageName());
//                View v = findViewById(resId);

//                Toast.makeText(this, str, Toast.LENGTH_LONG).show();

//                buttons[i][k] = (ImageButton) v;

                //creates a spot for each button
                spots[i][k] = new Spot("red",false,i,k);

                buttons[i][k].setOnClickListener(this);
                buttons[i][k].setClickable(true);

                buttons[0][0].setImageResource(R.drawable.rfull);
            }
        }

//        buttons[0][0] = (ImageButton) findViewById(R.id.ib00);
//        buttons[0][0].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(GameActivity.this, "Clicked", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }


    public ImageButton[][] populateArray(ImageButton[][] buttons) {
        buttons[0][0] = (ImageButton) findViewById(R.id.ib00);
        buttons[0][1] = (ImageButton) findViewById(R.id.ib01);
        buttons[0][2] = (ImageButton) findViewById(R.id.ib02);
        buttons[0][3] = (ImageButton) findViewById(R.id.ib03);
        buttons[0][4] = (ImageButton) findViewById(R.id.ib04);
        buttons[1][0] = (ImageButton) findViewById(R.id.ib10);
        buttons[1][1] = (ImageButton) findViewById(R.id.ib11);
        buttons[1][2] = (ImageButton) findViewById(R.id.ib12);
        buttons[1][3] = (ImageButton) findViewById(R.id.ib13);
        buttons[1][4] = (ImageButton) findViewById(R.id.ib14);
        buttons[2][0] = (ImageButton) findViewById(R.id.ib20);
        buttons[2][1] = (ImageButton) findViewById(R.id.ib21);
        buttons[2][2] = (ImageButton) findViewById(R.id.ib22);
        buttons[2][3] = (ImageButton) findViewById(R.id.ib23);
        buttons[2][4] = (ImageButton) findViewById(R.id.ib24);
        buttons[3][0] = (ImageButton) findViewById(R.id.ib30);
        buttons[3][1] = (ImageButton) findViewById(R.id.ib31);
        buttons[3][2] = (ImageButton) findViewById(R.id.ib32);
        buttons[3][3] = (ImageButton) findViewById(R.id.ib33);
        buttons[3][4] = (ImageButton) findViewById(R.id.ib34);
        buttons[4][0] = (ImageButton) findViewById(R.id.ib40);
        buttons[4][1] = (ImageButton) findViewById(R.id.ib41);
        buttons[4][2] = (ImageButton) findViewById(R.id.ib42);
        buttons[4][3] = (ImageButton) findViewById(R.id.ib43);
        buttons[4][4] = (ImageButton) findViewById(R.id.ib44);

        return buttons;


    }

}
