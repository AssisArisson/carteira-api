package br.com.alura.carteira.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private BigDecimal preco;
    private Integer quantidade;
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Transacao(String ticker, BigDecimal preco, Integer quantidade, LocalDate data, TipoTransacao tipo, Usuario usuario) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public void atualizarInformacoes(String ticker, LocalDate data, BigDecimal preco, int quantidade, TipoTransacao tipo) {
        this.ticker = ticker;
        this.data = data;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }
}
