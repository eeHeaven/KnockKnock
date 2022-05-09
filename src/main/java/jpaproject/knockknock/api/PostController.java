package jpaproject.knockknock.api;

import jpaproject.knockknock.api.request.PostWriteRequest;
import jpaproject.knockknock.api.response.CommentDto;
import jpaproject.knockknock.api.response.PostListShowResponse;
import jpaproject.knockknock.api.response.PostSaveResponse;
import jpaproject.knockknock.api.response.PostViewResponse;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Image;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import jpaproject.knockknock.requestForm.PostSaveRequest;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.post_comment.CommentService;
import jpaproject.knockknock.service.post_comment.ImageService;
import jpaproject.knockknock.service.post_comment.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import java.io.IOException;
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
    private final ImageService imageService;

    //로그인 한 계정으로 게시글 작성하기
    @PostMapping(value = "api/post/{userId}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostSaveResponse savePost(@RequestPart("request") @Valid PostWriteRequest request , @RequestPart(required = false) MultipartFile image,
                                     @PathVariable("userId") String writerId) throws IOException {

       PostSaveRequest postSaveRequest = new PostSaveRequest(request.getTitle(),request.getContent(),writerId,request.getHashtag(),request.getLat(),request.getLon(),request.getLocation());
       Post post = postService.save(postSaveRequest);
        PostSaveResponse response = new PostSaveResponse(post.getId(),post.getPostwriter().getUserId(),post.getTitle(),post.getContent(),post.getTimestamp(),post.getLocation());
        String[] hashtag = postSaveRequest.getHashTags().split(" ");
        List<String> hashtags = new ArrayList<>();
        for(String tag: hashtag)hashtags.add(tag);
        response.setHashtag(hashtags);
       String imguri = null;
      if(image != null) {
          imguri =  imageService.saveImage(image,"knockknock",post);
          List<String> imgs = new ArrayList<>();
          imgs.add(imguri);
        response.setImage(imgs);}
      else {response.setImage(null);}

       log.info("게시글 작성하기 실행");
       return response;
    }
    //로그인 한 계정으로 자신이 작성한 게시글 리스트 조회하기
    @GetMapping("api/post/viewlist/{userId}")
    public Result viewPostofWriter(@PathVariable("userId")String writerId){
        List<Post> posts = postService.getUserPosts(writerId);
        List<PostListShowResponse> dtos = posts.stream().map(p->new PostListShowResponse(p.getId(),p.getPostwriter().getUserId(),p.getTitle(),p.getContent(),p.getTimestamp().toString(),p.getPostTags()))
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

    //위치기반 1km 이내 게시글 리스트 조회하기
    @GetMapping("api/post/view/{latitude}/{longitude}")
    public Result viewPostListbyLocation(@PathVariable("latitude")Double latitude, @PathVariable("longitude")Double longitude){
        List<Post> posts = postService.getPostsbyLocation(latitude, longitude);
        List<PostListShowResponse> dtos = posts.stream().map(p->new PostListShowResponse(p.getId(),p.getPostwriter().getUserId(),p.getTitle(),p.getContent(),p.getTimestamp(),p.getPostTags()))
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
                post.getTimestamp(),
                post.getLocation()
        );
        List<Comment> comments = post.getPostcomments();
        List<PostHashTag> postHashTags = post.getPostTags();
        List<CommentDto> commentDtoList = comments.stream().map(c->new CommentDto(c.getId(),c.getCommentwriter().getNickName(),c.getCommentwriter().getUserId(),c.getTimestamp(),c.getContent()))
                .collect(Collectors.toList());
        response.setCommentlist(commentDtoList);
        List<String> hashtag = new ArrayList<>();
        for(PostHashTag tag: postHashTags) hashtag.add(tag.getTag());
        response.setPosthashtag(hashtag);
        List<Image> images = post.getImg();
       if(!images.isEmpty()) response.setImage(images.get(0).getImgurl());

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



}
