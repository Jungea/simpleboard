package kr.kro.simpleboard.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kro.simpleboard.post.application.PostService;
import kr.kro.simpleboard.post.exception.PostNotFoundException;
import kr.kro.simpleboard.post.presentation.dto.PostCreateRequest;
import kr.kro.simpleboard.post.presentation.dto.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@ImportAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
})
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @DisplayName("게시글 등록 성공")
    @Test
    void createPost() throws Exception {
        // given
        PostCreateRequest request = new PostCreateRequest("제목", "내용");

        PostResponse response = new PostResponse(
                1L,
                "제목",
                "내용",
                0,
                0,
                LocalDateTime.now()
        );

        given(postService.create(any(), any())).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"));
    }

    @DisplayName("게시글 등록 실패 - 비어있음")
    @Test
    void createPost_withBlank_shouldReturnBadRequest() throws Exception {
        // given
        PostCreateRequest request = new PostCreateRequest("", "내용 있음");

        // when & then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").exists()); // 메시지는 메시지 바디에 따라 조절

        // given
        PostCreateRequest request2 = new PostCreateRequest("제목 있음", "");

        // when & then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").exists()); // 메시지는 메시지 바디에 따라 조절
    }

    @DisplayName("게시글 단건 조회 성공")
    @Test
    void getPostById() throws Exception {
        // given
        Long postId = 1L;
        PostResponse response = new PostResponse(
                postId,
                "테스트 제목",
                "테스트 내용",
                10,
                5,
                LocalDateTime.now()
        );

        given(postService.findById(postId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("테스트 제목"))
                .andExpect(jsonPath("$.content").value("테스트 내용"));
    }

    @DisplayName("게시글 단건 조회 실패 - 존재하지 않는 ID")
    @Test
    void getPostById_NotFound() throws Exception {
        // given
        Long postId = 999L;
        given(postService.findById(postId)).willThrow(new PostNotFoundException(postId));

        // when & then
        mockMvc.perform(get("/api/posts/{id}", postId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("POST_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 게시글입니다. ID: " + postId));
    }
}