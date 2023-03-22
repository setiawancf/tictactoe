package com.fwd.tictactoe.service;

import com.fwd.tictactoe.common.Response;
import com.fwd.tictactoe.common.enums.StatusEnums;
import com.fwd.tictactoe.model.History;
import com.fwd.tictactoe.repository.HistoryRepository;
import com.fwd.tictactoe.response.ResponseUtil;
import com.fwd.tictactoe.vo.CompleteVo;
import com.fwd.tictactoe.vo.HistoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Setiawan Candrafu
 * @date 3/22/2023
 */
@Slf4j
@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public Response completeGame(CompleteVo completeVo) {
        History history = new History();
        history.setUserName(completeVo.getUserName());
        history.setStatus(StatusEnums.getById(completeVo.getStatus()).getMessage());
        history.setCompleteDate(new Date());
        historyRepository.save(history);
        return ResponseUtil.getSuccessResponse(history);
    }

    public Response history(String username) {
        List<Map<String, Object>> result = historyRepository.getHistory(username);
        if (result.isEmpty()) {
            return ResponseUtil.getSuccessResponse("User not Found");
        }
        HistoryVo vo = new HistoryVo();
        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> statusMap = (Map<String, Object>) result.get(i);
            if (statusMap.get("status").equals(StatusEnums.WIN.getMessage())) {
                vo.setWin(((BigInteger) statusMap.get("total")).intValue());
            }
            if (statusMap.get("status").equals(StatusEnums.LOSE.getMessage())) {
                vo.setLose(((BigInteger) statusMap.get("total")).intValue());
            }
            if (statusMap.get("status").equals(StatusEnums.DRAW.getMessage())) {
                vo.setDraw(((BigInteger) statusMap.get("total")).intValue());
            }
        }
        return ResponseUtil.getSuccessResponse(vo);
    }
}
