package com.app.chessserver.chess;

import com.app.chessserver.model.actions.MovePiece;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

    private long whiteKings = 0;
    private long whiteQueens = 0;
    private long whiteRooks = 0;
    private long whiteBishops = 0;
    private long whiteKnights = 0;
    private long whitePawns = 0;
    private long whitePieces = 0;

    private long blackKings = 0;
    private long blackQueens = 0;
    private long blackRooks = 0;
    private long blackBishops = 0;
    private long blackKnights = 0;
    private long blackPawns = 0;
    private long blackPieces = 0;

    public Board() {
        resetBoard();
    }

    private static <T> List<List<T>> getBatches(List<T> collection, int batchSize) {
        return IntStream.iterate(0, i -> i < collection.size(), i -> i + batchSize)
                .mapToObj(i -> collection.subList(i, Math.min(i + batchSize, collection.size())))
                .collect(Collectors.toList());
    }

    private void resetBoard() {
        whiteKings = 1L << Positions.E1;
        whiteQueens = 1L << Positions.D1;
        whiteRooks = 1L << Positions.A1 | 1L << Positions.H1;
        whiteBishops = 1L << Positions.C1 | 1L << Positions.F1;
        whiteKnights = 1L << Positions.B1 | 1L << Positions.G1;
        whitePawns = 255L << 8;
        whitePieces = whiteKings | whiteQueens | whiteRooks | whiteBishops | whiteKnights | whitePawns;

        blackKings = 1L << Positions.E8;
        blackQueens = 1L << Positions.D8;
        blackRooks = 1L << Positions.A8 | 1L << Positions.H8;
        blackBishops = 1L << Positions.C8 | 1L << Positions.F8;
        blackKnights = 1L << Positions.B8 | 1L << Positions.G8;
        blackPawns = 255L << 48;
        blackPieces = blackKings | blackQueens | blackRooks | blackBishops | blackKnights | blackPawns;
    }

    public void movePiece(final MovePiece movePiece) throws Exception {
        System.out.println("Moving the piece: " + movePiece);
        printBoard();
    }

    public Collection<Position> getValidMoves(final Position position) {
        return Stream.of(new Position(1, 5)).collect(Collectors.toList());
    }

    public void printBoard() {
        final String binary = String.format("%64s", Long.toBinaryString(whitePieces | blackPieces)).replace(' ', '0');

        final List<Character> chars = binary.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

        final List<List<Character>> rows = getBatches(chars, 8);

        rows.stream().forEach(row -> {
            final StringBuilder sb = new StringBuilder();
            row.forEach(character -> sb.append(character));

            System.out.println(sb);
        });
    }
}
