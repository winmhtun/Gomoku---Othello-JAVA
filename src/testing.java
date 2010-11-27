/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ljukang
 */

import java.io.*;

public class testing {
    public static void main(String[] args) throws Exception {
        // Initialise game board, made of chars - ' ' is empty, 'b' is a black piece, 'w' a white.
        // Using a 10x10 board, with the outer ring of empties acting as sentinels.
        char[][] board = new char[10][10];
        for (int y = 0; y < 10; y++) { for (int x = 0; x < 10; x++) { board[y][x] = ' '; } }
        // Set up the four pieces in the middle.
        board[4][4] = 'w'; board[4][5] = 'b'; board[5][4] = 'b'; board[5][5] = 'w';
        // Color of the current player.
        char currentColor = 'b';
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
                // Print game board.
                System.out.println(" abcdefgh");
                for (int y = 1; y < 9; y++) {
                        System.out.print(y);
                        for (int x = 1; x < 9; x++) { System.out.print(board[y][x]); }
                        System.out.println();
                }
                // See if there are any legal moves by considering all possible ones.
                boolean legalMovesAvailable = false;
                for (int y = 1; y < 9; y++) { for (int x = 1; x < 9; x++) {
                        legalMovesAvailable = legalMovesAvailable ||
                                        (board[y][x] == ' ' && flip(x, y, board, currentColor, false));
                }}
                if (!legalMovesAvailable) { break; }
                // Print input, indicating which color's turn it is.
                System.out.print(currentColor + ">");
                // Parse input.
                String l = r.readLine();
                if (l.length() < 2) { continue; }
                int x = l.charAt(0) - 'a' + 1;
                int y = l.charAt(1) - '0';
                // Discard malformed input.
                if (x < 1 || x > 8 || y < 1 || y > 8 || board[y][x] != ' ') { continue; }
                // Check if valid move & flip if it is - if not, continue.
                if (!flip(x, y, board, currentColor, true)) { continue; }
                // Put down the piece itself.
                board[y][x] = currentColor;
                // Switch players.
                currentColor = currentColor == 'b' ? 'w' : 'b';
        }
        // Calculate final score: negative is a win for white, positive one for black.
        int score = 0;
        for (int y = 1; y < 9; y++) { for (int x = 1; x < 9; x++) {
                score += board[y][x] == 'b' ? 1 : board[y][x] == 'w' ? -1 : 0;
        }}
        System.out.println(score == 0 ? "d" : score < 0 ? "w" : "b");
    }

    /** Flip pieces, or explore whether putting down a piece would cause any flips. */
    static boolean flip(int pieceX, int pieceY, char[][] board, char playerColor, boolean commitPutDown) {
        boolean causesFlips = false;
        // Explore all straight and diagonal directions from the piece put down.
        for (int dY = -1; dY < 2; dY++) {
            for (int dX = -1; dX < 2; dX++) {
                if (dY == 0 && dX == 0) { 
                    continue;
                }
                // Move along that direction - if there is at least one piece of the opposite color next
                // in line, and the pieces of the opposite color are followed by a piece of the same
                // color, do a flip.
                int distance = 0;
                do {
                        distance++;
                } while (board[pieceY + distance * dY][pieceX + distance * dX] == (playerColor == 'b' ? 'w' : 'b'));
                
                
                if (board[pieceY + distance * dY][pieceX + distance * dX] == playerColor && distance > 1) {
                     causesFlips = true;
                     for (int distance2 = 1; distance2 < distance; distance2++) {
                               if (commitPutDown) {
                                            board[pieceY + distance2 * dY][pieceX + distance2 * dX] = playerColor;
                                }
                        }
                }
        }}
        return causesFlips;
    }
}

