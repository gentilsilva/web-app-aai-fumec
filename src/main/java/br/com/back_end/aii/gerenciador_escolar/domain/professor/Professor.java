package br.com.back_end.aii.gerenciador_escolar.domain.professor;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "professores")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricula;
    private String nome;
    private String emailPessoal;
    private String emailUniversitario;
    private Boolean ativo;

    public Professor() {
    }

    public Professor(String matricula, String nome, String emailPessoal, String emailUniversitario, Boolean ativo) {
        this.matricula = matricula;
        this.nome = nome;
        this.emailPessoal = emailPessoal;
        this.emailUniversitario = emailUniversitario;
        this.ativo = true;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setEmailUniversitario(String emailUniversitario) {
        this.emailUniversitario = emailUniversitario;
    }

    public String geradorMatricula(UUID uuid) {
        String matriculaGerada = Long.toString(Math.abs(uuid.getLeastSignificantBits()), 8);
        return matriculaGerada.length() > 9 ? "p" + matriculaGerada.substring(0, 9) : String.format("p%09", Long.parseLong(matriculaGerada));
    }

    public String geradorEmailUniversitario(String matricula) {
        return matricula + "@fumec.br";
    }

    public void desativarProfessor() {
        this.ativo = false;
    }
}
