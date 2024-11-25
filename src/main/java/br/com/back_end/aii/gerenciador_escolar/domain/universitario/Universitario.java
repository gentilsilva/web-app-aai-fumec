package br.com.back_end.aii.gerenciador_escolar.domain.universitario;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "universitarios")
public class Universitario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String matricula;
    private String nome;
    private String emailUniversitario;
    private String emailPessoal;
    private Boolean ativo;


    public Universitario() {
    }

    public Universitario(UUID uuid, String matricula, String nome, String emailUniversitario, String emailPessoal) {
        this.id = uuid;
        this.matricula = matricula;
        this.nome = nome;
        this.emailUniversitario = emailUniversitario;
        this.emailPessoal = emailPessoal;
        ativo = true;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setEmailUniversitario(String emailUniversitario) {
        this.emailUniversitario = emailUniversitario;
    }

    public String geradorMatricula(UUID uuid) {
        String matriculaGerada = Long.toString(Math.abs(uuid.getLeastSignificantBits()), 10);
        return matriculaGerada.length() > 9 ? "a" + matriculaGerada.substring(0, 9) : String.format("a%09d", Long.parseLong(matriculaGerada));
    }

    public String geradorEmailUniversitario(String matricula) {
        return matricula + "@fumec.br";
    }

    public void desativarUniversitario() {
        this.ativo = false;
    }

}
