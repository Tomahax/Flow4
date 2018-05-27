package com.example.tomer.flow4;

public class Board {

    private int misparShurot = 6;
    private int misparAmudot = 8;
    private Spot boardMatrix[][];
    public static final String BoardFileName = "info.txt";

    public int getMisparShurot() {
        return misparShurot;
    }

    public int getMisparAmudot() {
        return misparAmudot;
    }

    public Spot[][] getBoardMatrix() {
        return boardMatrix;
    }

    public static String getBoardFileName() {
        return BoardFileName;
    }

    public Board(int row, int col) {

        this.misparShurot = row;
        this.misparAmudot = col;
        // פעולה לאתחול לוח
        this.boardMatrix = new Spot[misparAmudot][misparAmudot];
        // אתחול הלוח
        for (int i = 0; i < this.boardMatrix.length; i++) {
            for (int k = 0; k < this.boardMatrix[i].length; k++) {
               // this.boardMatrix[i][k] = new Spot("red", false, i, k);
            }
        }
    }
}
