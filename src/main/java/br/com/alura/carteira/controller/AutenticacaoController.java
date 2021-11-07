package br.com.alura.carteira.controller;

import br.com.alura.carteira.dto.LoginInDTO;
import br.com.alura.carteira.dto.TokenDTO;
import br.com.alura.carteira.infra.security.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    public TokenDTO autenticar(@RequestBody @Valid LoginInDTO loginInDTO){

        return new TokenDTO (autenticacaoService.autenticar(loginInDTO));

    }

}
