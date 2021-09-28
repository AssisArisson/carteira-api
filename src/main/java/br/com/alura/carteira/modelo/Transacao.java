package br.com.alura.carteira.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("preco")
    private BigDecimal preco;

    @JsonProperty("quantidade")
    private int quantidade;

    @JsonProperty("data")
    private LocalDate data;

    @JsonProperty("tipo")
    private TipoTransacao tipo;

}
