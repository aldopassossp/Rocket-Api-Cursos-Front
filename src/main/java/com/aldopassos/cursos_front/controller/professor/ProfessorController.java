package com.aldopassos.cursos_front.controller.professor;

import com.aldopassos.cursos_front.dto.ProfessorDTO;
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

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/listagem")
    @PreAuthorize("hasRole('USER')")
    public String list(Model model){
        var result = this.professorService.listarProfessores(getToken());
        model.addAttribute("professores", result);
        return "professor/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("professores", new ProfessorDTO());
        return "professor/create";
    }

    @PostMapping("/create")
    public String save(Model model, ProfessorDTO professorDTO){
        try {
            this.professorService.save(professorDTO, getToken());
//            model.addAttribute("professores", new ProfessorDTO() );
        } catch (HttpClientErrorException ex) {
            model.addAttribute("error_message", ex.getResponseBodyAsString());
            model.addAttribute("professores", professorDTO);
        }
        return "professor/create";
    }

    @GetMapping("/editar/{id}")
    public String editarProfessor(@PathVariable Long id, Model model, HttpSession session) {
        ProfessorDTO professor = professorService.obterProfessorPorId(id, getToken());
        model.addAttribute("professor", professor);
        return "professor/edit";
    }

    @PostMapping("/editar/{id}")
    public String atualizarProfessor(@PathVariable Long id, ProfessorDTO professor, HttpSession session) {
        professorService.atualizarProfessor(professor, getToken());
        return "redirect:/professores/listagem";
    }

    @DeleteMapping("/excluir/{id}")
    public String excluirProfessor(@PathVariable Long id, HttpSession session) {
        professorService.excluirProfessor(id, getToken());
        return "redirect:/professores/listagem";
    }

    private String getToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }
}
