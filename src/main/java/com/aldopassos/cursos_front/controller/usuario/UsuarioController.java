package com.aldopassos.cursos_front.controller.usuario;

import com.aldopassos.cursos_front.dto.UsuarioDTO;
import com.aldopassos.cursos_front.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listagem")
    @PreAuthorize("hasRole('USER')")
    public String list(Model model){
        var result = this.usuarioService.listarUsuarios(getToken());
        model.addAttribute("usuarios", result);
        return "usuario/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "usuario/create";
    }

    @PostMapping("/create")
    public String save(Model model, UsuarioDTO usuarioDTO){
        try {
            this.usuarioService.save(usuarioDTO, getToken());
        } catch (HttpClientErrorException ex) {
            model.addAttribute("error_message", ex.getResponseBodyAsString());
            model.addAttribute("usuario", usuarioDTO);
        }
        return "usuario/create";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model, HttpSession session) {
        UsuarioDTO usuario = usuarioService.obterUsuarioPorId(id, getToken());
        model.addAttribute("usuario", usuario);
        return "usuario/edit";
    }

    @PostMapping("/editar/{id}")
    public String atualizarUsuario(@PathVariable Long id, UsuarioDTO usuario, HttpSession session) {
        usuarioService.atualizarUsuario(usuario, getToken());
        return "redirect:/usuarios/listagem";
    }

    @DeleteMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id, HttpSession session) {
        usuarioService.excluirUsuario(id, getToken());
        return "redirect:/usuarios/listagem";
    }

    private String getToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }
}