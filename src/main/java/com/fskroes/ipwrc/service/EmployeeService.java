package com.fskroes.ipwrc.service;

import com.fskroes.ipwrc.dao.EmployeeDAO;
import com.fskroes.ipwrc.model.EmployeeModel;
import com.fskroes.ipwrc.model.ProductModel;

import javax.inject.Inject;

public class EmployeeService {

    private EmployeeDAO employeeDAO;

    @Inject
    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }


    public EmployeeModel createEmployee(EmployeeModel employeeModel) {
        return employeeDAO.createEmployee(employeeModel);
    }

    public EmployeeModel updateEmployee(int id, EmployeeModel model) {
        model.setId(id);
        return employeeDAO.updateEmployee(model);
    }

    public boolean deleteEmployee(int id) {
        return employeeDAO.deleteEmployee(id);
    }
}