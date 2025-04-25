package br.com.bpkedu.library_spring_webmvc.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    // Getters e setters padrão
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public String getDataEmprestimoFormatada() {
        return dataEmprestimo != null ? dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    public String getDataDevolucaoFormatada() {
        return dataDevolucao != null ? dataDevolucao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }
}
