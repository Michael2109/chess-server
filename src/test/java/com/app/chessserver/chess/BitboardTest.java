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

    @Test
    public void testClearFile() {
        final long bitboard = new Board().getBitboard();

        Assertions.assertEquals(0b1111111011111110000000000000000000000000000000001111111011111110L, bitboard & BitboardUtils.createFileClear(0));
        Assertions.assertEquals(0b1111110111111101000000000000000000000000000000001111110111111101L, bitboard & BitboardUtils.createFileClear(1));
        Assertions.assertEquals(0b0111111101111111000000000000000000000000000000000111111101111111L, bitboard & BitboardUtils.createFileClear(7));
    }

    @Test
    public void testMaskFile() {
        final long bitboard = new Board().getBitboard();

       BitboardUtils.printBitboard(bitboard & BitboardUtils.createFileMask(7));
        Assertions.assertEquals(0b0000000100000001000000000000000000000000000000000000000100000001L, bitboard & BitboardUtils.createFileMask(0));
        Assertions.assertEquals(0b0000001000000010000000000000000000000000000000000000001000000010L, bitboard & BitboardUtils.createFileMask(1));
        Assertions.assertEquals(0b1000000010000000000000000000000000000000000000001000000010000000L, bitboard & BitboardUtils.createFileMask(7));
    }
}
