package jpaproject.knockknock.api;

import jpaproject.knockknock.api.request.PostWriteRequest;
import jpaproject.knockknock.api.response.PostSaveResponse;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.requestForm.PostSaveRequest;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.post_comment.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //로그인 한 계정으로 게시글 작성하기  (*아직 해시태그 기능 구현 안함)
    @PostMapping("api/post/{userId}")
    public PostSaveResponse savePost(@RequestBody @Valid PostWriteRequest request ,
                                     @PathVariable("userId") String writerId){

       PostSaveRequest postSaveRequest = new PostSaveRequest(writerId,request.getTitle(),request.getContent(),new ArrayList<>());
       Post post = postService.save(postSaveRequest);
       PostSaveResponse response = new PostSaveResponse(post.getPostwriter().getUserId(),post.getTitle(),post.getContent(),post.getTimestamp());
       return response;
    }

    //로그인 한 계정으로 자신이 작성한 게시글 조회하기
    @GetMapping("api/post/view/{userId}")
    public Result viewPostofWriter(@PathVariable("userId")String writerId){
        List<Post> posts = postService.getUserPosts(writerId);
        List<PostSaveResponse> dtos = posts.stream().map(p->new PostSaveResponse(p.getPostwriter().getUserId(),p.getTitle(),p.getContent(),p.getTimestamp()))
                .collect(Collectors.toList());
        return new Result(dtos);
    }

    //전체 게시글 조회하기
    @GetMapping("api/post/view")
    public Result viewPost(){
        List<Post> posts = postService.getPosts();
        List<PostSaveResponse> dtos = posts.stream().map(p->new PostSaveResponse(p.getPostwriter().getUserId(),p.getTitle(),p.getContent(),p.getTimestamp()))
                .collect(Collectors.toList());
        return new Result(dtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

}
