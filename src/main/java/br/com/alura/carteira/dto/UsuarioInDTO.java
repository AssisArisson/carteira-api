package br.com.alura.carteira.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String login;

}
