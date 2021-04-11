package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.projects.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findById(Long id);
    Project getOneById(Integer id);
    Project save(Project project);
    void deleteById(Integer projectId);
    List<Project> findAll();


}
