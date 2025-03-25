package kr.kro.simpleboard.post.application;

import kr.kro.simpleboard.post.domain.Post;
import kr.kro.simpleboard.post.infrastructure.PostRepository;
import kr.kro.simpleboard.post.presentation.dto.PostCreateRequest;
import kr.kro.simpleboard.post.presentation.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostResponse create(PostCreateRequest request, Long userId) {
        Post post = Post.builder()
            .title(request.title())
            .content(request.content())
            .build();
        post.setCreatedBy(userId);

        Post saved = postRepository.save(post);

        return new PostResponse(
            saved.getId(),
            saved.getTitle(),
            saved.getContent(),
            saved.getViews(),
            saved.getLikes(),
            saved.getCreatedAt()
        );
    }
}
