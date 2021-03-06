package br.com.alura.carteira.service;

import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraDeImpostoServiceTest {

    private CalculadoraDeImpostoService calculadora;

    private Transacao criarTransacao(BigDecimal preco, Integer quantidade, TipoTransacao tipo){
        Transacao transacao = new Transacao(
                120l,
                "BBSE3",
                preco,
                quantidade,
                LocalDate.now(),
                tipo,
                new Usuario(1l, "Rafaela", "rafa@gmail", "123456", "rafa@gmail", null),
                BigDecimal.ZERO
        );
        return transacao;
    }

    @BeforeEach
    public void inicializar(){
        calculadora = new CalculadoraDeImpostoService();
    }

    @Test
    void transacaoDoTipoCompraNaoDeveriaTerImposto(){
        Transacao transacao = criarTransacao(new BigDecimal("30.00"), 10, TipoTransacao.COMPRA);

        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(BigDecimal.ZERO, imposto);

    }

    @Test
    void transacaoDoTipoVendaComValorMenorQueVinteMilNaoDeveriaTerImposto(){
        Transacao transacao = criarTransacao(new BigDecimal("30.00"), 10, TipoTransacao.VENDA);

        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(BigDecimal.ZERO, imposto);

    }

    @Test
    void deveriaCalcularImpostoDeTransacaoDoTipoVendaComValorMaiorQueVinteMil(){
        Transacao transacao = criarTransacao(new BigDecimal("1000.00"), 30, TipoTransacao.VENDA);

        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(new BigDecimal("4500.00"), imposto);

    }

}