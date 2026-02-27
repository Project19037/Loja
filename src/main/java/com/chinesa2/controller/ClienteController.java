package com.chinesa2.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chinesa2.entity.Cliente;
import com.chinesa2.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "cliente/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente, RedirectAttributes attributes) {
        clienteService.salvar(cliente);
        attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(id);
        if (clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
            return "cliente/formulario";
        } else {
            attributes.addFlashAttribute("erro", "Cliente não encontrado!");
            return "redirect:/clientes";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            clienteService.excluir(id);
            attributes.addFlashAttribute("mensagem", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao excluir cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
}