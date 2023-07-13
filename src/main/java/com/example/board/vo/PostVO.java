package com.example.board.vo;

import java.time.LocalDateTime;
import java.util.Objects;

public class PostVO {
    private Long id;
    private Integer boardId;
    private String title;
    private String content;
    private Long wittenUserId;
    private String writerNickname;
    private LocalDateTime writtenTime;
    private LocalDateTime updateTime;
    private Long views;
    private String nonMemNick;
    private String nonMemPw;

    public PostVO() {
    }

    public PostVO(Long id, Integer boardId, String title, String content, Long wittenUserId, String writerNickname, LocalDateTime writtenTime, LocalDateTime updateTime, Long views, String nonMemNick, String nonMemPw) {
        this.id = id;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.wittenUserId = wittenUserId;
        this.writerNickname = writerNickname;
        this.writtenTime = writtenTime;
        this.updateTime = updateTime;
        this.views = views;
        this.nonMemNick = nonMemNick;
        this.nonMemPw = nonMemPw;
    }

    public Long getId() {
        return id;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getWittenUserId() {
        return wittenUserId;
    }

    public LocalDateTime getWrittenTime() {
        return writtenTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public Long getViews() {
        return views;
    }

    public String getNonMemNick() {
        return nonMemNick;
    }

    public String getNonMemPw() {
        return nonMemPw;
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public static PostVOBuilder builder(){
        return new PostVOBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        return o instanceof PostVO &&
                Objects.equals(id, ((PostVO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static final class PostVOBuilder {
        private Long id;
        private Integer boardId;
        private String title;
        private String content;
        private Long wittenUserId;
        private String writerNickname;
        private LocalDateTime writtenTime;
        private LocalDateTime updateTime;
        private Long views;
        private String nonMemNick;
        private String nonMemPw;

        private PostVOBuilder() {
        }

        public static PostVOBuilder aPostVO() {
            return new PostVOBuilder();
        }

        public PostVOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostVOBuilder boardId(Integer boardId) {
            this.boardId = boardId;
            return this;
        }

        public PostVOBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostVOBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostVOBuilder wittenUserId(Long wittenUserId) {
            this.wittenUserId = wittenUserId;
            return this;
        }

        public PostVOBuilder writerNickname(String writerNickname) {
            this.writerNickname = writerNickname;
            return this;
        }

        public PostVOBuilder writtenTime(LocalDateTime writtenTime) {
            this.writtenTime = writtenTime;
            return this;
        }

        public PostVOBuilder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public PostVOBuilder views(Long views) {
            this.views = views;
            return this;
        }

        public PostVOBuilder nonMemNick(String nonMemNick) {
            this.nonMemNick = nonMemNick;
            return this;
        }

        public PostVOBuilder nonMemPw(String nonMemPw) {
            this.nonMemPw = nonMemPw;
            return this;
        }

        public PostVO build() {
            return new PostVO(id, boardId, title, content, wittenUserId, writerNickname, writtenTime, updateTime, views, nonMemNick, nonMemPw);
        }
    }
}