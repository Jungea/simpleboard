package kr.kro.simpleboard.post.application;

import kr.kro.simpleboard.post.domain.Post;
import kr.kro.simpleboard.post.exception.PostNotFoundException;
import kr.kro.simpleboard.post.infrastructure.PostRepository;
import kr.kro.simpleboard.post.presentation.dto.PostCreateRequest;
import kr.kro.simpleboard.post.presentation.dto.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class PostServiceTest {
    private final PostRepository postRepository = mock(PostRepository.class);
    private final PostService postService = new PostServiceImpl(postRepository);

    @DisplayName("게시글 등록 성공")
    @Test
    void createPost() {
        // given
        PostCreateRequest request = new PostCreateRequest("제목", "내용");

        Post savedPost = Post.builder()
                .id(1L)
                .title(request.title())
                .content(request.content())
                .views(0)
                .likes(0)
                .build();

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // when
        PostResponse response = postService.create(request, 1L);

        // then
        assertThat(response.id()).isEqualTo(savedPost.getId());
        assertThat(response.title()).isEqualTo(savedPost.getTitle());
        assertThat(response.content()).isEqualTo(savedPost.getContent());
        assertThat(response.views()).isEqualTo(savedPost.getViews());
        assertThat(response.likes()).isEqualTo(savedPost.getLikes());

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @DisplayName("게시글 단건 조회 - 조회수 증가 포함")
    @Test
    void findById_increaseViewsAndReturnPost() {
        // given
        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("테스트 제목")
                .content("테스트 내용")
                .views(0)
                .likes(0)
                .build();

        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        PostResponse response = postService.findById(postId);

        // then
        assertThat(response.id()).isEqualTo(postId);
        assertThat(response.title()).isEqualTo("테스트 제목");
        assertThat(response.views()).isEqualTo(1); // ✅ 조회수 1 증가 확인
        verify(postRepository).save(any(Post.class)); // 저장 호출 확인
    }

    @DisplayName("게시글 조회 실패 - 존재하지 않음")
    @Test
    void findById_fail() {
        // given
        Long postId = 999L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.findById(postId))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessageContaining("존재하지 않는 게시글");
    }
}