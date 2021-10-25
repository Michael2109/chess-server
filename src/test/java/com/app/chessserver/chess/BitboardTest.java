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

        Assertions.assertEquals(0b1111111111111111000000000000000000000000000000001111111100000000L, bitboard & BitboardUtils.createRowClear(0));

        Assertions.assertEquals(0b1111111111111111000000000000000000000000000000000000000011111111L, bitboard & BitboardUtils.createRowClear(1));

        Assertions.assertEquals(0b0000000011111111000000000000000000000000000000001111111111111111L, bitboard & BitboardUtils.createRowClear(7));
    }

    @Test
    public void testMaskRank() {
        final long bitboard = new Board().getBitboard();

        Assertions.assertEquals(0b0000000000000000000000000000000000000000000000000000000011111111L, bitboard & BitboardUtils.createRowMask(0));
        Assertions.assertEquals(0b0000000000000000000000000000000000000000000000001111111100000000L, bitboard & BitboardUtils.createRowMask(1));
        Assertions.assertEquals(0b1111111100000000000000000000000000000000000000000000000000000000L, bitboard & BitboardUtils.createRowMask(7));
    }

    @Test
    public void testClearFile() {
        final long bitboard = new Board().getBitboard();

        Assertions.assertEquals(0b1111111011111110000000000000000000000000000000001111111011111110L, bitboard & BitboardUtils.createColumnClear(0));
        Assertions.assertEquals(0b1111110111111101000000000000000000000000000000001111110111111101L, bitboard & BitboardUtils.createColumnClear(1));
        Assertions.assertEquals(0b0111111101111111000000000000000000000000000000000111111101111111L, bitboard & BitboardUtils.createColumnClear(7));
    }

    @Test
    public void testMaskFile() {
        final long bitboard = new Board().getBitboard();

        Assertions.assertEquals(0b0000000100000001000000000000000000000000000000000000000100000001L, bitboard & BitboardUtils.createColumnMask(0));
        Assertions.assertEquals(0b0000001000000010000000000000000000000000000000000000001000000010L, bitboard & BitboardUtils.createColumnMask(1));
        Assertions.assertEquals(0b1000000010000000000000000000000000000000000000001000000010000000L, bitboard & BitboardUtils.createColumnMask(7));
    }

    @Test
    public void testKingMoves() {

        Assertions.assertEquals(0b0000000000000000000000000000000000011100000101000001110000000000L, BitboardUtils.computeKingMoves(1L << Positions.D3, 0));
        Assertions.assertEquals(0b0000000000000000000000000000000000000011000000100000001100000000L, BitboardUtils.computeKingMoves(1L << Positions.A3, 0));
        Assertions.assertEquals(0b0000000000000000000000000000000011000000010000001100000000000000L, BitboardUtils.computeKingMoves(1L << Positions.H3, 0));
        Assertions.assertEquals(0b0000101000001110000000000000000000000000000000000000000000000000L, BitboardUtils.computeKingMoves(1L << Positions.C8, 0));
        Assertions.assertEquals(0b0000000000000000000000000000000000000000000000000000111000001010L, BitboardUtils.computeKingMoves(1L << Positions.C1, 0));

        // Add tests to check wont allow moves on own pieces
    }
}
