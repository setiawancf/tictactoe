package com.fwd.tictactoe.service;

import com.fwd.tictactoe.common.Response;
import com.fwd.tictactoe.common.enums.ResponsCodeTypeEnum;
import com.fwd.tictactoe.response.ResponseUtil;
import com.fwd.tictactoe.vo.PositionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Setiawan Candrafu
 * @date 3/22/2023
 */
@Slf4j
@Service
public class LogicService {
    public Response checkWin(List<List<String>> currentBoard, Integer boardSize) {
        //horizontal
        for (int i = 0; i < boardSize; i++) {
            String horizontalRow = StringUtils.join(currentBoard.get(i), "");
            System.out.println("Horizontal Row : " + i + " : " + horizontalRow);
            String result = checkRow(boardSize, horizontalRow);
            if (!result.isEmpty()) {
                return ResponseUtil.getSuccessResponse(result, ResponsCodeTypeEnum.SUCCESS.getCode());
            }
        }
        //vertical
        for (int i = 0; i < boardSize; i++) {
            String verticalRow = buildVerticalRow(currentBoard, boardSize, i);
            System.out.println("Vertical Row : " + i + " : " + verticalRow);
            String result = checkRow(boardSize, verticalRow);
            if (!result.isEmpty()) {
                return ResponseUtil.getSuccessResponse(result, ResponsCodeTypeEnum.SUCCESS.getCode());
            }
        }
        //diagonal left
        String diagonalLeftRow = buildDiagonal(currentBoard, boardSize, true);
        System.out.println("Diagonal Left Row : " + diagonalLeftRow);
        String resultLeft = checkRow(boardSize, diagonalLeftRow);
        if (!resultLeft.isEmpty()) {
            return ResponseUtil.getSuccessResponse(resultLeft, ResponsCodeTypeEnum.SUCCESS.getCode());
        }
        //diagonal right
        String diagonalRightRow = buildDiagonal(currentBoard, boardSize, false);
        System.out.println("Diagonal Left Row : " + diagonalRightRow);
        String resultRight = checkRow(boardSize, diagonalRightRow);
        if (!resultRight.isEmpty()) {
            return ResponseUtil.getSuccessResponse(resultRight, ResponsCodeTypeEnum.SUCCESS.getCode());
        }
        return ResponseUtil.getResponse(ResponsCodeTypeEnum.SUCCESS.getCode());
    }

    private String checkRow(Integer boardsize, String row) {
        if (oWins(boardsize, row)) {
            return "O Wins";
        } else if (xWins(boardsize, row)) {
            return "X Wins";
        }
        return "";
    }

    private boolean oWins(Integer boardsize, String row) {
        String oString = StringUtils.repeat('O', boardsize);
        if (row.equals(oString)) {
            return true;
        }
        return false;
    }

    private boolean xWins(Integer boardsize, String row) {
        String xString = StringUtils.repeat('X', boardsize);
        if (row.equals(xString)) {
            return true;
        }
        return false;
    }

    private String buildVerticalRow(List<List<String>> currentBoard, Integer boardSize, Integer column) {
        String a = "";
        for (int i = 0; i < boardSize; i++) {
            a += currentBoard.get(i).get(column);
        }
        return a;
    }

