package com.example.proiectpos.controllers;

import com.example.proiectpos.configs.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical_office/authentication")
@CrossOrigin
public class AuthenticationController {
    public final GrpcClientService grpcClientService;

    @Autowired
    public AuthenticationController(GrpcClientService grpcClientService) {
        this.grpcClientService = grpcClientService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserCredentials userCredentials){

        String reply = grpcClientService.registerUser(userCredentials.getUsername(), userCredentials.getPassword());
        if(userCredentials.getUsername() != null && userCredentials.getPassword() != null){
            if(reply.equals(userCredentials.getUsername())){
                return new ResponseEntity<>("success", HttpStatus.OK);
            }
        } return new ResponseEntity<>("failed",HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials userCredentials){

        if(userCredentials.getUsername() != null && userCredentials.getPassword() != null){
            String token = grpcClientService.getToken(userCredentials.getUsername(), userCredentials.getPassword());
            if(token != null){
                return new ResponseEntity<>(token, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("failed",HttpStatus.FORBIDDEN);
            }
        }
        else return new ResponseEntity<>("failed",HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token){

        if(token != null){
            String replyMessage = grpcClientService.destroyToken(token);
            if(replyMessage.equals("destroyed")) {
                return new ResponseEntity<>("destroyed", HttpStatus.OK);
            }
            else return new ResponseEntity<>("could not destroy token", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else return new ResponseEntity<>("failed",HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/role")
    public ResponseEntity<String> getRole(@RequestHeader("Authorization") String token){

        if(token != null){
            return new ResponseEntity<>(grpcClientService.getUserRole(token),HttpStatus.OK);
        }
        else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }

    @GetMapping("/id")
    public ResponseEntity<String> getId(@RequestHeader("Authorization") String token){

        if(token != null){
            return new ResponseEntity<>(grpcClientService.getId(token),HttpStatus.OK);
        }
        else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    static class UserCredentials {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
    }

}
