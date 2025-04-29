package com.biruk.habeshaJobs.Controller;

import com.biruk.habeshaJobs.DTO.OutgoingEmployerDTO;
import com.biruk.habeshaJobs.Service.EmployerService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employers")
@CrossOrigin
public class EmployerController {

    private final EmployerService employerService;

    @Autowired
    public EmployerController (EmployerService employerService){
        this.employerService = employerService;
    }

    @GetMapping
    public ResponseEntity<List<OutgoingEmployerDTO>> getAllEmployers () {

        List<OutgoingEmployerDTO> outgoingEmployerDTOS = employerService.getAllEmployers();

        return ResponseEntity.ok(outgoingEmployerDTOS);
    }

    @GetMapping("/{employerId}")
    public ResponseEntity<OutgoingEmployerDTO> getEmployerById (@PathVariable UUID employerId){

        OutgoingEmployerDTO outgoingEmployerDTO = employerService.getEmployerById(employerId);

        return ResponseEntity.ok(outgoingEmployerDTO);
    }

    @DeleteMapping("/{employerId}")
    public ResponseEntity<String> deleteEmployer (@PathVariable UUID employerId){

        employerService.deleteEmployer(employerId);

        return ResponseEntity.ok("Employer successfully deleted!");
    }

    @PatchMapping("/{employerId}")
    public ResponseEntity<OutgoingEmployerDTO> updateEmployerProfile (@PathVariable UUID employerId, @RequestBody JsonNode patchNode) {

        OutgoingEmployerDTO outgoingEmployerDTO = employerService.updateEmployer(employerId, patchNode);

        return ResponseEntity.ok(outgoingEmployerDTO);
    }
}
