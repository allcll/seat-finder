package kr.allcll.seatfinder.subject;

import org.springframework.data.jpa.domain.Specification;

public class SubjectSpecifications {

    public static Specification<Subject> hasSubjectId(Long subjectId) {
        return (root, query, builder) ->
            subjectId == null ? null : builder.equal(root.get("id"), subjectId);
    }

    public static Specification<Subject> hasSubjectName(String subjectName) {
        return (root, query, builder) ->
            subjectName == null ? null : builder.equal(root.get("subjectName"), subjectName);
    }

    public static Specification<Subject> hasSubjectCode(String subjectCode) {
        return (root, query, builder) ->
            subjectCode == null ? null : builder.equal(root.get("subjectCode"), subjectCode);
    }

    public static Specification<Subject> hasClassCode(String classCode) {
        return (root, query, builder) ->
            classCode == null ? null : builder.equal(root.get("classCode"), classCode);
    }

    public static Specification<Subject> hasProfessorName(String professorName) {
        return (root, query, builder) ->
            professorName == null ? null : builder.equal(root.get("professorName"), professorName);
    }
}
