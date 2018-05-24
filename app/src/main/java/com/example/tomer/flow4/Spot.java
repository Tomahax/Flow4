package com.example.tomer.flow4;

/**
 * Created by tomer on 24/05/2018.
 */

public class Spot {

    private String color;
    private boolean SorE;
    private int row;
    private int col;
    //Already pressed, previous Spot
    private boolean startClicked;

    public Spot(String color, boolean SorE, int row, int col)
    {
        this.color = "#000000";
        this.SorE = SorE;
        this.row = row;
        this.col = col;
        this.startClicked = false;
    }

    public void setColor(String color)
    {
        this.color =color;
    }

}
