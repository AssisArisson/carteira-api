package br.com.alura.carteira.repository;


import br.com.alura.carteira.dto.ItemCarteiraDTO;
import br.com.alura.carteira.dto.TransacaoOutDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("select new br.com.alura.carteira.dto.ItemCarteiraDTO("
            + "t.ticker, "
            + "sum(case when(t.tipo = 'COMPRA') then t.quantidade else(t.quantidade * -1)end), "
            + "(select sum(case when(t2.tipo = 'COMPRA') then t2.quantidade else(t2.quantidade * -1)end) from Transacao t2)) "
            + "from Transacao t "
            + "group by t.ticker")
    List<ItemCarteiraDTO> relatorioCarteiraDeInvestimentos();


    Page<Transacao> findAllByUsuario(Pageable paginacao, Usuario usuario);
}
