package com.fwd.tictactoe.controller;

import com.fwd.tictactoe.common.Response;
import com.fwd.tictactoe.constant.Urls;
import com.fwd.tictactoe.service.HistoryService;
import com.fwd.tictactoe.vo.CompleteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Setiawan Candrafu
 * @date 3/22/2023
 */
@Slf4j
@RestController
@RequestMapping(value = Urls.User.MODULE)
public class UserController {
    @Autowired
    private HistoryService historyService;

    @GetMapping(Urls.User.V1_USER_HISTORY)
    public Response<Map<String, Object>> completeGame(String username) {
        return historyService.history(username);
    }
}
