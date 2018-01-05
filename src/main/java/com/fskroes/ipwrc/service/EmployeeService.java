package com.fskroes.ipwrc.service;

import com.fskroes.ipwrc.dao.EmployeeDAO;

import javax.inject.Inject;

public class EmployeeService {

    private EmployeeDAO employeeDAO;

    @Inject
    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }


}
