package br.com.gomes.bankconta.components;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UsuarioLogado {

    public static String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se a autenticação está presente
        if (authentication != null) {
            return ((String) authentication.getPrincipal());
        } else {
            return "";
        }
    }
}
