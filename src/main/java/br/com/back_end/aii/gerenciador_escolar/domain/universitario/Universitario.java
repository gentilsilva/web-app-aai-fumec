package br.com.back_end.aii.gerenciador_escolar.domain.universitario;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "universitarios")
public class Universitario {

    @Id
    private Long id;
    private String matricula;
    private String nome;

    @Column(name = "email_universitario")
    private String emailUniversitario;
    private String email;
    private String telefone;
    private String cpf;
    private Boolean ativo;


    public Universitario() {
    }

    public Universitario(DadosCadastroUniversitario dadosUniversitario) {
        this.nome = dadosUniversitario.nome();
        this.email = dadosUniversitario.email();
        this.telefone = dadosUniversitario.telefone();
        this.cpf = dadosUniversitario.cpf();
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getEmailUniversitario() {
        return emailUniversitario;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setEmailUniversitario(String emailUniversitario) {
        this.emailUniversitario = emailUniversitario;
    }

    public String geradorMatricula() {
        String matriculaGerada = Long.toString(Math.abs(UUID.randomUUID().getLeastSignificantBits()), 10);
        return matriculaGerada.length() > 9 ? "a" + matriculaGerada.substring(0, 9) : String.format("a%09d", Long.parseLong(matriculaGerada));
    }

    public String geradorEmailUniversitario(String matricula) {
        return matricula + "@scgest.br";
    }

    public void desativarUniversitario() {
        this.ativo = false;
    }

}
