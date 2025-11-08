package sk.ukf.mvcdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Meno
    @NotBlank(message = "Meno nesmie byť prázdne.")
    @Size(min = 2, max = 64, message = "Meno musí mať 2–64 znakov.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // ✅ Priezvisko
    @NotBlank(message = "Priezvisko nesmie byť prázdne.")
    @Size(min = 2, max = 64, message = "Priezvisko musí mať 2–64 znakov.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // ✅ Dátum narodenia
    @NotNull(message = "Dátum narodenia nesmie byť prázdny.")
    @Past(message = "Dátum narodenia musí byť v minulosti.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    // ✅ Email s vlastným REGEX
    @NotBlank(message = "Email nesmie byť prázdny.")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email musí byť vo formáte meno@firma.sk."
    )
    @Size(max = 255, message = "Email môže mať najviac 255 znakov.")
    @Column(nullable = false)
    private String email;

    // ✅ Telefón
    @NotBlank(message = "Telefón nesmie byť prázdny.")
    @Pattern(
            regexp = "^\\+421[0-9]{9}$",
            message = "Telefón musí byť vo formáte +421XXXXXXXXX."
    )
    @Column
    private String phone;

    // ✅ Pozícia — musí byť vybraná
    @NotBlank(message = "Pozícia musí byť vybraná.")
    @Column(name = "job_title")
    private String jobTitle;

    // ✅ Plat
    @Min(value = 0, message = "Plat nesmie byť záporný.")
    @Column
    private Double salary;

    // ✅ TU JE HLAVNÁ OPRAVA – full_time JE INTEGER
    // databáza nepustí String, preto Integer 0/1
    @NotNull(message = "Úväzok musí byť vybraný.")
    @Min(0)
    @Max(1)
    @Column(name = "full_time")
    private Integer fullTime;

    public Employee() {}

    // ✅ GETTRE / SETTRE

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getFullTime() {
        return fullTime;
    }
    public void setFullTime(Integer fullTime) {
        this.fullTime = fullTime;
    }
}
