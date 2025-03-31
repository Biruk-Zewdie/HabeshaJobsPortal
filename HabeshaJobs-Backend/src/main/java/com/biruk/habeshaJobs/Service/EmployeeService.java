package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.JobSeekerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final JobSeekerDAO jobSeekerDAO;

    @Autowired
    public EmployeeService (JobSeekerDAO jobSeekerDAO){
        this.jobSeekerDAO = jobSeekerDAO;
    }




}
