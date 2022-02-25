package jpaproject.knockknock.service.post_comment;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.CommentRepository;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import jpaproject.knockknock.requestForm.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        Post post = postRepository.findOneById(commentRequest.getPostId());
        Member writer = memberRepository.findByUserId(commentRequest.getWriterId());

        Comment comment = new Comment();
        comment.setTimestamp(LocalDateTime.now());
        comment.setContent(commentString);
        comment.addToPost(post);
        comment.setCommentwriter(writer);
        commentRepository.save(comment);

        return comment;
    }

    //댓글 삭제
    @Transactional
    public void delete(Long id){
        Comment comment = commentRepository.findOne(id);
        commentRepository.remove(comment);
    }
}
