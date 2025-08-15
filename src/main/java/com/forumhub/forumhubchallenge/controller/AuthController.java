package com.forumhub.forumhubchallenge.controller;

import com.forumhub.forumhubchallenge.config.TokenService;
import com.forumhub.forumhubchallenge.exception.LoginDuplicadoException;
import com.forumhub.forumhubchallenge.model.Usuario;
import com.forumhub.forumhubchallenge.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/debug/user/{login}")
    public ResponseEntity<?> debugUser(@PathVariable String login) {
        try {
            UserDetails user = usuarioService.loadUserByUsername(login);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {

            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.login(),
                            loginRequest.senha()
                    )
            );


            Usuario usuario = (Usuario) usuarioService.loadUserByUsername(loginRequest.login());


            String tokenJWT = tokenService.gerarToken(usuario);

            return ResponseEntity.ok(new TokenResponse(tokenJWT));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        } catch (LoginDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }


    }

    record LoginRequest(String login, String senha) {
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.criar(usuario);
            return ResponseEntity.ok().body(usuarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    record TokenResponse(String tokenJWT) {
    }

}