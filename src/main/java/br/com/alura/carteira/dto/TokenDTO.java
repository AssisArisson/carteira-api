package br.com.alura.carteira.dto;

import lombok.Getter;

@Getter
public class TokenDTO {


    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }


}