    private String buildDiagonal(List<List<String>> currentBoard, Integer boardSize, boolean isLeft) {
        String a = "";
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (isLeft) {
                    if (i == j) {
                        a += currentBoard.get(i).get(j);
                    }
                } else {
                    if (i + j == boardSize - 1) {
                        a += currentBoard.get(i).get(j);
                    }
                }
            }
        }
        return a;
    }

    public Response botMove(List<List<String>> currentBoard, Integer boardSize) {
        List<List<String>> nextBoard = currentBoard;

        //check X near wins to block
        PositionVo blockPos = blockWin(currentBoard, boardSize);
        if (blockPos != null) {
            nextBoard.get(blockPos.getI()).set(blockPos.getJ(), "O");
            return ResponseUtil.getSuccessResponse(nextBoard, ResponsCodeTypeEnum.SUCCESS.getCode());
        }

        //check O near wins to win
        PositionVo winPos = getWin(currentBoard, boardSize);
        if (winPos != null) {
            nextBoard.get(winPos.getI()).set(winPos.getJ(), "O");
            return ResponseUtil.getSuccessResponse(nextBoard, ResponsCodeTypeEnum.SUCCESS.getCode());
        }

        //place O all over the board
        PositionVo randomPos = getRandom(currentBoard, boardSize);
        nextBoard.get(randomPos.getI()).set(randomPos.getJ(), "O");
        return ResponseUtil.getSuccessResponse(nextBoard, ResponsCodeTypeEnum.SUCCESS.getCode());
    }

    public PositionVo blockWin(List<List<String>> currentBoard, Integer boardSize) {
        //horizontal
        for (int i = 0; i < boardSize; i++) {
            String horizontalRow = StringUtils.join(currentBoard.get(i), "");
            System.out.println("Horizontal Row : " + i + " : " + horizontalRow);
            if (nearWins(boardSize, horizontalRow, "X")) {
                //block win
                PositionVo positionVo = new PositionVo();
                positionVo.setI(i);
                positionVo.setJ(horizontalRow.indexOf('-'));
            }
        }
        //vertical
        for (int i = 0; i < boardSize; i++) {
            String verticalRow = buildVerticalRow(currentBoard, boardSize, i);
            System.out.println("Vertical Row : " + i + " : " + verticalRow);
            if (nearWins(boardSize, verticalRow, "X")) {
                //block win
                PositionVo positionVo = new PositionVo();
                positionVo.setI(verticalRow.indexOf('-'));
                positionVo.setJ(i);
            }
        }
        //diagonal left
        String diagonalLeftRow = buildDiagonal(currentBoard, boardSize, true);
        System.out.println("Diagonal Left Row : " + diagonalLeftRow);
        if (nearWins(boardSize, diagonalLeftRow, "X")) {
            //block win
            PositionVo positionVo = new PositionVo();
            positionVo.setI(diagonalLeftRow.indexOf('-'));
            positionVo.setJ(diagonalLeftRow.indexOf('-'));
        }
        //diagonal right
        String diagonalRightRow = buildDiagonal(currentBoard, boardSize, false);
        System.out.println("Diagonal Left Row : " + diagonalRightRow);
        if (nearWins(boardSize, diagonalRightRow, "X")) {
            //block win
            PositionVo positionVo = new PositionVo();
            positionVo.setI(diagonalLeftRow.indexOf('-'));
            positionVo.setJ(boardSize - 1 - diagonalLeftRow.indexOf('-'));
        }
        return null;
    }

    public PositionVo getWin(List<List<String>> currentBoard, Integer boardSize) {
        //horizontal
        for (int i = 0; i < boardSize; i++) {
            String horizontalRow = StringUtils.join(currentBoard.get(i), "");
            System.out.println("Horizontal Row : " + i + " : " + horizontalRow);
            if (nearWins(boardSize, horizontalRow, "O")) {
                //block win
                PositionVo positionVo = new PositionVo();
                positionVo.setI(i);
                positionVo.setJ(horizontalRow.indexOf('-'));
            }
        }
        //vertical
        for (int i = 0; i < boardSize; i++) {
            String verticalRow = buildVerticalRow(currentBoard, boardSize, i);
            System.out.println("Vertical Row : " + i + " : " + verticalRow);
            if (nearWins(boardSize, verticalRow, "O")) {
                //block win
                PositionVo positionVo = new PositionVo();
                positionVo.setI(verticalRow.indexOf('-'));
                positionVo.setJ(i);
            }
        }
        //diagonal left
        String diagonalLeftRow = buildDiagonal(currentBoard, boardSize, true);
        System.out.println("Diagonal Left Row : " + diagonalLeftRow);
        if (nearWins(boardSize, diagonalLeftRow, "O")) {
            //block win
            PositionVo positionVo = new PositionVo();
            positionVo.setI(diagonalLeftRow.indexOf('-'));
            positionVo.setJ(diagonalLeftRow.indexOf('-'));
        }
        //diagonal right
        String diagonalRightRow = buildDiagonal(currentBoard, boardSize, false);
        System.out.println("Diagonal Left Row : " + diagonalRightRow);
        if (nearWins(boardSize, diagonalRightRow, "O")) {
            //block win
            PositionVo positionVo = new PositionVo();
            positionVo.setI(diagonalLeftRow.indexOf('-'));
            positionVo.setJ(boardSize - 1 - diagonalLeftRow.indexOf('-'));
        }
        return null;
    }

    public PositionVo getRandom(List<List<String>> currentBoard, Integer boardSize) {
        int i = (int) (Math.random() * boardSize);
        int j = (int) (Math.random() * boardSize);
        if (!currentBoard.get(i).get(j).equals("-")) {
            return getRandom(currentBoard, boardSize);
        }
        PositionVo positionVo = new PositionVo();
        positionVo.setI(i);
        positionVo.setJ(j);
        return positionVo;
    }

    private boolean nearWins(Integer boardsize, String row, String check) {
        if (onlyOneRemain(row)) {
            String oString = StringUtils.repeat(check, boardsize);
            String nearWin = row.replace("-", check);
            if (nearWin.equals(oString)) {
                return true;
            }
        }
        return false;
    }

    private boolean onlyOneRemain(String row) {
        int firstIndex = row.indexOf('-');
        int lastIndex = row.lastIndexOf('-');

        if (firstIndex == lastIndex && firstIndex >= 0) {
            return true;
        }

        return false;
    }


}
