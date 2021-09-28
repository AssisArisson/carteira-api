package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.TransacaoInDTO;
import br.com.alura.carteira.dto.TransacaoOutDTO;
import br.com.alura.carteira.modelo.Transacao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private List<Transacao> transacoes = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    public List<TransacaoOutDTO> listar(){

        return transacoes
                .stream()
                .map(t -> modelMapper.map(t , TransacaoOutDTO.class))
                .collect(Collectors.toList());
    }

  public void cadastrar(TransacaoInDTO dto){

      Transacao transacao = modelMapper.map(dto, Transacao.class);

      transacoes.add(transacao);
  }
}
