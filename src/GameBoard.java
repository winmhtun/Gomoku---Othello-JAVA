/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ljukang
 */
public class GameBoard {

    private SEEDS[][] xBoard = new SEEDS[8][8];
    private Player player1 = new Player(SEEDS.BLACK);
    private Player player2 = new Player(SEEDS.WHITE);
    private Player currentPlayer = player1;

    public GameBoard() {
        NewGame();
    }

    private void NewGame() {
        // create a new gameBoard  , all positions removed , score restart ;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                xBoard[i][j] = SEEDS.EMPTY;

            }
        }

        placeDisk(3, 3);
        nextTurn();
        placeDisk(3, 4);
        nextTurn();
        placeDisk(4, 4);
        nextTurn();
        placeDisk(4, 3);
        nextTurn();
    }

    public void placeDisk(int row, int col) {

        if (currentPlayer.getColor() == SEEDS.BLACK) {
            xBoard[row][col] = SEEDS.BLACK;
        } else if (currentPlayer.getColor() == SEEDS.WHITE) {
            xBoard[row][col] = SEEDS.WHITE;
        }
    }

    public SEEDS[][] getBoard() {
        return xBoard;
    }

    public void nextTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

//    public boolean chkSlot(int currentRow, int currentCol) {
//        boolean flippable = false;
//
//        for (int chkRow = -1; chkRow < 2; chkRow++) {
//            for (int chkCol = -1; chkCol < 2; chkCol++) {
//                // Explore all straight and diagonal directions from the piece put down.
//                // Move along that direction - if there is at least one piece of the opposite color next
//                // in line, and the pieces of the opposite color are followed by a piece of the same
//                // color, do a flip.
//                if (chkRow == 0 && chkCol == 0) {
//                    continue;
//                }
//
//                int xRow = currentRow + chkRow;
//                int xCol = currentCol + chkCol;
//
//                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
//                    if ((xBoard[xRow][xCol]) == (this.currentPlayer.getColor() == SEEDS.BLACK ? SEEDS.WHITE : SEEDS.BLACK)) {
//                        for (int range = 0; range < 8; range++) {
//                            int nRow = currentRow + range * chkRow;
//                            int nCol = currentCol + range * chkCol;
//                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
//                                continue;
//                            }
//
//                            if (xBoard[nRow][nCol] == this.currentPlayer.getColor()) {
//                                flippable = true;
//                                break;
//                            }
//
//                        }
//
//                    }
//                }
//
//
//            }
//        }
//        return flippable;
//    }

    public boolean doFlip(int currentRow, int currentCol, boolean flippable) {
        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) { 
                    continue;
                }

                int xRow = currentRow + chkRow;
                int xCol = currentCol + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((xBoard[xRow][xCol]) == (this.currentPlayer.getColor() == SEEDS.BLACK ? SEEDS.WHITE : SEEDS.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = currentRow + range * chkRow;
                            int nCol = currentCol + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (xBoard[nRow][nCol] == this.currentPlayer.getColor()) {
                                if (flippable) {
                                    for (int flipDistance = 1; flipDistance < range; flipDistance++) {
                                        int finalRow = currentRow + flipDistance * chkRow;
                                        int finalCol = currentCol + flipDistance * chkCol;
                                       
                                        xBoard[finalRow][finalCol] = this.currentPlayer.getColor();
                                    }
                                }
                                isValid = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return isValid;
    }

    public int chkWinner() {
        int slotsLeft = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (xBoard[i][j] == SEEDS.EMPTY) {
                    slotsLeft++;
                }


            }
        }
        return slotsLeft;
    }
}
