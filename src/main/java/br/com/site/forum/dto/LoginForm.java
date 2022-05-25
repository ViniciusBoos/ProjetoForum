package br.com.site.forum.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

    private String email;
    private String senha;

    public UsernamePasswordAuthenticationToken converter() {

        return new UsernamePasswordAuthenticationToken(this.email, this.senha);
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
