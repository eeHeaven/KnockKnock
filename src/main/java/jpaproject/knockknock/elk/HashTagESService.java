package jpaproject.knockknock.elk;

import jpaproject.knockknock.domain.post_comment.HashTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashTagESService {
    @Autowired
    HashTagESRepository hashTagESRepository;

    public void saveHashtag(HashTag hashTag) {
        hashTagESRepository.save(hashTag);
    }

    public void deleteHashtag(HashTag hashTag) {
        hashTagESRepository.delete(hashTag);
    }

    public List<HashTag> findHashTagbyinput(String input) {
        return hashTagESRepository.findByTag(input);
    }
}
