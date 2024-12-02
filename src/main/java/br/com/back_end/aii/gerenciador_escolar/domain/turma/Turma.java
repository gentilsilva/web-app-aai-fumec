package br.com.back_end.aii.gerenciador_escolar.domain.turma;

import br.com.back_end.aii.gerenciador_escolar.domain.materia.Materia;
import br.com.back_end.aii.gerenciador_escolar.domain.matricula.Matricula;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.Professor;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

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

    @OneToMany(mappedBy = "turma")
    private List<Matricula> matriculas; // Lista de matrículas

    public Turma() {}

    public Turma(DadosCadastroTurma dados, Professor professor, Materia materia) {
        this.nome = dados.nome();
        this.professor = professor;
        this.materia = materia;
    }

    public Long getId() {
        return id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getNome() {
        return nome;
    }

    public Materia getMateria() {
        return materia;
    }
}
