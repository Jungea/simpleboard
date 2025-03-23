package kr.kro.simpleboard.post.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "게시글 등록 요청 DTO")
public record PostCreateRequest(
        @Schema(description = "게시글 제목", example = "오늘의 일기")
        @NotBlank String title,

        @Schema(description = "게시글 내용", example = "날씨가 맑아서 산책을 다녀왔다.")
        @NotBlank String content
) {

}