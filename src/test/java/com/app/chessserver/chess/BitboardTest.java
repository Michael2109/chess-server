package com.app.chessserver.chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitboardTest {

    @Test
    public void testInitialBoardValues() {
        final long bitboard = new Board().getBitboard();

        Assertions.assertEquals(0b1111111111111111000000000000000000000000000000001111111111111111L, bitboard);
    }

    @Test
    public void testClearRank() {
        final long bitboard = new Board().getBitboard();

        Assertions.assertEquals(0b1111111111111111000000000000000000000000000000001111111100000000L, bitboard & BitboardUtils.createRankClear(0));

        Assertions.assertEquals(0b1111111111111111000000000000000000000000000000000000000011111111L, bitboard & BitboardUtils.createRankClear(1));

        Assertions.assertEquals(0b0000000011111111000000000000000000000000000000001111111111111111L, bitboard & BitboardUtils.createRankClear(7));
    }

    @Test
    public void testMaskRank() {
        final long bitboard = new Board().getBitboard();

        Assertions.assertEquals(0b0000000000000000000000000000000000000000000000000000000011111111L, bitboard & BitboardUtils.createRankMask(0));

        Assertions.assertEquals(0b0000000000000000000000000000000000000000000000001111111100000000L, bitboard & BitboardUtils.createRankMask(1));

        Assertions.assertEquals(0b1111111100000000000000000000000000000000000000000000000000000000L, bitboard & BitboardUtils.createRankMask(7));
    }
}
