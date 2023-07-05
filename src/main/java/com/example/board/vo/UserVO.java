package com.example.board.vo;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserVO {
    private Long id;
    private String accountId;
    private String password;
    private String nickname;
    private String email;
    private LocalDateTime writtenTime;
    private LocalDateTime updateTime;
    private LocalDateTime delReqTime;

    public UserVO(){

    }

    public UserVO(Long id, String accountId, String password, String nickname, String email, LocalDateTime writtenTime, LocalDateTime updateTime, LocalDateTime delReqTime) {
        this.id = id;
        this.accountId = accountId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.writtenTime = writtenTime;
        this.updateTime = updateTime;
        this.delReqTime = delReqTime;
    }


    public Long getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword(){
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getWrittenTime() {
        return writtenTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public LocalDateTime getDelReqTime() {
        return delReqTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return obj instanceof UserVO &&
                Objects.equals(this.id, ((UserVO) obj).id);
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", writtenTime=" + writtenTime +
                ", updateTime=" + updateTime +
                ", delReqTime=" + delReqTime +
                '}';
    }

    public static UserVOBuilder builder(){
        return new UserVOBuilder();
    }

    public static final class UserVOBuilder {
        private Long id;
        private String accountId;
        private String password;
        private String nickname;
        private String email;
        private LocalDateTime writtenTime;
        private LocalDateTime updateTime;
        private LocalDateTime delReqTime;

        private UserVOBuilder() {
        }

        public static UserVOBuilder anUserVO() {
            return new UserVOBuilder();
        }

        public UserVOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserVOBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public UserVOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserVOBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserVOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserVOBuilder writtenTime(LocalDateTime writtenTime) {
            this.writtenTime = writtenTime;
            return this;
        }

        public UserVOBuilder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public UserVOBuilder delReqTime(LocalDateTime delReqTime) {
            this.delReqTime = delReqTime;
            return this;
        }

        public UserVO build() {
            return new UserVO(id, accountId, password, nickname, email, writtenTime, updateTime, delReqTime);
        }
    }
}
