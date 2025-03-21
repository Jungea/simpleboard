package kr.kro.simpleboard.post.presentation.dto;

import java.time.LocalDateTime;

public record PostResponse(
    Long id,
    String title,
    String content,
    int views,
    int likes,
    LocalDateTime createdAt
) {

}