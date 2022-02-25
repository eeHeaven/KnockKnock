package jpaproject.knockknock.api;

import jpaproject.knockknock.api.request.PostWriteRequest;
import jpaproject.knockknock.api.response.CommentDto;
import jpaproject.knockknock.api.response.PostListShowResponse;
import jpaproject.knockknock.api.response.PostSaveResponse;
import jpaproject.knockknock.api.response.PostViewResponse;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import jpaproject.knockknock.requestForm.PostSaveRequest;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.post_comment.CommentService;
import jpaproject.knockknock.service.post_comment.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    //로그인 한 계정으로 게시글 작성하기
    @PostMapping("api/post/{userId}")
    public PostSaveResponse savePost(@RequestBody @Valid PostWriteRequest request ,
                                     @PathVariable("userId") String writerId){

       PostSaveRequest postSaveRequest = new PostSaveRequest(request.getTitle(),request.getContent(),writerId,request.getHashtag());
       Post post = postService.save(postSaveRequest);
       PostSaveResponse response = new PostSaveResponse(post.getId(),post.getPostwriter().getUserId(),post.getTitle(),post.getContent(),post.getTimestamp());
       String[] hashtag = postSaveRequest.getHashTags().split(" ");
       List<String> hashtags = new ArrayList<>();
       for(String tag: hashtag)hashtags.add(tag);
       response.setHashtag(hashtags);
       return response;
    }

    //로그인 한 계정으로 자신이 작성한 게시글 리스트 조회하기
    @GetMapping("api/post/viewlist/{userId}")
    public Result viewPostofWriter(@PathVariable("userId")String writerId){
        List<Post> posts = postService.getUserPosts(writerId);
        List<PostSaveResponse> dtos = posts.stream().map(p->new PostSaveResponse(p.getId(),p.getPostwriter().getUserId(),p.getTitle(),p.getContent(),p.getTimestamp()))
                .collect(Collectors.toList());
        log.info("dto ={}", dtos);
        return new Result(dtos);
    }

    //전체 게시글 리스트 조회하기
    @GetMapping("api/post/view")
    public Result viewPost(){
        List<Post> posts = postService.getPosts();
        List<PostListShowResponse> dtos = posts.stream().map(p->new PostListShowResponse(p.getId(),p.getPostwriter().getUserId(),p.getTitle(),p.getContent(),p.getTimestamp().toString(),p.getPostTags()))
                .collect(Collectors.toList());
        return new Result(dtos);
    }

    //게시글 하나 detail 정보 조회
    @GetMapping("api/post/view/{postId}")
    public PostViewResponse viewEachPost(@PathVariable("postId")Long id){
        Post post = postService.getPostById(id);
        PostViewResponse response =  new PostViewResponse(post.getId(),
               post.getPostwriter().getNickName(),
                post.getPostwriter().getUserId(),
                post.getTitle(),
                post.getContent(),
                post.getTimestamp());
        List<Comment> comments = post.getPostcomments();
        List<PostHashTag> postHashTags = post.getPostTags();
        List<CommentDto> commentDtoList = comments.stream().map(c->new CommentDto(c.getId(),c.getCommentwriter().getNickName(),c.getCommentwriter().getUserId(),c.getTimestamp(),c.getContent()))
                .collect(Collectors.toList());
        response.setCommentlist(commentDtoList);
        List<String> hashtag = new ArrayList<>();
        for(PostHashTag tag: postHashTags) hashtag.add(tag.getTag());
        response.setPosthashtag(hashtag);

        return response;
    }

    @DeleteMapping("api/post/view/{postid}/delete")
    public void deletePost(@PathVariable("postid")Long postid){
        postService.delete(postid);
    }

    @DeleteMapping("api/comment/{commentid}/delete")
    public void deleteComment(@PathVariable("commentid")Long commentid){
        commentService.delete(commentid);
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

}
