package kr.allcll.seatfinder.subject;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.allcll.seatfinder.subject.dto.SubjectsResponse;
import kr.allcll.seatfinder.support.fixture.SubjectFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SubjectServiceTest {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        subjectRepository.deleteAllInBatch();
        initializeSubjects();
    }

    private void initializeSubjects() {
        subjectRepository.saveAll(
            List.of(
                SubjectFixture.createSubject(null, "컴퓨터구조", "003278", "001", "유재석"),
                SubjectFixture.createSubject(null, "운영체제", "003279", "001", "노홍철"),
                SubjectFixture.createSubject(null, "자료구조", "003280", "001", "하하"),
                SubjectFixture.createSubject(null, "알고리즘", "003281", "001", "길"),
                SubjectFixture.createSubject(null, "컴퓨터구조", "003278", "002", "정형돈"),
                SubjectFixture.createSubject(null, "운영체제", "003279", "002", "나영석"),
                SubjectFixture.createSubject(null, "자료구조", "003280", "003", "박명수"),
                SubjectFixture.createSubject(null, "알고리즘", "003281", "004", "전진")
            )
        );
    }

    @DisplayName("과목명으로 과목을 조회한다.")
    @Test
    void findSubjectByQueryTest() {
        SubjectsResponse subjectsResponse = subjectService.findSubjectsByCondition(null, "컴퓨터구조", null, null, null);

        assertThat(subjectsResponse.subjectResponses()).hasSize(2);
        assertThat(subjectsResponse.subjectResponses().get(0).subjectName()).isEqualTo("컴퓨터구조");
        assertThat(subjectsResponse.subjectResponses().get(0).subjectCode()).isEqualTo("003278");
        assertThat(subjectsResponse.subjectResponses().get(0).classCode()).isEqualTo("001");
        assertThat(subjectsResponse.subjectResponses().get(0).professorName()).isEqualTo("유재석");
        assertThat(subjectsResponse.subjectResponses().get(1).subjectName()).isEqualTo("컴퓨터구조");
        assertThat(subjectsResponse.subjectResponses().get(1).subjectCode()).isEqualTo("003278");
        assertThat(subjectsResponse.subjectResponses().get(1).classCode()).isEqualTo("002");
        assertThat(subjectsResponse.subjectResponses().get(1).professorName()).isEqualTo("정형돈");
    }

    @DisplayName("학수번호, 분반, 교수명으로 과목을 조회한다.")
    @Test
    void findSubjectByQueryTest2() {
        SubjectsResponse subjectsResponse = subjectService.findSubjectsByCondition(null, null, "003279", "001", "노홍철");

        assertThat(subjectsResponse.subjectResponses()).hasSize(1);
        assertThat(subjectsResponse.subjectResponses().get(0).subjectName()).isEqualTo("운영체제");
        assertThat(subjectsResponse.subjectResponses().get(0).subjectCode()).isEqualTo("003279");
        assertThat(subjectsResponse.subjectResponses().get(0).classCode()).isEqualTo("001");
        assertThat(subjectsResponse.subjectResponses().get(0).professorName()).isEqualTo("노홍철");
    }

    @DisplayName("존재하지 않는 조건으로 과목을 조회한다.")
    @Test
    void findSubjectByQueryTest3() {
        SubjectsResponse subjectsResponse = subjectService.findSubjectsByCondition(100L, null, "003279", "001",
            "유재석");

        assertThat(subjectsResponse.subjectResponses()).hasSize(0);
    }
}
