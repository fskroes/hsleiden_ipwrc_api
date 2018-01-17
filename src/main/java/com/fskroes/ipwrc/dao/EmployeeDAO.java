package com.fskroes.ipwrc.dao;

import com.fskroes.ipwrc.model.EmployeeModel;
import com.fskroes.ipwrc.model.ProductModel;
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


    public EmployeeModel findEmployeeById(int id) {
        Optional<EmployeeModel> result = employeeCache.stream()
                .filter(employeeModel -> employeeModel.getId() == id)
                .findAny(); // todo is 'findany()' needed?
        return result.orElse(
                uniqueResult(
                        namedQuery("Employee.FIND_BY_ID")
                                .setParameter("id", id)
                )
        );
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

    public EmployeeModel createEmployee(EmployeeModel employeeModel) {
        if (sessionFactory.getCurrentSession() != null) {
            EmployeeModel model = (EmployeeModel) sessionFactory.getCurrentSession().merge(employeeModel);
            return model;
        }
        return (EmployeeModel) sessionFactory.openSession().merge(employeeModel);
    }

    public EmployeeModel updateEmployee(EmployeeModel model) {
        if (sessionFactory.getCurrentSession() != null) {
            EmployeeModel newModel = (EmployeeModel) sessionFactory.getCurrentSession().merge(model);
            return newModel;
        }
        return (EmployeeModel) sessionFactory.openSession().merge(model);
    }

    public boolean deleteEmployee(int id) {
        EmployeeModel model = findEmployeeById(id);

        if (sessionFactory.getCurrentSession() != null) {
            sessionFactory.getCurrentSession().delete(model);
            return true;
        } else {
            return false;
        }
    }
}
