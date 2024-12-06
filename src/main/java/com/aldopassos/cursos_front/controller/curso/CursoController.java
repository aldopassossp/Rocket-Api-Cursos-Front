package com.aldopassos.cursos_front.controller.curso;

import com.aldopassos.cursos_front.dto.CursoDTO;
import com.aldopassos.cursos_front.dto.CursoInsertDTO;
import com.aldopassos.cursos_front.dto.ProfessorDTO;
import com.aldopassos.cursos_front.service.CursoService;
import com.aldopassos.cursos_front.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/listagem")
    @PreAuthorize("hasRole('USER')")
    public String list(Model model){
        var result = this.cursoService.listarCursos(getToken());
        model.addAttribute("cursos", result);
        return "curso/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<ProfessorDTO> professores = professorService.listarProfessores(getToken());
        model.addAttribute("curso", new CursoDTO());
        model.addAttribute("professores", professores);
        return "curso/novo";
    }

    @PostMapping("/create")
    public String save(Model model, CursoInsertDTO cursoInsertDTO){

        try {
            ProfessorDTO professor = professorService.obterProfessorPorId(cursoInsertDTO.getProfessorId(), getToken());

            CursoDTO curso = new CursoDTO();
            curso.setName(cursoInsertDTO.getName());
            curso.setCategory(cursoInsertDTO.getCategory());
            curso.setActive(cursoInsertDTO.getActive());
            curso.setProfessor(professor);

            this.cursoService.save(curso, getToken());

        } catch (HttpClientErrorException ex) {
            model.addAttribute("error_message", ex.getResponseBodyAsString());
            model.addAttribute("curso", cursoInsertDTO);
        }
        return "curso/novo";
    }

    @GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable Long id, Model model, HttpSession session) {
        CursoDTO curso = cursoService.obterCursoPorId(id, getToken());
        List<ProfessorDTO> professores = professorService.listarProfessores(getToken());
        model.addAttribute("curso", curso);
        model.addAttribute("professores", professores);
        return "curso/edit";
    }

    @PostMapping("/editar/{id}")
    public String atualizarCurso(@PathVariable Long id, CursoInsertDTO cursoInsertDTO, HttpSession session) {

        ProfessorDTO professor = null; // Inicializa como null
        if (cursoInsertDTO.getProfessorId() != null) {
            professor = professorService.obterProfessorPorId(cursoInsertDTO.getProfessorId(), getToken());
        }

        CursoDTO curso = new CursoDTO();
        curso.setId(id.toString());
        curso.setName(cursoInsertDTO.getName());
        curso.setCategory(cursoInsertDTO.getCategory());
        curso.setActive(cursoInsertDTO.getActive());
        curso.setProfessor(professor);

        cursoService.atualizarCurso(curso, getToken());
        return "redirect:/cursos/listagem";
    }

    @DeleteMapping("/excluir/{id}")
    public String excluirCurso(@PathVariable Long id, HttpSession session) {
        cursoService.excluirCurso(id, getToken());
        return "redirect:/cursos/listagem";
    }

    private String getToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }
}