package com.fskroes.ipwrc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.security.Principal;

@Entity
@Table(name = "employee")

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "Employee.FIND_BY_EMAIL",
            query = "SELECT * FROM employee WHERE email = :email",
            resultClass = EmployeeModel.class
    ),
    @NamedNativeQuery(
            name = "Employee.FIND_BY_ID",
            query = "SELECT * FROM employee WHERE id = :id",
            resultClass = EmployeeModel.class
    )
})
public class EmployeeModel implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
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

    public String getRole() {
        return role;
    }

    public boolean hasRole(String role) {
        return this.role.equals(role);
    }

    public String getName() {
        return this.name;
    }

    public void setId(long id) {
        this.id = id;
    }
}
