package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.AtualizarTransacaoInDTO;
import br.com.alura.carteira.dto.DetalhesTransacaoOutDTO;
import br.com.alura.carteira.dto.TransacaoInDTO;
import br.com.alura.carteira.dto.TransacaoOutDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.parser.Entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CalculadoraDeImpostoService calcularImposto;

    public Page<TransacaoOutDTO> listar(Pageable paginacao, Usuario usuario){
        return transacaoRepository.findAllByUsuario(paginacao, usuario)
                .map(t -> modelMapper.map(t, TransacaoOutDTO.class));

/*        List<TransacaoOutDTO> transacoesDto = new ArrayList<>();
        transacoes.forEach(transacao -> {
            BigDecimal imposto = calcularImposto.calcular(transacao);
            TransacaoOutDTO dto = modelMapper.map(transacao, TransacaoOutDTO.class);
            dto.setImposto(imposto);
            transacoesDto.add(dto);
        });
        return new PageImpl<TransacaoOutDTO>(transacoesDto, transacoes.getPageable(), transacoes.getTotalElements());*/
    }

  @Transactional
  public TransacaoOutDTO cadastrar(TransacaoInDTO dto, Usuario logado){
      Long idUsuario = dto.getUsuarioId();

      try{
          Usuario usuario = usuarioRepository.getById(idUsuario);
          if (!usuario.equals(logado)){
              lancarErroAcessoNegado();
          }

          Transacao transacao = modelMapper.map(dto, Transacao.class);

          transacao.setId(null);
          transacao.setUsuario(usuario);
          BigDecimal imposto = calcularImposto.calcular(transacao);
          transacao.setImposto(imposto);

          transacaoRepository.save(transacao);

          return modelMapper.map(transacao, TransacaoOutDTO.class);

      }catch (EntityNotFoundException e){
          throw new IllegalArgumentException("usuario inexistente");
      }

  }

    @Transactional
    public TransacaoOutDTO atualizar(AtualizarTransacaoInDTO dto, Usuario logado) {
        Transacao transacao = transacaoRepository.getById(dto.getId());

        if (!transacao.pertenceAoUsuario(logado)){
            lancarErroAcessoNegado();
        }

        transacao.atualizarInformacoes(dto.getTicker(), dto.getData(), dto.getPreco(), dto.getQuantidade(), dto.getTipo());

        return modelMapper.map(transacao, TransacaoOutDTO.class);
    }

    @Transactional
    public void remover(Long id, Usuario logado) {
        Transacao transacao = transacaoRepository.getById(id);
        if (!transacao.pertenceAoUsuario(logado)){
            lancarErroAcessoNegado();
        }

        transacaoRepository.deleteById(id);
    }

    @Transactional
    public DetalhesTransacaoOutDTO detalahar(Long id, Usuario logado) {

        Transacao transacao = transacaoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        if (!transacao.pertenceAoUsuario(logado)){
            lancarErroAcessoNegado();
        }

        return modelMapper.map(transacao, DetalhesTransacaoOutDTO.class);
    }

    private void lancarErroAcessoNegado(){
        throw new AccessDeniedException("Acesso Negado!");
    }

}
