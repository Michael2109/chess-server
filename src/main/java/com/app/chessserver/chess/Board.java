package com.app.chessserver.chess;

import com.app.chessserver.model.actions.MovePiece;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final Piece[][] pieces = new Piece[8][8];

    public Board(){
        resetBoard();
    }

    public Piece[][] getPieces() {
        return pieces.clone();
    }

    private void resetBoard(){
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);

        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        pieces[0][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);

    }

    public void movePiece(final MovePiece movePiece) throws Exception{
        System.out.println("Moving the piece: " + movePiece);
    }

    public Collection<Position> getValidMoves(final Position position){
        return Stream.of(new Position(1,5)).collect(Collectors.toList());
    }
}
