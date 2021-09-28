package br.com.alura.carteira.dto;

import br.com.alura.carteira.modelo.TipoTransacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
public class TransacaoInDTO {

    @NotNull
    @NotEmpty
    @Size(min=5, max=6)
    @JsonProperty("ticker")
    private String ticker;

    @NotNull
    @DecimalMin("0.01")
    @JsonProperty("preco")
    private BigDecimal preco;

    @NotNull
    @JsonProperty("quantidade")
    private int quantidade;

    @NotNull
    @JsonProperty("tipo")
    private TipoTransacao tipo;

    @PastOrPresent
    @JsonProperty("data")
    private LocalDate data;

}
