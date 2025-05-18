package kr.kro.simpleboard.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "에러 응답 DTO")
public class ErrorResponse {

    @Schema(description = "HTTP 상태 코드", example = "404")
    private final int status;

    @Schema(description = "에러 코드", example = "RESOURCE_NOT_FOUND")
    private final String code;

    @Schema(description = "에러 메시지", example = "요청하신 리소스를 찾을 수 없습니다.")
    private final String message;

    public ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatusCode();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse of(int status, String code, String message) {
        return new ErrorResponse(status, code, message);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, String customMessage) {
        return new ErrorResponse(errorCode.getStatusCode(), errorCode.getCode(), customMessage);
    }
}
