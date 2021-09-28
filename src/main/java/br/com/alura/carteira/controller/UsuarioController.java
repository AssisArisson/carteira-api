package br.com.alura.carteira.controller;


import br.com.alura.carteira.dto.TransacaoInDTO;
import br.com.alura.carteira.dto.TransacaoOutDTO;
import br.com.alura.carteira.dto.UsuarioInDTO;
import br.com.alura.carteira.dto.UsuarioOutDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioOutDTO> listar() {

        return usuarioService.listar();

    }

    @PostMapping
    public void cadastrar(@RequestBody @Valid UsuarioInDTO dto){
        usuarioService.cadastrar(dto);

    }

}
