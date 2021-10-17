package br.com.alura.carteira.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DetalhesTransacaoOutDTO extends TransacaoOutDTO {

    private LocalDate data;
    private UsuarioOutDTO usuario;

}
