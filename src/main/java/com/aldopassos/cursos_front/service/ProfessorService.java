package com.aldopassos.cursos_front.service;

import com.aldopassos.cursos_front.dto.ProfessorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProfessorService {

    @Value("${host.api.cursos}")
    private String hostApiCursos;

    private final RestTemplate restTemplate = new RestTemplate();

    public void execute(ProfessorDTO createProfessorDTO){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProfessorDTO> request = new HttpEntity<>(createProfessorDTO, headers);

        String url = hostApiCursos.concat("/professores");

        var result = rt.postForObject(url, request, String.class);
    }

    public List<ProfessorDTO> listarProfessores(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<ProfessorDTO>> responseType = new ParameterizedTypeReference<List<ProfessorDTO>>() {

        };

        String url = hostApiCursos.concat("/professores");
        var result = rt.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return result.getBody();
    }

    public String save(ProfessorDTO professor, String token){

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<ProfessorDTO> request = new HttpEntity<>(professor, headers);

        String url = hostApiCursos.concat("/professores");
        var result = rt.postForObject(url, request, String.class);

        return result;
    }

    public ProfessorDTO obterProfessorPorId(Long id, String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<ProfessorDTO> responseType = new ParameterizedTypeReference<ProfessorDTO>() {

        };

        String url = hostApiCursos + "/professores/" + id;
        var result = rt.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return result.getBody();
    }

    public String atualizarProfessor(ProfessorDTO professor, String token) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<ProfessorDTO> request = new HttpEntity<>(professor, headers);

        String url = hostApiCursos.concat("/professores/" + professor.getId().toString());
        var result = rt.postForObject(url, request, String.class);

        return result;
    }

    public void excluirProfessor(Long id, String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        String url = hostApiCursos + "/professores/" + id;

        try {
            rt.exchange(url, HttpMethod.DELETE, httpEntity, Void.class);
        } catch (HttpClientErrorException e) {

            System.err.println("Erro ao excluir o curso: " + e.getMessage());
            throw e;
        }
    }

}
