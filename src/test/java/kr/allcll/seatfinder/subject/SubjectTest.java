package kr.allcll.seatfinder.subject;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubjectTest {

    @DisplayName("비전공 과목이 맞는지 검증한다.")
    @Test
    void isNonMajorTest() {
        Subject subject = new Subject(1L, "대양휴머니티칼리지", "대양휴머니티칼리지", "009352", "001", "사고와표현1", "공통교양필수", "학문기초", "1",
            "3.0", "3", "0", "이론", null, "화 목 09:00~10:30", "세101", "노지현", "국어국문학과", "외국인대상과목, 기초(Beginner)", null,
            null, "영어", "Y", null);

        assertThat(subject.isNonMajor()).isTrue();
    }

    @DisplayName("비전공 과목이 아닌지 검증한다.")
    @Test
    void isNonMajorTest2() {
        Subject subject = new Subject(1L, "소프트웨어융합대학", "컴퓨터공학과", "009352", "001", "사고와표현1", "공통교양필수", "학문기초", "1",
            "3.0", "3", "0", "이론", null, "화 목 09:00~10:30", "세101", "노지현", "국어국문학과", "외국인대상과목, 기초(Beginner)", null,
            null, "영어", "Y", null);

        assertThat(subject.isNonMajor()).isFalse();
    }
}
