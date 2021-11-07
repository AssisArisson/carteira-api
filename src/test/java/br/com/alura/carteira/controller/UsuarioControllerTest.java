package br.com.alura.carteira.controller;

import br.com.alura.carteira.infra.security.TokenService;
import br.com.alura.carteira.modelo.Perfil;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.PerfilRepository;
import br.com.alura.carteira.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String token;

    @BeforeEach
    public void gerarToken(){
        Usuario logado = new Usuario("fulano","fulano","123456");
        Perfil admin =  perfilRepository.findById(1l).get();
        logado.adicionarPerfil(admin);
        usuarioRepository.save(logado);

        Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
        this.token = tokenService.gerarToken(authentication);
    }

    @Test
    void naoDeveriaCadastrarUsuarioCopmDadosImcompletos() throws Exception {
        String json = "{}";

        mvc
                .perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization","Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void DeveriaCadastrarUsuarioCopmDadosCompletos() throws Exception {
        String json = "{\"nome\":\"fulano\", \"login\":\"fulano@gmail.com\", \"perfilId\":1}";
        String jsonEsperado = "{\"nome\":\"fulano\", \"login\":\"fulano@gmail.com\"}";

        mvc
                .perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization","Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(MockMvcResultMatchers.content().json(jsonEsperado));
    }

}