package br.com.alura.carteira.controller;

import br.com.alura.carteira.dto.TransacaoInDTO;
import br.com.alura.carteira.dto.TransacaoOutDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.service.TransacaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public List<TransacaoOutDTO> listar(){
        return transacaoService.listar();

    }


    @PostMapping
    public void cadastrar(@RequestBody @Valid TransacaoInDTO dto){

        transacaoService.cadastrar(dto);

    }

}
