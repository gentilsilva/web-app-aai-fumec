package br.com.back_end.aii.gerenciador_escolar.domain.turma;

import br.com.back_end.aii.gerenciador_escolar.domain.materia.Materia;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.Professor;
import jakarta.persistence.*;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;


    public Turma() {}

    public Long getId() {
        return id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getNome() {
        return nome;
    }
}
