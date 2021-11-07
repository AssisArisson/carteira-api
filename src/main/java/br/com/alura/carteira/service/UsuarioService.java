package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.UsuarioInDTO;
import br.com.alura.carteira.dto.UsuarioOutDTO;
import br.com.alura.carteira.modelo.Perfil;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.PerfilRepository;
import br.com.alura.carteira.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Page<UsuarioOutDTO> listar(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return usuarios
                .map(t -> modelMapper.map(t , UsuarioOutDTO.class));
    }

    @Transactional
    public UsuarioOutDTO cadastrar(UsuarioInDTO dto) {

        Usuario usuario = modelMapper.map(dto, Usuario.class);

        Perfil perfil = perfilRepository.getById(dto.getPerfilId());
        usuario.adicionarPerfil(perfil);

        String senha = new Random().nextInt(999999) + "";
        usuario.setSenha(bCryptPasswordEncoder.encode(senha));

        usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioOutDTO.class);
    }
}
