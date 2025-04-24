package com.biruk.habeshaJobs.Controller;

import com.biruk.habeshaJobs.DTO.*;
import com.biruk.habeshaJobs.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin()
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register/jobseeker")
    public ResponseEntity<OutgoingJobSeekerDTO> registerJobSeeker(@ModelAttribute IncomingJobSeekerRegDTO jobSeekerRegDTO){

        //save the job seeker to the database and return the job seeker object to the client
        OutgoingJobSeekerDTO outgoingJobSeekerDTO = authService.registerJobSeeker(jobSeekerRegDTO);

        return ResponseEntity.ok(outgoingJobSeekerDTO);

    }

    @PostMapping("/register/employer")

    public ResponseEntity <OutgoingEmployerDTO> registerEmployer (@ModelAttribute IncomingEmployerRegDTO employerRegDTO) {

        OutgoingEmployerDTO outgoingEmployerDTO = authService.registerEmployer(employerRegDTO);

        return ResponseEntity.ok(outgoingEmployerDTO);

    }

    @PostMapping("/login")
    public ResponseEntity <?> login (@RequestBody IncomingUserLoginDTO userLoginDTO){
        try {
            Object userObj = authService.login(userLoginDTO);
            String token = null;
            if (userObj instanceof OutgoingJobSeekerDTO jobSeekerDTO){
                token = jobSeekerDTO.getToken();
            }else if (userObj instanceof OutgoingEmployerDTO employerDTO) {
                token = employerDTO.getToken();
            }
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(userObj);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }

}
