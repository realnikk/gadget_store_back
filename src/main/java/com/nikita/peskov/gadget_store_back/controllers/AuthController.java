package com.nikita.peskov.gadget_store_back.controllers;

import com.nikita.peskov.gadget_store_back.exceptions.AccountAlreadyExistsException;
import com.nikita.peskov.gadget_store_back.models.Account;
import com.nikita.peskov.gadget_store_back.requests.LoginRequest;
import com.nikita.peskov.gadget_store_back.responses.JwtResponse;
import com.nikita.peskov.gadget_store_back.security.account.AccountDetails;
import com.nikita.peskov.gadget_store_back.security.jwt.JwtUtils;
import com.nikita.peskov.gadget_store_back.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerAccount(@RequestBody Account account){
        try{
            accountService.registerAccount(account);
            return ResponseEntity.ok("Registration successful!");

        }catch (AccountAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request){
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        List<String> roles = accountDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(new JwtResponse(
                accountDetails.getId(),
                accountDetails.getEmail(),
                jwt,
                roles));
    }
}
