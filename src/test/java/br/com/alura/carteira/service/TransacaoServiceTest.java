package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.TransacaoInDTO;
import br.com.alura.carteira.dto.TransacaoOutDTO;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TransacaoService service;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CalculadoraDeImpostoService calculadoraDeImpostoService;

    private Usuario logado;

    @BeforeEach
    public void before(){
        this.logado = new Usuario("fulano", "fulano@gmail.com", "123456");
    }

    private TransacaoInDTO criarTransacaoInDto(){
        TransacaoInDTO tranDTO = new TransacaoInDTO(
                "ITSA4",
                new BigDecimal("10.45"),
                120,
                TipoTransacao.COMPRA,
                LocalDate.now(),
                1l
        );
        return tranDTO;
    }

    @Test
    void deveriaCadastrarUmaTransacao(){
        TransacaoInDTO tranDTO = criarTransacaoInDto();

        Mockito.when(usuarioRepository
                .getById(tranDTO.getUsuarioId()))
                .thenReturn(logado);

        Transacao transacao = new Transacao(tranDTO.getTicker(),
                tranDTO.getPreco(),
                tranDTO.getQuantidade(),
                tranDTO.getData(),
                tranDTO.getTipo(),
                logado);

        Mockito.when(modelMapper.map(tranDTO, Transacao.class))
                .thenReturn(transacao);

        Mockito.when(modelMapper.map(transacao, TransacaoOutDTO.class))
                .thenReturn(new TransacaoOutDTO(
                        null,
                        transacao.getTicker(),
                        transacao.getPreco(),
                        transacao.getQuantidade(),
                        transacao.getTipo(),
                        BigDecimal.ZERO
                ));

        TransacaoOutDTO dto = service.cadastrar(tranDTO, logado);

        Mockito.verify(transacaoRepository).save(Mockito.any());

        assertEquals(tranDTO.getTicker(), dto.getTicker());
        assertEquals(tranDTO.getPreco(), dto.getPreco());
        assertEquals(tranDTO.getQuantidade(), dto.getQuantidade());
        assertEquals(tranDTO.getTipo(), dto.getTipo());

    }

    @Test
    void nãoDeveriaCadastrarUmaTransacaoComUsuarioInexistente(){
        TransacaoInDTO tranDTO = criarTransacaoInDto();

        Mockito
                .when(usuarioRepository.getById(tranDTO.getUsuarioId()))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(IllegalArgumentException.class ,() -> service.cadastrar(tranDTO, logado));

    }
}