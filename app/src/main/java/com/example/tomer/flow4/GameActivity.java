package com.example.tomer.flow4;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystemNotFoundException;

import pl.droidsonroids.gif.GifImageView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton buttons[][];
    private Spot spots[][];
    private Board board;
    private String difficulty;

    private String curLevel = "level 1";

    private int prevColor;

    private Long curTime;

    private TextView difficulityTextView;
    private TextView levelTextView;

    //animation variable creation
    GifImageView animationImageView;
    AnimationDrawable winAnimation;

    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            animationImageView.setVisibility(View.VISIBLE);
        }
    };


    //List view setup
    private ListView lst;
    String[] levels = {"level 1", "level 2", "level 3", "level 4", "level 5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        difficulty = getIntent().getExtras().getString("difficulty");

        Toast.makeText(this, readFinishTime(), Toast.LENGTH_SHORT).show();

        if (difficulty.equals("easy"))
            setContentView(R.layout.activity_game);
        else if (difficulty.equals("medium"))
            setContentView(R.layout.activity_game_m);
        else
            setContentView(R.layout.activity_game_h);

        //Setting up level info
        difficulityTextView = (TextView) findViewById(R.id.difficulty_text_view);
        levelTextView = (TextView) findViewById(R.id.level_text_view);
        difficulityTextView.setText(this.difficulty);
        levelTextView.setText(curLevel);
        //Setting up animation
        animationImageView = (GifImageView) findViewById(R.id.animation_image_view);
        animationImageView.bringToFront();
        animationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
            }
        });





        //Setting up the ListView items
        lst = (ListView) findViewById(R.id.list_levels);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, levels);
        lst.setAdapter(arrayAdapter);
        lst.setOnItemClickListener(this);


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


        for (int i = 0; i < board.getMisparShurot(); i++) {
            for (int k = 0; k < board.getMisparAmudot(); k++) {
                char x = (char) (i);
                char y = (char) (k);
                String str = "ib" + x + y;

                //doesn't work for some reason..
//                int resId = getResources().getIdentifier(str, "id", getPackageName());
//                View v = findViewById(resId);

//                Toast.makeText(this, str, Toast.LENGTH_LONG).show();

//                buttons[i][k] = (ImageButton) v;

                //creates a spot for each button
                //spots[i][k] = new Spot(0, false, i, k);

                buttons[i][k].setOnClickListener(this);
                buttons[i][k].setClickable(true);


            }

        }

        //populates spots
        for (int i = 0; i < board.getMisparShurot(); i++) {
            for (int k = 0; k < board.getMisparAmudot(); k++) {
                spots[i][k] = new Spot(0, false, i, k);
            }
        }


        //colors the board and populates spots colors
        try {
            loadLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //Selects pressed level and calls loadLevel() with the new level
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view;
        this.curLevel = tv.getText().toString();
        levelTextView.setText(curLevel);
        try {
            loadLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int row = Character.getNumericValue(String.valueOf(v.getTag()).charAt(0));
        int col = Character.getNumericValue(String.valueOf(v.getTag()).charAt(1));

        try {
            doTurn(row, col, spots[row][col].getColor());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, row + "" + col, Toast.LENGTH_SHORT).show();

    }

    private void doTurn(int row, int col, int color) throws IOException {
        Spot spot = spots[row][col];
        ImageButton button = buttons[row][col];
        Spot spotTop = null;
        Spot spotBottom = null;
        Spot spotLeft = null;
        Spot spotRight = null;

        if (row < (spots[0].length - 1))
            spotTop = spots[row + 1][col];
        if (row > 0)
            spotBottom = spots[row - 1][col];
        if (col < (spots[0].length - 1))
            spotLeft = spots[row][col + 1];
        if (col > 0)
            spotRight = spots[row][col - 1];


        if (color == 0) {
            if (spotTop != null && spotTop.isStartClicked()) {
                spotTop.setStartClicked(false);
                spot.setColor(prevColor);
                spot.setStartClicked(true);

                setImageButtonColor(button, prevColor);
            } else if (spotBottom != null && spotBottom.isStartClicked()) {
                spotBottom.setStartClicked(false);
                spot.setColor(prevColor);
                spot.setStartClicked(true);

                setImageButtonColor(button, prevColor);


            } else if (spotLeft != null && spotLeft.isStartClicked()) {
                spotLeft.setStartClicked(false);
                spot.setColor(prevColor);
                spot.setStartClicked(true);

                setImageButtonColor(button, prevColor);
            } else if (spotRight != null && spotRight.isStartClicked()) {
                spotRight.setStartClicked(false);
                spot.setColor(prevColor);
                spot.setStartClicked(true);

                setImageButtonColor(button, prevColor);

            }

        } else if (spot.isSorE()) {
            if (spotBottom != null)
                spotBottom.setStartClicked(false);
            if (spotTop != null)
                spotTop.setStartClicked(false);
            if (spotRight != null)
                spotRight.setStartClicked(false);
            if (spotLeft != null)
                spotLeft.setStartClicked(false);

            spot.setStartClicked(true);
            clearAllStartClicked(spot);
            checkWin(this.spots, this.board);
        } else {
            spot.setColor(0);
            setImageButtonColor(button, 0);
        }

        if (spot.getColor() != 0)
            prevColor = spot.getColor();

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


    public void writeFinishTime(String time) throws IOException {
        BufferedWriter writer;

        //writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("time.txt", Context.MODE_PRIVATE)));
        try {
            FileOutputStream fileOutputStream = openFileOutput("hello_file", MODE_PRIVATE);
            fileOutputStream.write(time.getBytes());
            fileOutputStream.close();
            Toast.makeText(this, "Time Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public String readFinishTime() {
        try {
            String Message;
            FileInputStream fileInputStream = openFileInput("hello_file");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((Message = bufferedReader.readLine()) != null) {
                stringBuffer.append(Message + "\n");
            }
            return "Last Finish Time: " + stringBuffer.toString() + " Seconds";
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed to read file";
    }


    public void loadLevel() throws IOException {
        //resets the board to be empty
        ResetBoard();

        curTime = System.currentTimeMillis();

        BufferedReader reader = null;
        String strLine;
        String[] lineParts;
        boolean readNow = false;
        int curRow;
        int curCol;


        try {

            //which difficulty?
            if (difficulty.equals("easy"))
                reader = new BufferedReader(new InputStreamReader(getAssets().open("info.txt")));
            else if (difficulty.equals("medium"))
                reader = new BufferedReader(new InputStreamReader(getAssets().open("infom.txt")));
            else
                reader = new BufferedReader(new InputStreamReader(getAssets().open("infoh.txt")));

            while ((strLine = reader.readLine()) != null && !(strLine.equals("#") && readNow)) {
                //Toast.makeText(this, strLine, Toast.LENGTH_SHORT).show();


                if (readNow && !(strLine.equals("#"))) {

                    lineParts = strLine.split(";");

                    curRow = Character.getNumericValue(lineParts[0].charAt(0));
                    curCol = Character.getNumericValue(lineParts[1].charAt(0));

                    //sets starting and ending points

                    //test
                    if (spots[curRow][curCol] == null) {
                        Toast.makeText(this, curRow + " " + curCol, Toast.LENGTH_SHORT).show();
                    }


                    //sets colors images
                    if (lineParts[2].equals("1")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.rfull);
                        spots[curRow][curCol].setColor(1);
                        spots[curRow][curCol].setSorE(true);
                    } else if (lineParts[2].equals("2")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.bfull);
                        spots[curRow][curCol].setColor(2);
                        spots[curRow][curCol].setSorE(true);
                    } else if (lineParts[2].equals("3")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.gfull);
                        spots[curRow][curCol].setColor(3);
                        spots[curRow][curCol].setSorE(true);
                    } else if (lineParts[2].equals("4")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.yfull);
                        spots[curRow][curCol].setColor(4);
                        spots[curRow][curCol].setSorE(true);
                    } else if (lineParts[2].equals("5")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.ofull);
                        spots[curRow][curCol].setColor(5);
                        spots[curRow][curCol].setSorE(true);
                    } else if (lineParts[2].equals("6")) {
                        buttons[curRow][curCol].setImageResource(R.drawable.pfull);
                        spots[curRow][curCol].setColor(6);
                        spots[curRow][curCol].setSorE(true);
                    }
                }

                if (strLine.equals(this.curLevel)) {
                    readNow = true;
                }

            }
            //String data []= strLine.split(";");


            reader.close();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            // Toast.makeText(this, reader.readLine(), Toast.LENGTH_LONG).show();
        }
    }

    private void checkWin(Spot[][] spots, Board board) throws IOException {
        boolean full = true;
        int row = board.getMisparShurot();
        int col = board.getMisparAmudot();


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (spots[i][j].getColor() == 0)
                    full = false;
            }
        }

        if (full) {
            //Toast.makeText(this, "you win!", Toast.LENGTH_SHORT).show();
            startService(new Intent(this, MyService.class));
            displayWinDialog();

            writeFinishTime(Long.toString((System.currentTimeMillis() - curTime) / 1000));


            //Display animation
            mAnimationRunnable.run();
        }
    }



    public ImageButton[][] populateArray(ImageButton[][] buttons) {
        if (this.difficulty.equals("easy")) {
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

        } else if (this.difficulty.equals("medium")) {
            buttons[0][0] = (ImageButton) findViewById(R.id.ib00);
            buttons[0][1] = (ImageButton) findViewById(R.id.ib01);
            buttons[0][2] = (ImageButton) findViewById(R.id.ib02);
            buttons[0][3] = (ImageButton) findViewById(R.id.ib03);
            buttons[0][4] = (ImageButton) findViewById(R.id.ib04);
            buttons[0][5] = (ImageButton) findViewById(R.id.ib05);
            buttons[1][0] = (ImageButton) findViewById(R.id.ib10);
            buttons[1][1] = (ImageButton) findViewById(R.id.ib11);
            buttons[1][2] = (ImageButton) findViewById(R.id.ib12);
            buttons[1][3] = (ImageButton) findViewById(R.id.ib13);
            buttons[1][4] = (ImageButton) findViewById(R.id.ib14);
            buttons[1][5] = (ImageButton) findViewById(R.id.ib15);
            buttons[2][0] = (ImageButton) findViewById(R.id.ib20);
            buttons[2][1] = (ImageButton) findViewById(R.id.ib21);
            buttons[2][2] = (ImageButton) findViewById(R.id.ib22);
            buttons[2][3] = (ImageButton) findViewById(R.id.ib23);
            buttons[2][4] = (ImageButton) findViewById(R.id.ib24);
            buttons[2][5] = (ImageButton) findViewById(R.id.ib25);
            buttons[3][0] = (ImageButton) findViewById(R.id.ib30);
            buttons[3][1] = (ImageButton) findViewById(R.id.ib31);
            buttons[3][2] = (ImageButton) findViewById(R.id.ib32);
            buttons[3][3] = (ImageButton) findViewById(R.id.ib33);
            buttons[3][4] = (ImageButton) findViewById(R.id.ib34);
            buttons[3][5] = (ImageButton) findViewById(R.id.ib35);
            buttons[4][0] = (ImageButton) findViewById(R.id.ib40);
            buttons[4][1] = (ImageButton) findViewById(R.id.ib41);
            buttons[4][2] = (ImageButton) findViewById(R.id.ib42);
            buttons[4][3] = (ImageButton) findViewById(R.id.ib43);
            buttons[4][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[4][5] = (ImageButton) findViewById(R.id.ib45);
            buttons[5][0] = (ImageButton) findViewById(R.id.ib50);
            buttons[5][1] = (ImageButton) findViewById(R.id.ib51);
            buttons[5][2] = (ImageButton) findViewById(R.id.ib52);
            buttons[5][3] = (ImageButton) findViewById(R.id.ib53);
            buttons[5][4] = (ImageButton) findViewById(R.id.ib54);
            buttons[5][5] = (ImageButton) findViewById(R.id.ib55);
        }
        if (this.difficulty.equals("hard")) {
            buttons[0][0] = (ImageButton) findViewById(R.id.ib00);
            buttons[0][1] = (ImageButton) findViewById(R.id.ib01);
            buttons[0][2] = (ImageButton) findViewById(R.id.ib02);
            buttons[0][3] = (ImageButton) findViewById(R.id.ib03);
            buttons[0][4] = (ImageButton) findViewById(R.id.ib04);
            buttons[0][5] = (ImageButton) findViewById(R.id.ib05);
            buttons[0][6] = (ImageButton) findViewById(R.id.ib06);
            buttons[1][0] = (ImageButton) findViewById(R.id.ib10);
            buttons[1][1] = (ImageButton) findViewById(R.id.ib11);
            buttons[1][2] = (ImageButton) findViewById(R.id.ib12);
            buttons[1][3] = (ImageButton) findViewById(R.id.ib13);
            buttons[1][4] = (ImageButton) findViewById(R.id.ib14);
            buttons[1][5] = (ImageButton) findViewById(R.id.ib15);
            buttons[1][6] = (ImageButton) findViewById(R.id.ib16);
            buttons[2][0] = (ImageButton) findViewById(R.id.ib20);
            buttons[2][1] = (ImageButton) findViewById(R.id.ib21);
            buttons[2][2] = (ImageButton) findViewById(R.id.ib22);
            buttons[2][3] = (ImageButton) findViewById(R.id.ib23);
            buttons[2][4] = (ImageButton) findViewById(R.id.ib24);
            buttons[2][5] = (ImageButton) findViewById(R.id.ib25);
            buttons[2][6] = (ImageButton) findViewById(R.id.ib26);
            buttons[3][0] = (ImageButton) findViewById(R.id.ib30);
            buttons[3][1] = (ImageButton) findViewById(R.id.ib31);
            buttons[3][2] = (ImageButton) findViewById(R.id.ib32);
            buttons[3][3] = (ImageButton) findViewById(R.id.ib33);
            buttons[3][4] = (ImageButton) findViewById(R.id.ib34);
            buttons[3][5] = (ImageButton) findViewById(R.id.ib35);
            buttons[3][6] = (ImageButton) findViewById(R.id.ib36);
            buttons[4][0] = (ImageButton) findViewById(R.id.ib40);
            buttons[4][1] = (ImageButton) findViewById(R.id.ib41);
            buttons[4][2] = (ImageButton) findViewById(R.id.ib42);
            buttons[4][3] = (ImageButton) findViewById(R.id.ib43);
            buttons[4][4] = (ImageButton) findViewById(R.id.ib44);
            buttons[4][5] = (ImageButton) findViewById(R.id.ib45);
            buttons[4][6] = (ImageButton) findViewById(R.id.ib46);
            buttons[5][0] = (ImageButton) findViewById(R.id.ib50);
            buttons[5][1] = (ImageButton) findViewById(R.id.ib51);
            buttons[5][2] = (ImageButton) findViewById(R.id.ib52);
            buttons[5][3] = (ImageButton) findViewById(R.id.ib53);
            buttons[5][4] = (ImageButton) findViewById(R.id.ib54);
            buttons[5][5] = (ImageButton) findViewById(R.id.ib55);
            buttons[5][6] = (ImageButton) findViewById(R.id.ib56);
            buttons[6][0] = (ImageButton) findViewById(R.id.ib60);
            buttons[6][1] = (ImageButton) findViewById(R.id.ib61);
            buttons[6][2] = (ImageButton) findViewById(R.id.ib62);
            buttons[6][3] = (ImageButton) findViewById(R.id.ib63);
            buttons[6][4] = (ImageButton) findViewById(R.id.ib64);
            buttons[6][5] = (ImageButton) findViewById(R.id.ib65);
            buttons[6][6] = (ImageButton) findViewById(R.id.ib66);

            //needs fixing
        }
        return buttons;


    }


    public void ResetBoard() {
        for (int i = 0; i < board.getMisparShurot(); i++) {
            for (int k = 0; k < board.getMisparAmudot(); k++) {
                spots[i][k].Reset();
                buttons[i][k].setImageResource(R.drawable.black1);
            }
        }
    }

    //turns off all startClicked spots on the board
    public void clearAllStartClicked(Spot spot) {
        for (int i = 0; i < board.getMisparShurot(); i++) {
            for (int k = 0; k < board.getMisparAmudot(); k++) {
                if (spot.getRow() != i && spot.getRow() != k) {
                    spots[i][k].setStartClicked(false);
                }
            }
        }
    }


    public void displayWinDialog() {
        //creates and shows the dialog with the received SMS
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(curLevel + " Finished");
        alertDialog.setMessage("You Win!!!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


//***************************************************************************************************
    //animation handling

    Runnable mAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    };




}
