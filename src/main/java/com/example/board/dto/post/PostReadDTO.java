package com.example.board.dto.post;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class PostReadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String contents;
    private String writer;
    private Boolean updatable;
    private List<Integer> images;
    private String writtenNonMem;
    private LocalDateTime updateTime;
    private Long views;

    public PostReadDTO() {
    }

    public PostReadDTO(Long id, String title, String contents, String writer, Boolean updatable, List<Integer> images, String writtenNonMem, LocalDateTime updateTime, Long views) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.updatable = updatable;
        this.images = images;
        this.writtenNonMem = writtenNonMem;
        this.updateTime = updateTime;
        this.views = views;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Boolean getUpdatable() {
        return updatable;
    }

    public List<Integer> getImages() {
        return images;
    }

    public String getWrittenNonMem() {
        return writtenNonMem;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public String getWriter() {
        return writer;
    }

    public Long getViews() {
        return views;
    }

    public static PostReadDTOBuilder builder(){
        return new PostReadDTOBuilder();
    }

    public static final class PostReadDTOBuilder {
        private Long id;
        private String title;
        private String contents;
        private String writer;
        private Boolean updatable;
        private List<Integer> images;
        private String writtenNonMem;
        private LocalDateTime updateTime;
        private Long views;

        private PostReadDTOBuilder() {
        }

        public static PostReadDTOBuilder aPostReadDTO() {
            return new PostReadDTOBuilder();
        }

        public PostReadDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostReadDTOBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostReadDTOBuilder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public PostReadDTOBuilder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public PostReadDTOBuilder updatable(Boolean updatable) {
            this.updatable = updatable;
            return this;
        }

        public PostReadDTOBuilder images(List<Integer> images) {
            this.images = images;
            return this;
        }

        public PostReadDTOBuilder writtenNonMem(String writtenNonMem) {
            this.writtenNonMem = writtenNonMem;
            return this;
        }

        public PostReadDTOBuilder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public PostReadDTOBuilder views(Long views) {
            this.views = views;
            return this;
        }

        public PostReadDTO build() {
            return new PostReadDTO(id, title, contents, writer, updatable, images, writtenNonMem, updateTime, views);
        }
    }
}
