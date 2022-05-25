package br.com.site.forum.controller;

import br.com.site.forum.config.security.TokenService;
import br.com.site.forum.dto.LoginForm;
import br.com.site.forum.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
@Profile(value = {"prod","test"})
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService token;

    @PostMapping
    public ResponseEntity<TokenDto> logar(@RequestBody @Valid LoginForm form) {

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        try {
            Authentication authenticate = authManager.authenticate(dadosLogin);
            String tok = token.gerarToken(authenticate);

            return ResponseEntity.ok(new TokenDto(tok, "Bearer"));
        } catch (AuthenticationException e){

            return ResponseEntity.badRequest().build();
        }

    }
}
