package br.com.alura.carteira.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginInDTO {

    @NotBlank
    private String login;
    @NotBlank
    private  String senha;

}
