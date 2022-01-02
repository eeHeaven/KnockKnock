package jpaproject.knockknock.Controller;

import jpaproject.knockknock.requestForm.PostSaveRequest;
import jpaproject.knockknock.service.post_comment.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostServiceController {

    final PostService postService;

    @PostMapping("") // 포스트 새로 작성
    public String post(PostSaveRequest postSaveRequest){
        postService.save(postSaveRequest);
        return "";
    }

    @DeleteMapping(path = "/{id}") // 포스트 삭제
    public String postDelete(@PathVariable(value = "id")Long id){
        postService.delete(id);
        return "";
    }
}
