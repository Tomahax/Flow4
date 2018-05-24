package com.example.tomer.flow4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by tomer on 24/05/2018.
 */

public class GameManager {
//    private Board luach;
//    GameUiInterface gameUI;
//    public static final String gameFileName = "Game.txt";
//
//    public GameManager(GameUiInterface _gameUI, Player[] _players) {
//        this.gameUI = _gameUI;
//        this.players = _players;
//
//        this.luach = new Board();
//        this.currentPlayer = 1;
//    }
//
//    public void restart() {
//        this.luach.reStart();
//        this.currentPlayer = 1;
//        this.gameUI.drawBoard(this.luach);
//        for (int i = 0; i < Board.MisparShurot; i++) {
//            for (int k = 0; k < Board.misparAmudot; k++)
//                this.gameUI.setColumnClickable(i, this.luach.getClickable(i, k));
//        }
//        this.gameUI.markCurrentPlayer(this.currentPlayer);
//        this.gameUI.apesLuach();
//    }
//
//    public boolean check4Victory(int p, int[] arrVic) {
//        return luach.check4Victory(p, arrVic);
//    }
//
//    public void placeDisk(int rowNum, int colNum) {
//        if (luach.chckPlay(rowNum, colNum, this.currentPlayer)) {
//            // called from GameUiActivity
//            // מעדכן את הלוח
//            luach.play(rowNum, colNum, this.currentPlayer);
//            //  מצייר את הלוח לאחר הלחיצה
//            this.gameUI.drawBoard(this.luach);
//            // מעדכן אפשרות לחיצה על העמודה
//            Boolean matzav = this.luach.getClickable(rowNum, colNum);
//            this.gameUI.setColumnClickable(colNum, matzav);
//            // משנה שחקן
//            if (this.currentPlayer == 1)
//                this.currentPlayer = -1;
//            else
//                this.currentPlayer = 1;
//            // מראה שחקן חדש
//            this.gameUI.markCurrentPlayer(this.currentPlayer);
//        }
//
//
//    }
//
//    // loading
//
//    public void loadBoard(FileInputStream fis) {
//        // טעינת הלוח
//        this.luach.load(fis);
//
//    }
//
//    public void load(FileInputStream fis) {
//        try {
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader reader = new BufferedReader(isr);
//            String strLine = reader.readLine();
//            String data[] = strLine.split(";");
//            this.players[0].setPlayerName(data[0]);
//            this.players[1].setPlayerName(data[1]);
//            this.currentPlayer = Integer.parseInt(data[2]);
//
//            reader.close();
//            isr.close();
//            fis.close();
//
//        } catch (Exception e) {
//        }
//    }
//
//
//    // saving
//
//    public void saveBoard(FileOutputStream fos) {
//        this.luach.save(fos);
//    }
//
//    public void save(FileOutputStream fos) {
//        try {
//            OutputStreamWriter osw = new OutputStreamWriter(fos);
//            BufferedWriter writer = new BufferedWriter(osw);
//            writer.append(this.players[0].toString() + ";" + this.players[1].toString() + ";" + this.currentPlayer);
//            writer.close();
//            osw.close();
//            fos.close();
//        } catch (Exception e) {
//
//        }
//
//    }
}