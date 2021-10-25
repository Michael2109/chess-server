package com.app.chessserver.chess;

import com.app.chessserver.model.actions.MovePiece;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

    private long whiteKings;
    private long whiteQueens;
    private long whiteRooks;
    private long whiteBishops;
    private long whiteKnights;
    private long whitePawns;
    private long whitePieces;

    private long blackKings;
    private long blackQueens;
    private long blackRooks;
    private long blackBishops;
    private long blackKnights;
    private long blackPawns;
    private long blackPieces;

    private long allPieces;

    public Board() {
        resetBoard();
    }

    public long getBitboard(){
        return allPieces;
    }

    private void resetBoard() {
        whiteKings = 1L << Positions.E1;
        whiteQueens = 1L << Positions.D1;
        whiteRooks = 1L << Positions.A1 | 1L << Positions.H1;
        whiteBishops = 1L << Positions.C1 | 1L << Positions.F1;
        whiteKnights = 1L << Positions.B1 | 1L << Positions.G1;
        whitePawns = 255L << 8;

        blackKings = 1L << Positions.E8;
        blackQueens = 1L << Positions.D8;
        blackRooks = 1L << Positions.A8 | 1L << Positions.H8;
        blackBishops = 1L << Positions.C8 | 1L << Positions.F8;
        blackKnights = 1L << Positions.B8 | 1L << Positions.G8;
        blackPawns = 255L << 48;

        updatePieces();
    }

    private void updatePieces() {
        whitePieces = whiteKings | whiteQueens | whiteRooks | whiteBishops | whiteKnights | whitePawns;
        blackPieces = blackKings | blackQueens | blackRooks | blackBishops | blackKnights | blackPawns;
        allPieces = whitePieces | blackPieces;
    }

    public void movePiece(final MovePiece movePiece) throws Exception {
        System.out.println("Moving the piece: " + movePiece);

        BitboardUtils.printBitboard(allPieces);
    }

    public Collection<Position> getValidMoves(final Position position) {
        return Stream.of(new Position(1, 5)).collect(Collectors.toList());
    }

}
