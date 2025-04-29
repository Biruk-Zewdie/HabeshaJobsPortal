package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.EmployerDAO;
import com.biruk.habeshaJobs.DTO.OutgoingEmployerDTO;
import com.biruk.habeshaJobs.Model.Employer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EmployerService {

    private final EmployerDAO employerDAO;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmployerService (EmployerDAO employerDAO, ObjectMapper objectMapper){
        this.employerDAO = employerDAO;
        this.objectMapper = objectMapper;
    }

    public List <OutgoingEmployerDTO> getAllEmployers () {

        List <Employer> employers = employerDAO.findAll();

        if (employers.isEmpty()){
            throw new NoSuchElementException("No employer found");
        }

        List <OutgoingEmployerDTO> outgoingEmployerDTOs = new ArrayList<>();

        for (Employer employer : employers){
            OutgoingEmployerDTO outgoingEmployerDTO = new OutgoingEmployerDTO(employer);
            outgoingEmployerDTOs.add(outgoingEmployerDTO);
        }

        return outgoingEmployerDTOs;
    }

    public OutgoingEmployerDTO getEmployerById (UUID employerId) {

        Employer employer = employerDAO.findById(employerId).orElseThrow(() ->
                new NoSuchElementException("The employer with employer Id " + employerId + " not found"));

        return new OutgoingEmployerDTO(employer);

    }

    public void deleteEmployer (UUID employerId) {

        if (!employerDAO.existsById(employerId)){
            throw new NoSuchElementException("Employer with Id " + employerId + " not found");
        }

        employerDAO.deleteById(employerId);
    }

    public OutgoingEmployerDTO updateEmployer (UUID employerId, JsonNode patchNode) {

        Employer employer = employerDAO.findById (employerId).orElseThrow(() ->
                new NoSuchElementException("Employer with employer Id " + employerId + " not found"));

        try {
            objectMapper.readerForUpdating(employer).readValue(patchNode); //patchNode is the incoming JSON body (request body) from the client that only contains the fields the user wants to update.

            Employer updatedEmployer = employerDAO.save(employer); //-> @PreUpdate is triggered, and updates the timestamp

            return new OutgoingEmployerDTO(updatedEmployer);
        }catch (Exception e){
            throw new RuntimeException("Failed to apply patch: " + e.getMessage());
        }
    }
}
