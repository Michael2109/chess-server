package com.app.chessserver.chess;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BitboardUtils {

    public static long createRowClear(final long row) {
        return ~(0b11111111L << (row * 8));
    }

    public static long createRowMask(final long row) {
        return 0b11111111L << (row * 8);
    }

    public static long createColumnClear(final long column) {
        return ~ (0b0000000100000001000000010000000100000001000000010000000100000001L << column);
    }

    public static long createColumnMask(final long column) {
        return ~createColumnClear(column);
    }

    public static void printBitboard(final long bitboard) {
        final String binary = String.format("%64s", Long.toBinaryString(bitboard)).replace(' ', '0');

        final List<Character> chars = binary.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

        final List<List<Character>> rows = getBatches(chars, 8);

        rows.stream().forEach(row -> {
            final StringBuilder sb = new StringBuilder();

            Collections.reverse(row);
            row.forEach(character -> sb.append(character));

            System.out.println(sb);
        });

        printBitboardBinary(bitboard);
    }


    public static void printBitboardBinary(final long bitboard) {
        final String binary = String.format("0b%64sL", Long.toBinaryString(bitboard)).replace(' ', '0');

        System.out.println(binary);
    }


    public static long getValidMoves(final Board board,final long position) {

        final long moves;
        if((board.whiteKings & position) > 0){
            moves = computeKingMoves(position, board.whitePieces);
        } else if((board.whiteKings & position) > 0) {
            moves = computeKingMoves(position, board.blackPieces);
        }else {
            moves = 0;
        }

        return moves;
    }

    public static long computeKingMoves(final long kingPosition, final long ownPieces){
        // 1 2 3
        // 8 K 4
        // 7 6 5

        printBitboard(kingPosition);
        final long kingClipColumnH = kingPosition & createColumnClear(7);
        final long kingClipColumnA = kingPosition & createColumnClear(0);


        final long spot1 = kingClipColumnA << 7;
        final long spot2 = kingPosition << 8;
        final long spot3 = kingClipColumnH << 9;
        final long spot4 = kingClipColumnH << 1;

        final long spot5 = kingClipColumnH >> 7;
        final long spot6 = kingPosition >> 8;
        final long spot7 = kingClipColumnA >> 9;
        final long spot8 = kingClipColumnA >> 1;

        final long kingMoves = spot1 | spot2 | spot3 | spot4 | spot5 | spot6 | spot7 | spot8;

       printBitboard(kingMoves);

        return kingMoves & (~ownPieces);
    }


    private static <T> List<List<T>> getBatches(final List<T> collection, final int batchSize) {
        return IntStream.iterate(0, i -> i < collection.size(), i -> i + batchSize)
                .mapToObj(i -> collection.subList(i, Math.min(i + batchSize, collection.size())))
                .collect(Collectors.toList());
    }
}
