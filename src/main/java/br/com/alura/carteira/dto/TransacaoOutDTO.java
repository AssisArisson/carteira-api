package br.com.alura.carteira.dto;

import br.com.alura.carteira.modelo.TipoTransacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoOutDTO {

    private Long id;
    private String ticker;
    private BigDecimal preco;
    private Integer quantidade;
    private TipoTransacao tipo;
    private BigDecimal imposto;

}
