package com.fskroes.ipwrc.route;

import com.fskroes.ipwrc.model.EmployeeModel;
import com.fskroes.ipwrc.model.ProductModel;
import com.fskroes.ipwrc.service.EmployeeService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeRoute {

    private EmployeeService employeeService;

    @Inject
    public EmployeeRoute(EmployeeService service) {
        this.employeeService = service;
    }

    @GET
    @UnitOfWork
    @Path("/login")
    public EmployeeModel authenticate(@Auth EmployeeModel employeeModel) {
        return employeeModel;
    }
}
