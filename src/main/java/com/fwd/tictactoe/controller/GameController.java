package com.fwd.tictactoe.controller;

import com.fwd.tictactoe.common.Response;
import com.fwd.tictactoe.constant.Urls;
import com.fwd.tictactoe.service.HistoryService;
import com.fwd.tictactoe.service.LogicService;
import com.fwd.tictactoe.vo.CompleteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Setiawan Candrafu
 * @date 3/22/2023
 */
@Slf4j
@RestController
@RequestMapping(value = Urls.Game.MODULE)
public class GameController {

    @Autowired
    private LogicService logicService;
    @Autowired
    private HistoryService historyService;

    @PostMapping(Urls.Game.V1_GAME_CHECK_WIN)
    public Response<Map<String, Object>> checkWin(@RequestBody List<List<String>> currentBoard,
                                                  @RequestParam Integer boardSize) {
        return logicService.checkWin(currentBoard, boardSize);
    }

    @PostMapping(Urls.Game.V1_GAME_BOT_MOVE)
    public Response<Map<String, Object>> botMove(@RequestBody List<List<String>> currentBoard,
                                                 @RequestParam Integer boardSize) {
        return logicService.botMove(currentBoard, boardSize);
    }

    @PostMapping(Urls.Game.V1_GAME_COMPLETE)
    public Response<Map<String, Object>> completeGame(@RequestBody CompleteVo completeVo) {
        return historyService.completeGame(completeVo);
    }
}
