package com.fskroes.ipwrc.route;

import com.fskroes.ipwrc.model.EmployeeModel;
import com.fskroes.ipwrc.model.ProductModel;
import com.fskroes.ipwrc.service.EmployeeService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
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

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/new")
    public EmployeeModel createEmployee(EmployeeModel employeeModel) {
        return this.employeeService.createEmployee(employeeModel);
    }

    @PUT
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/edit/{id}")
    public EmployeeModel insertEmployee(@Auth @PathParam("id") int id, EmployeeModel model) {
        return this.employeeService.updateEmployee(id, model);
    }

    @DELETE
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public boolean deleteEmployee(@Auth @PathParam("id") int id) {
        return this.employeeService.deleteEmployee(id);
    }
}
