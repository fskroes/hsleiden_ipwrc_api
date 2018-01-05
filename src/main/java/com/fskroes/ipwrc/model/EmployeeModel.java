package com.fskroes.ipwrc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.security.auth.Subject;
import java.security.Principal;

@Entity
@Table(name = "employee")
@NamedNativeQuery(
        name = "Employee.FIND_BY_EMAIL",
        query = "SELECT * FROM employee WHERE email = :email",
        resultClass = EmployeeModel.class
)
public class EmployeeModel implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private long id;

    private String email;
    private String password;
    private String name;
    private String role;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public boolean hasRole(String role) {
        return this.role.equals(role);
    }

    @Override
    @JsonIgnore
    public String getName() {
        return null;
    }
}
