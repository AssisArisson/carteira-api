package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.TransacaoInDTO;
import br.com.alura.carteira.dto.TransacaoOutDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.repository.TransacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public Page<TransacaoOutDTO> listar(Pageable paginacao){
        Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
        return transacoes.map(t -> modelMapper.map(t , TransacaoOutDTO.class));
    }

  @Transactional
  public TransacaoOutDTO cadastrar(TransacaoInDTO dto){

      Transacao transacao = modelMapper.map(dto, Transacao.class);

      transacao.setId(null);

      transacaoRepository.save(transacao);

      return modelMapper.map(transacao, TransacaoOutDTO.class);
  }
}
