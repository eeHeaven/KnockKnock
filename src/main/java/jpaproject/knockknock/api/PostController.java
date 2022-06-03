package jpaproject.knockknock.api;

import jpaproject.knockknock.api.response.PostListResponse;
import jpaproject.knockknock.api.response.PostResponse;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.api.request.PostSaveRequest;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.post_comment.CommentService;
import jpaproject.knockknock.service.post_comment.ImageService;
import jpaproject.knockknock.service.post_comment.PostService;
import jpaproject.knockknock.strategy.factory.PointModifyFactory;
import jpaproject.knockknock.strategy.pointmodify.PointModify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final MemberService memberService;
    private final PointModifyFactory pointModifyFactory;

    //로그인 한 계정으로 게시글 작성하기
    @PostMapping(value = "api/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponse savePost(@RequestPart("request") @Valid PostSaveRequest request,
                                     @RequestPart(required = false) MultipartFile image
    ) throws IOException {
        Post post = postService.save(request);
        if (image != null) imageService.saveImage(image, "knockknock", post);
        PostResponse response = PostResponse.entityToDto(post);

        log.info("게시글 작성하기 실행");
        return response;
    }

    //로그인 한 계정으로 자신이 작성한 게시글 리스트 조회하기
    @GetMapping("api/post/viewlist/{userId}")
    public Result viewPostofWriter(@PathVariable("userId")String writerId){
        List<Post> posts = postService.getUserPosts(writerId);
        List<PostListResponse> dtos = PostListResponse.entityListToDtoList(posts);
        log.info("dto ={}", dtos);
        return new Result(dtos);
    }

    //전체 게시글 리스트 조회하기
    @GetMapping("api/post/view")
    public Result viewPost(){
        List<Post> posts = postService.getPosts();
        List<PostListResponse> dtos = PostListResponse.entityListToDtoList(posts);
        return new Result(dtos);
    }

    //위치기반 1km 이내 게시글 리스트 조회하기
    @GetMapping("api/post/view/{latitude}/{longitude}")
    public Result viewPostListbyLocation(@PathVariable("latitude")Double latitude, @PathVariable("longitude")Double longitude){
        List<Post> posts = postService.getPostsbyLocation(latitude, longitude);
        List<PostListResponse> dtos =PostListResponse.entityListToDtoList(posts);
        return new Result(dtos);
    }

    //게시글 하나 detail 정보 조회
    @GetMapping("api/post/view/{userId}/{postId}")
    public PostResponse viewEachPost(@PathVariable("userId")String userId, @PathVariable("postId")Long id){
        Post post = postService.getPostById(id);
        //포인트 변화
        PointModify pointModify = pointModifyFactory.findPointModify(PointModify.Situation.viewPost);
        Member user = memberService.findByUserId(userId);
        pointModify.modifyPointof(user);
        return PostResponse.entityToDto(post);
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
