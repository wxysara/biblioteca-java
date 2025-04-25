package br.com.bpkedu.library_spring_webmvc.controller;

import br.com.bpkedu.library_spring_webmvc.domain.Cliente;
import br.com.bpkedu.library_spring_webmvc.domain.Emprestimo;
import br.com.bpkedu.library_spring_webmvc.domain.Livro;
import br.com.bpkedu.library_spring_webmvc.repository.ClienteRepository;
import br.com.bpkedu.library_spring_webmvc.repository.EmprestimoRepository;
import br.com.bpkedu.library_spring_webmvc.repository.LivroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoRepository emprestimoRepository;
    private final ClienteRepository clienteRepository;
    private final LivroRepository livroRepository;

    public EmprestimoController(EmprestimoRepository emprestimoRepository, ClienteRepository clienteRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.clienteRepository = clienteRepository;
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("emprestimos", emprestimoRepository.findAll());
        return "emprestimos/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        List<Long> idsEmprestados = emprestimoRepository.findIdsLivrosEmprestados();
        List<Livro> livrosDisponiveis = livroRepository.findAll()
            .stream()
            .filter(livro -> !idsEmprestados.contains(livro.getId()))
            .collect(Collectors.toList());

        model.addAttribute("emprestimo", new Emprestimo());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("livros", livrosDisponiveis);
        return "emprestimos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Emprestimo emprestimo) {
        Cliente cliente = clienteRepository.findById(emprestimo.getCliente().getId()).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Livro livro = livroRepository.findById(emprestimo.getLivro().getId()).orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        emprestimo.setCliente(cliente);
        emprestimo.setLivro(livro);

        emprestimoRepository.save(emprestimo);
        return "redirect:/emprestimos";
    }

}
