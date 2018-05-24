package com.example.tomer.flow4;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttons[][];
    private Spot spots[][];
    private Board board;
    private String difficulty;
    private GameManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        difficulty = getIntent().getExtras().getString("difficulty");


        if (difficulty.equals("easy")) {
            board = new Board(5, 5);
        } else if (difficulty.equals("medium")) {
            board = new Board(6, 6);
        } else {
            board = new Board(7, 7);
        }

        buttons = new ImageButton[board.getMisparShurot()][board.getMisparAmudot()];
        spots = new Spot[board.getMisparShurot()][board.getMisparAmudot()];


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
                spots[i][k] = new Spot(1, false, i, k);

                buttons[i][k].setOnClickListener(this);
                buttons[i][k].setClickable(true);


            }

            //colors the board and populates spots colors
            try {
                loadLevel();
            } catch (IOException e) {
                e.printStackTrace();
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
        int row = Character.getNumericValue(String.valueOf(v.getTag()).charAt(0));
        int col = Character.getNumericValue(String.valueOf(v.getTag()).charAt(1));

        doTurn(row, col, spots[row][col].getColor());
        Toast.makeText(this, row + "" + col, Toast.LENGTH_SHORT).show();

    }

    private void doTurn(int row, int col, int color) {
        Spot spot = spots[row][col];
        ImageButton button = buttons[row][col];
        Spot spotTop = null;
        Spot spotBottom = null;
        Spot spotLeft = null;
        Spot spotRight = null;
        if (row < 4)
            spotTop = spots[row + 1][col];
        if (row > 0)
            spotBottom = spots[row - 1][col];
        if (col < 4)
            spotLeft = spots[row][col + 1];
        if (col > 0)
            spotRight = spots[row][col - 1];

        if (color == 0) {
            if (spotTop != null && spotTop.isStartClicked()) {
                spotTop.setStartClicked(false);
                spot.setColor(spotTop.getColor());
                spot.setStartClicked(true);

                setImageButtonColor(button, spotTop.getColor());

            } else if (spotBottom != null && spotBottom.isStartClicked()) {

                spotBottom.setStartClicked(false);
                spot.setColor(spotBottom.getColor());
                spot.setStartClicked(true);

                setImageButtonColor(button, spotBottom.getColor());

            } else if (spotLeft != null && spotLeft.isStartClicked()) {

                spotLeft.setStartClicked(false);
                spot.setColor(spotLeft.getColor());
                spot.setStartClicked(true);

                setImageButtonColor(button, spotLeft.getColor());

            } else if (spotRight != null && spotRight.isStartClicked()) {

                spotRight.setStartClicked(false);
                spot.setColor(spotRight.getColor());
                spot.setStartClicked(true);

                setImageButtonColor(button, spotRight.getColor());

            }

            //spot.setColor(2);
            //button.setImageResource(R.drawable.rfull);
        } else {
            spot.setStartClicked(true);
        }
    }

    public void setImageButtonColor(ImageButton button, int color) {
        if (color == 0) {
            button.setImageResource(R.drawable.black1);
        } else if (color == 1) {
            button.setImageResource(R.drawable.rfull);
        } else if (color == 2) {
            button.setImageResource(R.drawable.bfull);
        } else if (color == 3) {
            button.setImageResource(R.drawable.gfull);
        } else if (color == 4) {
            button.setImageResource(R.drawable.yfull);
        } else if (color == 5) {
            button.setImageResource(R.drawable.ofull);
        } else if (color == 6) {
            button.setImageResource(R.drawable.pfull);
        }
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

        if (this.difficulty.equals("medium")) {
            buttons[5][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[5][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[5][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[5][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[5][4] = (ImageButton) findViewById(R.id.ib44);

            //needs fixing

        }
        if (this.difficulty.equals("hard")) {
            buttons[4][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[4][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[4][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[4][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[4][4] = (ImageButton) findViewById(R.id.ib44);
        }
        return buttons;


    }

    public void loadLevel() throws IOException {
        BufferedReader reader = null;
        String strLine;
        String[] lineParts;
        boolean readNow = false;
        int curRow;
        int curCol;

        try {

            reader = new BufferedReader(new InputStreamReader(getAssets().open("info.txt")));

            while ((strLine = reader.readLine()) != null && !(strLine.equals("#") && readNow)) {
                //Toast.makeText(this, strLine, Toast.LENGTH_SHORT).show();


                if (readNow) {
                    lineParts = strLine.split(";");
                    for (int i = 0; i < lineParts.length; i++) {
                        //Toast.makeText(this, lineParts[i], Toast.LENGTH_SHORT).show();
                    }

                    curRow = Character.getNumericValue(lineParts[0].charAt(0));
                    curCol = Character.getNumericValue(lineParts[1].charAt(0));

                    //counts all spots coordinates
                    //Toast.makeText(this, curRow + "" + curCol, Toast.LENGTH_SHORT).show();

                    spots[curRow][curCol].setSorE(true);

                    //sets colors images
                    if (lineParts[2].equals("1")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.rfull);
                        spots[curRow][curCol].setColor(1);
                    } else if (lineParts[2].equals("2")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.bfull);
                        spots[curRow][curCol].setColor(2);
                    } else if (lineParts[2].equals("3")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.gfull);
                        spots[curRow][curCol].setColor(3);
                    } else if (lineParts[2].equals("4")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.yfull);
                        spots[curRow][curCol].setColor(4);
                    } else if (lineParts[2].equals("5")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.ofull);
                        spots[curRow][curCol].setColor(5);
                    } else if (lineParts[2].equals("6")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.pfull);
                        spots[curRow][curCol].setColor(6);
                    }
                }

                if (strLine.equals("level 1")) {
                    readNow = true;
                }

            }
            //String data []= strLine.split(";");


            reader.close();

        } catch (
                Exception e)

        {
            File f = new File("info.txt");
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
