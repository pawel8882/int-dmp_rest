package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.departmentsOnWork.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department getOneById(Integer id);
    Department save(Department department);
}
