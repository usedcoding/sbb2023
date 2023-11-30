package com.example.sbb1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.example.sbb1.answer.Answer;
import com.example.sbb1.answer.AnswerRepository;
import com.example.sbb1.question.Question;
import com.example.sbb1.question.QuestionRepository;
import com.example.sbb1.question.QuestionService;
import com.example.sbb1.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserService userService;

	@Test
	@DisplayName("데이터 저장하기")
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	@DisplayName("전체 조회")
	void test001() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2,all.size());

		Question q =all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName(("id 단건조회"))
	void test002() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	@DisplayName("subject 단건 조회")
	void test003() {
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1,q.getId());
	}
	@Test
	@DisplayName("제목 내용 동시 조회")
	void test004() {
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1,q.getId());
	}

	@Test
	@DisplayName("특정 문자열 조회")
	void test005() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?",q.getSubject());
	}

	@Test
	@DisplayName("데이터 수정")
	void test006() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	@DisplayName("데이터 삭제")
	void test007() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q =oq.get();
		this.questionRepository.delete(q);
		assertEquals(1,this.questionRepository.count());
	}
	@Test
	@DisplayName("답변 생성 후 저장")
	void test008() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	@DisplayName("답변 조회")
	void test009() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2,a.getQuestion().getId());
	}

	@Transactional
	@Test
	@DisplayName("질문 기준 답변 가져오기")
	void test010() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1,answerList.size());
		assertEquals("네 자동으로 생성됩니다.",answerList.get(0).getContent());
	}

	@Test
	@DisplayName("테스트 데이터")
	void test11() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
	}

//	@Test
//	@DisplayName("스트림 버전 테스트 데이터")
//	void test12(){
//		IntStream.rangeClosed(3,300)
//				.forEach(no -> questionService("테스트 제목입니다.%d".formatted(no),"테스트 내용입니다.%d".formatted(no)));
//	}

	//test 실행 전에 무조건 실행 되게 하는 annotation
	//@BeforeEach
	@Test
	@DisplayName("회원 데이터")
	void test013() {
		userService.create("user13", "user13@test.com", "1234");
		userService.create("user14", "user14@test.com", "1234");
	}


}