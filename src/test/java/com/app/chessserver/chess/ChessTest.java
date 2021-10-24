package com.app.chessserver.chess;

import org.junit.jupiter.api.Test;

public class ChessTest {

    @Test
    public void testInitialBoardValues(){
        new Board().printBoard();
    }

}
