package jpaproject.knockknock.api;


import jpaproject.knockknock.api.response.HashTagResponse;
import jpaproject.knockknock.api.response.PostListResponse;
import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.elk.HashTagESService;
import jpaproject.knockknock.service.post_comment.HashTagService;
import jpaproject.knockknock.service.post_comment.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HashTagController {

    private final HashTagService hashTagService;
    private final HashTagESService hashTagESService;

    @GetMapping("api/post/view/hashtag")
    public Result<List<PostListResponse>> viewPostByTag(@RequestParam(name = "hashtag") String hashtag) {
        List<Post> posts = hashTagService.PostListByTag(hashtag);
        if (posts == null) {
            log.info("해시태그 {}를 포함한 게시글 없음", hashtag);
            return new Result<>(SuccessCode.LIST_SUCCESSFULLY_RETURNED, new ArrayList<>());
        }

        List<PostListResponse> dtos = posts.stream()
                .map(PostListResponse::entityToDto)
                .collect(Collectors.toList());
        log.info("해시태그 {}를 포함한 게시글 {} 개 조회", hashtag, posts.size());
        return new Result<>(SuccessCode.LIST_SUCCESSFULLY_RETURNED, dtos);
    }

    @GetMapping("api/search/autocomplete")
    public Result<List<HashTagResponse>> viewHashTagAutoCompleteResult(@RequestParam(name = "input") String input) {
        log.info("AutoCompleteApi start: input = " + input);
        List<HashTag> hashTags = hashTagESService.findHashTagbyinput(input);
        for (HashTag each : hashTags) {
            log.info("조회된 검색어 자동완성 리스트 : " + each.getTag());
        }
        List<HashTagResponse> dtos = hashTags.stream().map(h -> new HashTagResponse(h.getTag()))
                .collect(Collectors.toList());
        return new Result<>(SuccessCode.LIST_SUCCESSFULLY_RETURNED, dtos);
    }

}
