package com.fwd.tictactoe.constant;

public interface Urls {
    String ROOT = "/tictactoe/api";
    String V1 = "/v1";

    interface Game {
        public static final String MODULE = "/game";
        public static final String V1_GAME_CHECK_WIN= V1 + "/check-win";
        public static final String V1_GAME_BOT_MOVE= V1 + "/bot-move";
        public static final String V1_GAME_COMPLETE= V1 + "/complete";
    }

    interface User {
        public static final String MODULE = "/user";
        public static final String V1_USER_HISTORY= V1 + "/history";
    }
}
