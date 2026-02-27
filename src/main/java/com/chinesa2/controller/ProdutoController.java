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

import com.chinesa2.entity.Produto;
import com.chinesa2.service.ProdutoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "produto/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, RedirectAttributes attributes) {
        produtoService.salvar(produto);
        attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);
        if (produtoOpt.isPresent()) {
            model.addAttribute("produto", produtoOpt.get());
            return "produto/formulario";
        } else {
            attributes.addFlashAttribute("erro", "Produto não encontrado!");
            return "redirect:/produtos";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            produtoService.excluir(id);
            attributes.addFlashAttribute("mensagem", "Produto excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao excluir produto: " + e.getMessage());
        }
        return "redirect:/produtos";
    }
}
