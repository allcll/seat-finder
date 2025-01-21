package kr.allcll.seatfinder.subject;

import java.io.IOException;
import kr.allcll.seatfinder.excel.SubjectSheetParser;
import kr.allcll.seatfinder.excel.SubjectsParsingResponse;
import kr.allcll.seatfinder.subject.dto.SubjectsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class SubjectApi {

    private final SubjectService subjectService;
    private final SubjectSheetParser subjectSheetParser;

    @PostMapping("/api/subject/upload")
    public ResponseEntity<String> uploadSubjects(@RequestParam MultipartFile file) throws IOException {
        SubjectsParsingResponse parsedSubjects = subjectSheetParser.parse(file);
        SubjectsRequest subjectsRequest = SubjectsRequest.from(parsedSubjects);
        subjectService.save(subjectsRequest);
        return ResponseEntity.ok("업로드에 성공했습니다.");
    }
}
