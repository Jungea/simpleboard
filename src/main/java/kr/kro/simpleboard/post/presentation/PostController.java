package kr.kro.simpleboard.post.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.kro.simpleboard.global.exception.ErrorResponse;
import kr.kro.simpleboard.post.application.PostService;
import kr.kro.simpleboard.post.presentation.dto.PostCreateRequest;
import kr.kro.simpleboard.post.presentation.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공",
                    content = @Content(schema = @Schema(implementation = PostCreateRequest.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ValidationError",
                                    summary = "입력값 오류 예시",
                                    value = """
                                                {
                                                  "status": 400,
                                                  "code": "VALIDATION_ERROR",
                                                  "message": "게시글 제목은 필수입니다."
                                                }
                                            """
                            ))),
    })
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest request) {
        PostResponse response = postService.create(request, 1L);  // TODO: memberId 하드코딩 제거 예정
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "게시글 단건 조회", description = "ID로 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공",
                    content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "PostNotFound",
                                    summary = "게시글이 존재하지 않을 경우",
                                    value = """
                                                {
                                                  "status": 404,
                                                  "code": "POST_NOT_FOUND",
                                                  "message": "존재하지 않는 게시글입니다. ID: 999"
                                                }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse response = postService.findById(id);
        return ResponseEntity.ok(response);
    }
}