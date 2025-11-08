package sk.ukf.mvcdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.ukf.mvcdemo.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);
}


