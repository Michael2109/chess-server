package com.app.chessserver.controller;

import com.app.chessserver.chess.Board;
import com.app.chessserver.chess.Position;
import com.app.chessserver.model.actions.MovePiece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;

@Controller
public class ChessController {

    private final Board chessBoard = new Board();

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/movePiece")
    @SendTo("/topic/movePiece")
    @CrossOrigin(origins = "http://localhost:3000")
    public Board movePiece(final MovePiece movePiece) throws Exception {

        chessBoard.movePiece(movePiece);
        this.template.convertAndSend("/topic/movePiece", chessBoard);

        return chessBoard;
    }

    @MessageMapping("/validMoves")
    @SendTo("/topic/validMoves")
    @CrossOrigin(origins = "http://localhost:3000")
    public Collection<Position> getValidMoves(final Position position) throws Exception {
        return chessBoard.getValidMoves(position);
    }
}
