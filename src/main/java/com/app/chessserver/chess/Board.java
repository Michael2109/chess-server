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
        whiteRooks = 1L << Positions.A1 & 1L << Positions.H1;
        whiteBishops = 1L << Positions.C1 & 1L << Positions.F1;
        whiteKnights = 1L << Positions.B1 & 1L << Positions.G1;


    }

    public void movePiece(final MovePiece movePiece) throws Exception {
        System.out.println("Moving the piece: " + movePiece);
    }

    public Collection<Position> getValidMoves(final Position position) {
        return Stream.of(new Position(1, 5)).collect(Collectors.toList());
    }

    public void printBoard() {
        final String binary = String.format("%64s", Long.toBinaryString(whiteKings)).replace(' ', '0');

        final List<Character> chars = binary.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

        System.out.println(chars);
        final List<List<Character>> rows = getBatches(chars, 8);
        System.out.println(rows);
        rows.stream().forEach(row -> {
            final StringBuilder sb = new StringBuilder();
            row.forEach(character -> sb.append(character));

            System.out.println(sb);
        });
    }
}
