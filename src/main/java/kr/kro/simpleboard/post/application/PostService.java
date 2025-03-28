package kr.kro.simpleboard.post.application;

import kr.kro.simpleboard.post.presentation.dto.PostCreateRequest;
import kr.kro.simpleboard.post.presentation.dto.PostResponse;

public interface PostService {

    PostResponse create(PostCreateRequest request, Long userId);

    PostResponse findById(Long id);
}
