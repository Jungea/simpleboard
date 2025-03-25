package kr.kro.simpleboard.post.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "게시글 응답 DTO")
public record PostResponse(
        @Schema(description = "게시글 ID", example = "1")
        Long id,

        @Schema(description = "게시글 제목", example = "오늘의 일기")
        String title,

        @Schema(description = "게시글 내용", example = "날씨가 맑아서 산책을 다녀왔다.")
        String content,

        @Schema(description = "조회수", example = "0")
        int views,

        @Schema(description = "좋아요 수", example = "0")
        int likes,

        @Schema(description = "게시글 생성일시", example = "2025-03-23T12:34:56")
        LocalDateTime createdAt
) {

}