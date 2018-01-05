package com.fskroes.ipwrc.service;

import com.fskroes.ipwrc.dao.EmployeeDAO;
import com.fskroes.ipwrc.model.EmployeeModel;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import java.util.Optional;

public class UserAuthenticationService implements Authenticator<BasicCredentials, EmployeeModel>, Authorizer<EmployeeModel> {

    private final EmployeeDAO employeeDAO;

    @Inject
    public UserAuthenticationService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    @UnitOfWork
    public Optional<EmployeeModel> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        EmployeeModel employeeModel = employeeDAO.findEmployeeByEmail(basicCredentials.getUsername());
        if (employeeModel != null && employeeModel.getPassword().equals(basicCredentials.getPassword())) {
            System.out.println("sign in succes!");
            return Optional.of(employeeModel);
        }
        return Optional.empty();
    }

    @Override
    public boolean authorize(EmployeeModel employeeModel, String role) {
        return employeeModel.hasRole(role);
    }
}
