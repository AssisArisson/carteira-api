package br.com.alura.carteira.dto;

import br.com.alura.carteira.modelo.TipoTransacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class TransacaoOutDTO {

    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("preco")
    private BigDecimal preco;

    @JsonProperty("quantidade")
    private int quantidade;

    @JsonProperty("tipo")
    private TipoTransacao tipo;

}
