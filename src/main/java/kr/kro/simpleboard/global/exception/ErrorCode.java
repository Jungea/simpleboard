package kr.kro.simpleboard.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Schema(description = "에러 코드 정의")
public enum ErrorCode {

    @Schema(description = "게시글이 존재하지 않습니다.")
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),

    @Schema(description = "유효성 검사에 실패하였습니다.")
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사에 실패하였습니다."),

    @Schema(description = "예상치 못한 서버 오류가 발생했습니다.")
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatusCode() {
        return status.value();
    }

    public String getCode() {
        return this.name();
    }

}
