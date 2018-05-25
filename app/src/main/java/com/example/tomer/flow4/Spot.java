package com.example.tomer.flow4;

/**
 * Created by tomer on 24/05/2018.
 */

public class Spot {

    private int color;
    private boolean SorE;
    private int row;
    private int col;
    //Already pressed, previous Spot
    private boolean startClicked;

    public int getColor() {
        return color;
    }

    public boolean isSorE() {
        return SorE;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isStartClicked() {
        return startClicked;
    }



    public Spot(int color, boolean SorE, int row, int col)
    {
        this.color = 0;
        this.SorE = SorE;
        this.row = row;
        this.col = col;
        this.startClicked = false;
    }

    public void setSorE(boolean sorE) {
        SorE = sorE;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setStartClicked(boolean startClicked) {
        this.startClicked = startClicked;
    }

    public void setColor(int color)
    {
        this.color =color;
    }

    public void Reset()
    {
        this.color =0;
        this.SorE = false;
        this.startClicked = false;
    }

}
