package br.com.back_end.aii.gerenciador_escolar.domain.professor;

import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;
import br.com.back_end.aii.gerenciador_escolar.domain.turma.Turma;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "professores")
public class Professor {

    @Id
    private Long id;
    private String matricula;
    private String nome;
    private String email;
    private String emailUniversitario;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Formacao formacao;
    private Boolean ativo;

    @OneToMany(mappedBy = "professor")
    private List<Turma> turmas;

    public Professor() {
    }

    public Professor(DadosCadastroProfessor dadosCadastroProfessor) {
        this.nome = dadosCadastroProfessor.nome();
        this.email = dadosCadastroProfessor.email();
        this.cpf = dadosCadastroProfessor.cpf();
        this.formacao = dadosCadastroProfessor.formacao();
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

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailUniversitario() {
        return emailUniversitario;
    }

    public void setEmailUniversitario(String emailUniversitario) {
        this.emailUniversitario = emailUniversitario;
    }

    public String getCpf() {
        return cpf;
    }

    public Formacao getFormacao() {
        return formacao;
    }

    public String geradorMatricula() {
        String matriculaGerada = Long.toString(Math.abs(UUID.randomUUID().getLeastSignificantBits()), 10);
        return matriculaGerada.length() > 9 ? "p" + matriculaGerada.substring(0, 9) : String.format("p%09", Long.parseLong(matriculaGerada));
    }

    public String geradorEmailUniversitario(String matricula) {
        return matricula + "@scgest.br";
    }

    public void desativarProfessor() {
        this.ativo = false;
    }

}
