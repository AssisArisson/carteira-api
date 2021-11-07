package br.com.alura.carteira.infra.security;


import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class VericacaoTokenFilter extends OncePerRequestFilter {


    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;

    public VericacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository =usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()){
            filterChain.doFilter(request, response);

            return;
        }

        token = token.replace("Bearer", "");

        boolean tokenvalido = tokenService.isVaid(token);
        if (tokenvalido){
            Long idUsuario = tokenService.extrairIdUsuario(token);
            Usuario logado = usuarioRepository.carregarPorIdComPerfis(idUsuario).get();
            Authentication authentication = new UsernamePasswordAuthenticationToken(logado, null, logado.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

}
