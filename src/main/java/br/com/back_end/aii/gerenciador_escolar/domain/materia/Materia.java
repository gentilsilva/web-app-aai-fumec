package br.com.back_end.aii.gerenciador_escolar.domain.materia;

import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;
import br.com.back_end.aii.gerenciador_escolar.domain.turma.Turma;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "materias")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    Formacao formacao;
    String nome;
    Integer semestre;
    String codigo;

    @Column(name = "horas_semestre")
    Integer horasSemestre;

    @OneToMany(mappedBy = "materia") // Relacionamento inverso
    private List<Turma> turmas;

    public Materia() {}

    public Materia(DadosCadastroMateria dados) {
        this.formacao = dados.formacao();
        this.nome = dados.nome();
        this.semestre = dados.semestre();
        this.codigo = dados.codigo();
        this.horasSemestre = dados.horasSemestre();
    }

    public Long getId() {
        return id;
    }

    public Formacao getFormacao() {
        return formacao;
    }

    public String getNome() {
        return nome;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public String getCodigo() {
        return codigo;
    }

    public Integer getHorasSemestre() {
        return horasSemestre;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }
}
