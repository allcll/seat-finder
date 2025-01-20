package kr.allcll.seatfinder.subject;

import java.io.IOException;
import kr.allcll.seatfinder.excel.SubjectSheetParser;
import kr.allcll.seatfinder.excel.SubjectsParsingResponse;
import kr.allcll.seatfinder.subject.dto.SubjectsRequest;
import kr.allcll.seatfinder.subject.dto.SubjectsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class SubjectApi {

    private final SubjectService subjectService;
    private final SubjectSheetParser subjectSheetParser;

    @GetMapping("/api/subjects")
    public ResponseEntity<SubjectsResponse> getSubjects(
        @RequestParam(required = false) Long subjectId,
        @RequestParam(required = false) String subjectName,
        @RequestParam(required = false) String subjectCode,
        @RequestParam(required = false) String classCode,
        @RequestParam(required = false) String professorName
    ) {
        SubjectsResponse subjectsResponse = subjectService.findSubjectsByCondition(
            subjectId,
            subjectName,
            subjectCode,
            classCode,
            professorName
        );
        return ResponseEntity.ok(subjectsResponse);
    }

    @PostMapping("/api/subject/upload")
    public ResponseEntity<String> uploadSubjects(@RequestParam MultipartFile file) throws IOException {
        SubjectsParsingResponse parsedSubjects = subjectSheetParser.parse(file);
        SubjectsRequest subjectsRequest = SubjectsRequest.from(parsedSubjects);
        subjectService.save(subjectsRequest);
        return ResponseEntity.ok("업로드에 성공했습니다.");
    }
}
