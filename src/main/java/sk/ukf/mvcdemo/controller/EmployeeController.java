package sk.ukf.mvcdemo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import sk.ukf.mvcdemo.entity.Employee;
import sk.ukf.mvcdemo.exception.EmailAlreadyExistsException;
import sk.ukf.mvcdemo.service.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Value("${job.titles}")
    private List<String> jobTitles;

    @Value("${employment.types}")
    private List<String> employmentTypes;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ✅ Orezávanie prázdnych stringov
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    // ✅ Zoznam
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    // ✅ Detail
    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/view";
    }

    // ✅ Formulár - nový zamestnanec
    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("employee", new Employee()); // ✅ id bude null
        model.addAttribute("jobTitles", jobTitles);
        model.addAttribute("employmentTypes", employmentTypes);

        return "employees/form";
    }

    // ✅ CREATE + UPDATE + VALIDÁCIA
    @PostMapping
    public String saveEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("jobTitles", jobTitles);
            model.addAttribute("employmentTypes", employmentTypes);
            return "employees/form";
        }

        try {
            employeeService.save(employee);
            return "redirect:/employees";

        } catch (EmailAlreadyExistsException ex) {

            bindingResult.rejectValue("email", "email.exists", ex.getMessage());

            model.addAttribute("jobTitles", jobTitles);
            model.addAttribute("employmentTypes", employmentTypes);

            return "employees/form";
        }
    }

    // ✅ Formulár na update
    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {

        Employee employee = employeeService.findById(id);

        model.addAttribute("employee", employee);
        model.addAttribute("jobTitles", jobTitles);
        model.addAttribute("employmentTypes", employmentTypes);

        return "employees/form";
    }

    // ✅ Delete
    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
