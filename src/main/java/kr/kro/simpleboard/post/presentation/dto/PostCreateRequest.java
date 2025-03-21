package kr.kro.simpleboard.post.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest(
    @NotBlank String title,
    @NotBlank String content
) {

}