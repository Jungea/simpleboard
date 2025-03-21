package kr.kro.simpleboard.post.presentation;

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

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest request) {
        Long userId = 1L; // Spring Security 적용 전, 임시 하드코딩
        PostResponse response = postService.create(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}