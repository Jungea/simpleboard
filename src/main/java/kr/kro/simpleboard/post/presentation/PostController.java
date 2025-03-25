package kr.kro.simpleboard.post.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.kro.simpleboard.post.application.PostService;
import kr.kro.simpleboard.post.presentation.dto.PostCreateRequest;
import kr.kro.simpleboard.post.presentation.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시글", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 등록", description = "새로운 게시글을 등록합니다.")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest request) {
        PostResponse response = postService.create(request, 1L);  // TODO: memberId 하드코딩 제거 예정
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}