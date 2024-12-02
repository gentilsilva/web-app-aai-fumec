package br.com.back_end.aii.gerenciador_escolar.domain.matricula;

import br.com.back_end.aii.gerenciador_escolar.domain.turma.Turma;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.Universitario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "universitario_id", nullable = false) // FK para Universitários
    private Universitario universitario;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false) // FK para Turmas
    private Turma turma;

    private LocalDate dataMatricula; // Data da matrícula
    private String status; // Status da matrícula (ativo, trancado, etc.)

    public Matricula() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
