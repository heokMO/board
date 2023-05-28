package com.example.board.dto.common;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class MessageDTO implements Serializable{
    private final Integer status;
    private final LocalDateTime timestamp = LocalDateTime.now();
    
    public MessageDTO(Integer status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
