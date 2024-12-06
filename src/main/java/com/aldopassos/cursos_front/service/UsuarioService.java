package com.aldopassos.cursos_front.service;

import com.aldopassos.cursos_front.dto.Token;
import com.aldopassos.cursos_front.dto.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Value("${host.api.cursos}")
    private String hostApiCursos;

    private final RestTemplate restTemplate = new RestTemplate();

    public Token login(String login, String senha) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("senha", senha);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

        String url = hostApiCursos.concat("/login");

        ResponseEntity<String> response = rt.postForEntity(url, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Token token = objectMapper.readValue(response.getBody(), Token.class);
            if (token == null || token.getRole() == null) {
                throw new IllegalStateException("O token ou a role estão nulos!");
            }
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UsuarioDTO> listarUsuarios(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<UsuarioDTO>> responseType = new ParameterizedTypeReference<List<UsuarioDTO>>() {

        };

        String url = hostApiCursos.concat("/usuarios");
        var result = rt.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return result.getBody();
    }

    public String save(UsuarioDTO usuario, String token){

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuario, headers);

        String url = hostApiCursos.concat("/usuarios");
        var result = rt.postForObject(url, request, String.class);

        return result;
    }

    public UsuarioDTO obterUsuarioPorId(Long id, String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<UsuarioDTO> responseType = new ParameterizedTypeReference<UsuarioDTO>() {

        };

        String url = hostApiCursos + "/usuarios/" + id;
        var result = rt.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return result.getBody();
    }

    public String atualizarUsuario(UsuarioDTO usuario, String token) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuario, headers);

        String url = hostApiCursos.concat("/usuarios/" + usuario.getId().toString());
        var result = rt.postForObject(url, request, String.class);

        return result;
    }

    public void excluirUsuario(Long id, String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        String url = hostApiCursos + "/usuarios/" + id;

        try {
            rt.exchange(url, HttpMethod.DELETE, httpEntity, Void.class);
        } catch (HttpClientErrorException e) {

            System.err.println("Erro ao excluir o usuário: " + e.getMessage());
            throw e;
        }
    }

}
