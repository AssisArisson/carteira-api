package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.ItemCarteiraDTO;
import br.com.alura.carteira.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private TransacaoRepository repository;

    public List<ItemCarteiraDTO> relatorioCarteiraDeInvestimentos() {

        return  repository.relatorioCarteiraDeInvestimentos();

    }
}
