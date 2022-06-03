package jpaproject.knockknock.service.post_comment;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.exception.CustomException;
import jpaproject.knockknock.exception.ExceptionEnum;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.CommentRepository;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import jpaproject.knockknock.api.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //댓글 저장
    @Transactional
    public Comment save(CommentRequest commentRequest){
       String commentString =  commentRequest.getComment();
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(()-> new CustomException(ExceptionEnum.POST_NOT_FOUND));
        Member writer = memberRepository.findByUserId(commentRequest.getWriterId())
                .orElseThrow(()-> new CustomException(ExceptionEnum.USER_NOT_FOUND));

        Comment comment = Comment.create(writer,post,commentString);
        commentRepository.save(comment);

        return comment;
    }

    //댓글 삭제
    @Transactional
    public void delete(Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }
}
