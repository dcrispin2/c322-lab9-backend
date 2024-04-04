package edu.iu.dcrispin.ducksservice.controllers;

import edu.iu.dcrispin.ducksservice.repository.CustomerRepository;
import edu.iu.dcrispin.ducksservice.model.Customer;
import edu.iu.dcrispin.ducksservice.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    CustomerRepository customerRepository;
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, CustomerRepository
                                            customerRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/signin")
    public String login(@RequestBody Customer customer){
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                customer.username(),
                                customer.password()));
        return tokenService.generateToken(authentication);
    }
    @PostMapping("/signup")
    public void signup(@RequestBody Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
