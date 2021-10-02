package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.UsuarioInDTO;
import br.com.alura.carteira.dto.UsuarioOutDTO;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public List<UsuarioOutDTO> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios
                .stream()
                .map(t -> modelMapper.map(t , UsuarioOutDTO.class))
                .collect(Collectors.toList());
    }

    public void cadastrar(UsuarioInDTO dto) {

        Usuario usuario = modelMapper.map(dto, Usuario.class);

        String senha = new Random().nextInt(999999) + "";
        usuario.setSenha(senha);

        usuarioRepository.save(usuario);
    }
}
