package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeService (EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
    }





}
