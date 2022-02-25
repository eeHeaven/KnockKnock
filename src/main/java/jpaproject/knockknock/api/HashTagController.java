package jpaproject.knockknock.api;


import jpaproject.knockknock.api.response.PostListShowResponse;
import jpaproject.knockknock.api.response.PostSaveResponse;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.service.post_comment.HashTagService;
import jpaproject.knockknock.service.post_comment.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HashTagController {

    private final HashTagService hashTagService;
    private final PostService postService;

    @GetMapping("api/{hashtag}/posts")
    public Result viewPostByTag(@PathVariable("hashtag")String hashtag){
        List<Post> posts = hashTagService.PostListByTag(hashtag);
        log.info("posts = {}",posts);
        log.info("hashtag = {}",hashtag);
        List<PostListShowResponse> dtos = posts.stream().map(p->new PostListShowResponse(p.getId(),p.getPostwriter().getUserId(),p.getTitle(),p.getContent(),p.getTimestamp().toString(),p.getPostTags()))
                .collect(Collectors.toList());
        return new Result(dtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
