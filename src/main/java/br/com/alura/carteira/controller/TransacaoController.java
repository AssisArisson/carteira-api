package br.com.alura.carteira.controller;

import br.com.alura.carteira.dto.AtualizarTransacaoInDTO;
import br.com.alura.carteira.dto.DetalhesTransacaoOutDTO;
import br.com.alura.carteira.dto.TransacaoInDTO;
import br.com.alura.carteira.dto.TransacaoOutDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.service.TransacaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public Page<TransacaoOutDTO> listar(@PageableDefault(size = 10) Pageable paginacao, @ApiIgnore @AuthenticationPrincipal Usuario logado) {
        return transacaoService.listar(paginacao, logado);

    }

    @PostMapping
    public ResponseEntity<TransacaoOutDTO> cadastrar(@RequestBody @Valid TransacaoInDTO dto,
                                                     UriComponentsBuilder uriBuilder, @ApiIgnore @AuthenticationPrincipal Usuario logado) {

        TransacaoOutDTO transacaoDTO = transacaoService.cadastrar(dto, logado);

        URI uri = uriBuilder.path("/transacoes/{id}")
                .buildAndExpand(transacaoDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(transacaoDTO);

    }

    @PutMapping
    public ResponseEntity<TransacaoOutDTO> atualizar(@RequestBody @Valid AtualizarTransacaoInDTO dto, @ApiIgnore @AuthenticationPrincipal Usuario logado) {

        TransacaoOutDTO atualizada = transacaoService.atualizar(dto, logado);

        return ResponseEntity.ok(atualizada);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransacaoOutDTO> remover(@PathVariable @NotNull Long id, @ApiIgnore @AuthenticationPrincipal Usuario logado) {

        transacaoService.remover(id, logado);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTransacaoOutDTO> detalhar(@PathVariable @NotNull Long id, @ApiIgnore @AuthenticationPrincipal Usuario logado) {

        DetalhesTransacaoOutDTO dto = transacaoService.detalahar(id, logado);

        return ResponseEntity.ok(dto);

    }
}
