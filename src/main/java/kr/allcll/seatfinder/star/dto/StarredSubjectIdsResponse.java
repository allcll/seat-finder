package kr.allcll.seatfinder.star.dto;

import java.util.List;

public record StarredSubjectIdsResponse(
    List<StarredSubjectIdResponse> subjects
) {

}
