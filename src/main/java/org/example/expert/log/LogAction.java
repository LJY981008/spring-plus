package org.example.expert.log;

public enum LogAction {
    CREATE("매니저 생성");

    private final String action;

    LogAction(String action) {
        this.action = action;
    }
}
