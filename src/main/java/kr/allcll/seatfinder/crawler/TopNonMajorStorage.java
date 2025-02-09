package kr.allcll.seatfinder.crawler;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TopNonMajorStorage {

    private final List<Long> topNonMajors;

    public TopNonMajorStorage() {
        this.topNonMajors = new ArrayList<>();
    }

    public void addAll(List<Long> subjectIds) {
        this.topNonMajors.addAll(subjectIds);
    }
}
