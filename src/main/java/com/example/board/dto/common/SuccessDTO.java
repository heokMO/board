package com.example.board.dto.common;

import org.springframework.http.HttpStatus;

public class SuccessDTO extends MessageDTO{
    private static final long serialVersionUID = 1L;

    private final ResultDTO resultDTO;

    public SuccessDTO(ResultDTO resultDTO) {
        super(HttpStatus.OK.value());
        this.resultDTO = resultDTO;
    }

    public ResultDTO getResultDTO() {
        return resultDTO;
    }
    

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private ResultDTO resultDTO;

        public Builder resultDTO(ResultDTO resultDTO) {
            this.resultDTO = resultDTO;
            return this;
        }

        public SuccessDTO build() {
            return new SuccessDTO(resultDTO);
        }
    }
}
