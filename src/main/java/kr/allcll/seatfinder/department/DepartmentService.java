package kr.allcll.seatfinder.department;

import java.util.List;
import kr.allcll.seatfinder.department.dto.DepartmentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentsResponse retrieveAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        return DepartmentsResponse.from(departments);
    }
}
