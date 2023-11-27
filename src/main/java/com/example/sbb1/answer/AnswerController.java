package com.example.sbb1.answer;

import com.example.sbb1.question.Question;
import com.example.sbb1.question.QuestionService;
import com.example.sbb1.user.SiteUser;
import com.example.sbb1.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        //답변 부모 질문 객체를 받아온다.
        Question question = this.questionService.getquestion(id);

        SiteUser author = this.userService.getUser(principal.getName());
        //hasErrors() : 오류 검증 메서드, true시 오류 발생 했다는 뜻
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent(), author);
        return"redirect:/question/detail/%d".formatted(id);
    }



}
