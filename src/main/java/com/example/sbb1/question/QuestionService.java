package com.example.sbb1.question;

import com.example.sbb1.DataNotExecption;
import com.example.sbb1.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//final 키워드가 있는 객체만 생성자를 만든다.
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getquestion(Integer id) {
        Optional<Question> oq = this.questionRepository.findById(id);

        if (oq.isPresent() == false) throw new DataNotExecption("question not found");
        return oq.get();

//        if (oq.isPresent()) {
//            return oq.get();
//        } else {
//            throw new DataNotExecption("question not found");
//        }
    }

    public Question create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setContent(content);
        q.setSubject(subject);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);

        this.questionRepository.save(q);
        return q;
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public Page<Question> getList(int page) {
        //역순 정렬
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }
}

