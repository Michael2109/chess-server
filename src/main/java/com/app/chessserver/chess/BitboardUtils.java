package com.app.chessserver.chess;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BitboardUtils {

    public static long createRankClear(final int row) {
        return ~(0b11111111L << (row * 8));
    }

    public static long createRankMask(final int row) {
        return 0b11111111L << (row * 8);
    }

    public static long createFileClear(final int column) {
        return 0b1111111111111111111111111111111111111111111111111111111111111111L << (column * 8);
    }

    public static long createFileMask(final int column) {
        return 0b111111111111111111111111111111111111111111111111111111111L << (column * 8);
    }

    public static void printBitboard(final long bitboard) {
        final String binary = String.format("%64s", Long.toBinaryString(bitboard)).replace(' ', '0');

        final List<Character> chars = binary.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

        final List<List<Character>> rows = getBatches(chars, 8);

        rows.stream().forEach(row -> {
            final StringBuilder sb = new StringBuilder();
            row.forEach(character -> sb.append(character));

            System.out.println(sb);
        });
    }

    private static <T> List<List<T>> getBatches(List<T> collection, int batchSize) {
        return IntStream.iterate(0, i -> i < collection.size(), i -> i + batchSize)
                .mapToObj(i -> collection.subList(i, Math.min(i + batchSize, collection.size())))
                .collect(Collectors.toList());
    }
}
