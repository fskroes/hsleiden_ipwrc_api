package com.fskroes.ipwrc.dao;

import com.fskroes.ipwrc.model.EmployeeModel;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

public class EmployeeDAO extends AbstractDAO<EmployeeModel>{

    private final ArrayList<EmployeeModel> employeeCache;
    private SessionFactory sessionFactory;

    @Inject
    public EmployeeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
        employeeCache = new ArrayList<>();
    }

    public EmployeeModel findEmployeeByEmail(String email) {
        Optional<EmployeeModel> result = employeeCache.stream()
                .filter(employeeModel -> employeeModel.getEmail().equals(email))
                .findAny(); // todo is 'findany()' needed?
        return result.orElse(
                uniqueResult(
                        namedQuery("Employee.FIND_BY_EMAIL")
                                .setParameter("email", email)
                )
        );
    }
}
