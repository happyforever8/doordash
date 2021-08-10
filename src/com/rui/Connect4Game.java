package com.rui;

public class Connect4Game {

    private int [][] board;
    private int col;
    private int row;
    public Connect4Game(int row,int col) {
        board = new int[row][col];
        this.col = col;
        this.row = row;
    }

    public void move(int player, int r,int c) {
        board[r][c] = player;
    }

    public int checkWin(int player,int r,int c) {

        //checkout vertically;
        int count = 1;
        for(int i=r+1;i<row;i++) {
            if(board[i][c]!=player) break;
            if(++count==4) return player;
        }
        //check horizontally
        count = 1;
        //check right side
        for(int i= c+1;i<col;i++) {
            if(board[r][i]!=player) break;
            if(++count==4) return player;
        }
        //check left side
        for(int i= c-1;i>=0;i--) {
            if(board[r][i]!=player) break;
            if(++count==4) return player;
        }

        count=1;
        int i = r-1;
        int j = c-1;
        while(i>=0 && j>=0) {
            if(board[i][j]!=player) break;
            i--;
            j--;
            if(++count==4) return player;
        }
        i = r+1;
        j = c+1;
        while(i<row && j<col) {
            if(board[i][j]!=player) break;
            i++;
            j++;
            if(++count==4) return player;
        }
        count=1;
        i = r-1;
        j = c+1;
        while(i>=0 && j<col) {
            if(board[i][j]!=player) break;
            i--;
            j++;
            if(++count==4) return player;
        }
        i = r+1;
        j = c-1;
        while(i<row && j>=0) {
            if(board[i][j]!=player) break;
            i++;
            j--;
            if(++count==4) return player;
        }

        return 0;
    }
}
