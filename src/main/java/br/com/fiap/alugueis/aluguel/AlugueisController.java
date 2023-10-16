package br.com.fiap.alugueis.aluguel;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/aluguel")
public class AlugueisController {

    @Autowired
    AluguelService service;
    
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("alugueis", service.findAll());
        return "aluguel/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", "Aluguel deletado com sucesso");
        }else{
            redirect.addFlashAttribute("error", "Aluguel não encontrado");
        }
        return "redirect:/aluguel";
    }

    @GetMapping("new")
    public String form(Aluguel aluguel){
        return "aluguel/form";
    }

    @PostMapping
    public String create(@Valid Aluguel aluguel, BindingResult binding, RedirectAttributes redirect){
        if (binding.hasErrors()) return "/aluguel/form";

        service.save(aluguel);
        redirect.addFlashAttribute("success", "Aluguel cadastrado com sucesso");
        return "redirect:/aluguel";
    }
    
}
