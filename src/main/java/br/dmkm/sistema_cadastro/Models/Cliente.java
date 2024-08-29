package br.dmkm.sistema_cadastro.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private Integer idade;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private Double altura;
    @Column(nullable = false)
    private Double peso;
    @Column(nullable = false)
    private Double rendaPessoal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getRendaPessoal() {
        return rendaPessoal;
    }

    public void setRendaPessoal(Double rendaPessoal) {
        this.rendaPessoal = rendaPessoal;
    }
}

