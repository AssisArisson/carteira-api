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
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
