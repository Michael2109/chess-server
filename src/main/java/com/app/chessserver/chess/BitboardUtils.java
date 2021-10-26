package com.app.chessserver.chess;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BitboardUtils {

    // TODO Change to be multiple constants
    public static long createRowClear(final long row) {
        return ~(0b11111111L << (row * 8));
    }

    public static long createRowMask(final long row) {
        return 0b11111111L << (row * 8);
    }

    public static long createColumnClear(final long column) {
        return ~(0b0000000100000001000000010000000100000001000000010000000100000001L << column);
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


    public static long getValidMoves(final Board board, final long position) {

        final long moves;
        if ((board.whiteKings & position) > 0) {
            moves = computeKingMoves(position, board.whitePieces);
        } else if ((board.blackKings & position) > 0) {
            moves = computeKingMoves(position, board.blackPieces);
        } else if ((board.whiteKnights & position) > 0) {
            moves = computeKnightMoves(position, board.whitePieces);
        } else if ((board.blackKnights & position) > 0) {
            moves = computeKnightMoves(position, board.blackPieces);
        } else if ((board.whitePawns & position) > 0) {
            moves = computeWhitePawnMoves(position, board.allPieces, board.blackPieces);
        } else if ((board.blackPawns & position) > 0) {
            moves = 0;
        } else {
            moves = 0;
        }

        return moves;
    }

    public static long computeKingMoves(final long kingPosition, final long ownPieces) {
        // 1 2 3
        // 8 K 4
        // 7 6 5
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

        return kingMoves & (~ownPieces);
    }

    public static long computeKnightMoves(final long knightPosition, final long ownPieces) {
        //   2  3
        // 1      4
        //     K
        // 8      5
        //   7  6

        final long spot_1_clip = BitboardUtils.createColumnClear(0) & BitboardUtils.createColumnClear(1);
        final long spot_2_clip = BitboardUtils.createColumnClear(0);
        final long spot_3_clip = BitboardUtils.createColumnClear(7);
        final long spot_4_clip = BitboardUtils.createColumnClear(7) & BitboardUtils.createColumnClear(6);

        final long spot_5_clip = BitboardUtils.createColumnClear(7) & BitboardUtils.createColumnClear(6);
        final long spot_6_clip = BitboardUtils.createColumnClear(7);
        final long spot_7_clip = BitboardUtils.createColumnClear(0);
        final long spot_8_clip = BitboardUtils.createColumnClear(0) & BitboardUtils.createColumnClear(1);

        /* The clipping masks we just created will be used to ensure that no
		under or overflow positions are computed when calculating the
		possible moves of the knight in certain files. */

        final long spot_1 = (knightPosition & spot_1_clip) << 6;
        final long spot_2 = (knightPosition & spot_2_clip) << 15;
        final long spot_3 = (knightPosition & spot_3_clip) << 17;
        final long spot_4 = (knightPosition & spot_4_clip) << 10;

        final long spot_5 = (knightPosition & spot_5_clip) >> 6;
        final long spot_6 = (knightPosition & spot_6_clip) >> 15;
        final long spot_7 = (knightPosition & spot_7_clip) >> 17;
        final long spot_8 = (knightPosition & spot_8_clip) >> 10;

        final long KnightValid = spot_1 | spot_2 | spot_3 | spot_4 | spot_5 | spot_6 |
                spot_7 | spot_8;

        return KnightValid & ~ownPieces;
    }

    public static long computeWhitePawnMoves(final long pawnPosition, final long allPieces, final long blackPieces) {

        /* check the single space infront of the white pawn */
        final long white_pawn_one_step = (pawnPosition << 8) & ~allPieces;

	/* for all moves that came from rank 2 (home row) and passed the above
		filter, thereby being on rank 3, check and see if I can move forward
		one more */
        final long white_pawn_two_steps =
                ((white_pawn_one_step & BitboardUtils.createRowMask(2)) << 8) & ~allPieces;

	/* the union of the movements dictate the possible moves forward
		available */
        final long white_pawn_valid_moves = white_pawn_one_step | white_pawn_two_steps;

        /* next we calculate the pawn attacks */

        /* check the left side of the pawn, minding the underflow File A */
        final long white_pawn_left_attack = (pawnPosition & BitboardUtils.createColumnClear(0)) << 7;

        /* then check the right side of the pawn, minding the overflow File H */
        final long white_pawn_right_attack = (pawnPosition & BitboardUtils.createColumnClear(7)) << 9;

	    /* the union of the left and right attacks together make up all the
		possible attacks */
        final long white_pawn_attacks = white_pawn_left_attack | white_pawn_right_attack;

        /* Calculate where I can _actually_ attack something */
        final long white_pawn_valid_attacks = white_pawn_attacks & blackPieces;

	/* then we combine the two situations in which a white pawn can legally
		attack/move. */
        final long WhitePawnValid = white_pawn_valid_moves | white_pawn_valid_attacks;

        printBitboard(WhitePawnValid);
        return WhitePawnValid;

    }


    public static long computeBlackPawnMoves(final long pawnPosition, final long allPieces, final long whitePieces) {

        /* check the single space infront of the black pawn */
        final long black_pawn_one_step = (pawnPosition >> 8) & ~allPieces;

	/* for all moves that came from rank 2 (home row) and passed the above
		filter, thereby being on rank 3, check and see if I can move forward
		one more */
        final long black_pawn_two_steps =
                ((black_pawn_one_step & BitboardUtils.createRowMask(2)) >> 8) & ~allPieces;

	/* the union of the movements dictate the possible moves forward
		available */
        final long black_pawn_valid_moves = black_pawn_one_step | black_pawn_two_steps;

        /* next we calculate the pawn attacks */

        /* check the left side of the pawn, minding the underflow File A */
        final long black_pawn_left_attack = (pawnPosition & BitboardUtils.createColumnClear(0)) >> 7;

        /* then check the right side of the pawn, minding the overflow File H */
        final long black_pawn_right_attack = (pawnPosition & BitboardUtils.createColumnClear(7)) >> 9;

	/* the union of the left and right attacks together make up all the
		possible attacks */
        final long black_pawn_attacks = black_pawn_left_attack | black_pawn_right_attack;

        /* Calculate where I can _actually_ attack something */
        final long black_pawn_valid_attacks = black_pawn_attacks & whitePieces;

	/* then we combine the two situations in which a black pawn can legally
		attack/move. */
        final long blackPawnValid = black_pawn_valid_moves | black_pawn_valid_attacks;

        return blackPawnValid;

    }


    private static <T> List<List<T>> getBatches(final List<T> collection, final int batchSize) {
        return IntStream.iterate(0, i -> i < collection.size(), i -> i + batchSize)
                .mapToObj(i -> collection.subList(i, Math.min(i + batchSize, collection.size())))
                .collect(Collectors.toList());
    }
}
