package sk.ukf.mvcdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.mvcdemo.dao.EmployeeRepository;
import sk.ukf.mvcdemo.entity.Employee;
import sk.ukf.mvcdemo.exception.EmailAlreadyExistsException;
import sk.ukf.mvcdemo.exception.ObjectNotFoundException;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Zamestnanec s ID " + id + " neexistuje.")
                );
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {

        // ✅ CREATE – nový zamestnanec
        if (employee.getId() == null) {

            if (employeeRepository.existsByEmail(employee.getEmail())) {
                throw new EmailAlreadyExistsException(employee.getEmail());
            }
        }

        // ✅ UPDATE – existujúci zamestnanec
        else {

            Employee existing = employeeRepository.findByEmail(employee.getEmail()).orElse(null);

            // niekto iný má rovnaký e-mail → chyba
            if (existing != null && !existing.getId().equals(employee.getId())) {
                throw new EmailAlreadyExistsException(employee.getEmail());
            }
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new ObjectNotFoundException("Zamestnanec s ID " + id + " neexistuje.");
        }

        employeeRepository.deleteById(id);
    }
}
