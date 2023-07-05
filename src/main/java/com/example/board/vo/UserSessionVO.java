package com.example.board.vo;

import java.time.LocalDateTime;

public class UserSessionVO {
    private int id;
    private long userId;
    private String sessionId;
    private LocalDateTime expirationTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private int id;
        private long userId;
        private String sessionId;
        private LocalDateTime expirationTime;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder expirationTime(LocalDateTime expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public UserSessionVO build() {
            UserSessionVO userSessionVO = new UserSessionVO();
            userSessionVO.id = this.id;
            userSessionVO.sessionId = this.sessionId;
            userSessionVO.userId = this.userId;
            userSessionVO.expirationTime = this.expirationTime;
            return userSessionVO;
        }
    }
}
