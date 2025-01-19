package kr.allcll.seatfinder.subject;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectApp {

    private final SubjectRepository subjectRepository;

    public void save(SubjectsRequest subjectRequests) {
        List<Subject> subjects = subjectRequests.toEntity();
        subjectRepository.saveAll(subjects);
    }
}
