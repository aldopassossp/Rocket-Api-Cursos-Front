package com.aldopassos.cursos_front.service;

import com.aldopassos.cursos_front.dto.CursoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CursoService {

    @Value("${host.api.cursos}")
    private String hostApiCursos;

    private final RestTemplate restTemplate = new RestTemplate();

    public void execute(CursoDTO createCursoDTO){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CursoDTO> request = new HttpEntity<>(createCursoDTO, headers);

        String url = hostApiCursos.concat("/cursos");

        var result = rt.postForObject(url, request, String.class);
    }

    public List<CursoDTO> listarCursos(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<CursoDTO>> responseType = new ParameterizedTypeReference<List<CursoDTO>>() {

        };

        String url = hostApiCursos.concat("/cursos");
        var result = rt.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return result.getBody();
    }

    public String save(CursoDTO curso, String token){

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<CursoDTO> request = new HttpEntity<>(curso, headers);

        String url = hostApiCursos.concat("/cursos");
        var result = rt.postForObject(url, request, String.class);

        return result;
    }

    public CursoDTO obterCursoPorId(Long id, String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<CursoDTO> responseType = new ParameterizedTypeReference<CursoDTO>() {

        };

        String url = hostApiCursos + "/cursos/" + id;
        var result = rt.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return result.getBody();
    }

    public String atualizarCurso(CursoDTO curso, String token) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<CursoDTO> request = new HttpEntity<>(curso, headers);

        String url = hostApiCursos.concat("/cursos/" + curso.getId().toString());
        var result = rt.postForObject(url, request, String.class);

        return result;
    }

    public void excluirCurso(Long id, String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        String url = hostApiCursos + "/cursos/" + id;

        try {
            rt.exchange(url, HttpMethod.DELETE, httpEntity, Void.class);
        } catch (HttpClientErrorException e) {

            System.err.println("Erro ao excluir o curso: " + e.getMessage());
            throw e;
        }
    }


}
