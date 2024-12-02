package br.com.back_end.aii.gerenciador_escolar.domain.matricula;

import br.com.back_end.aii.gerenciador_escolar.domain.turma.Turma;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.Universitario;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "universitario_id", nullable = false)
    private Universitario universitario;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    private LocalDate dataMatricula;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Matricula() {
    }

    public Matricula(Universitario universitario, Turma turma) {
        this.universitario = universitario;
        this.turma = turma;
        this.dataMatricula = LocalDate.now();
        this.status = Status.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public Universitario getUniversitario() {
        return universitario;
    }

    public Turma getTurma() {
        return turma;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
