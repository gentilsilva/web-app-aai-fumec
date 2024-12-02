package br.com.back_end.aii.gerenciador_escolar.domain.materia;

import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {
    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Page<DadosListagemMateria> listar(Pageable paginacao) {
        return materiaRepository.findAll(paginacao).map(DadosListagemMateria::new);
    }

    public DadosCadastroMateria carregarPorId(Long id) {
        Materia materia = materiaRepository.findById(id).orElseThrow();
        return new DadosCadastroMateria(materia.getId(), materia.getNome(), materia.getSemestre(),
                materia.getCodigo(), materia.getHorasSemestre(), materia.getFormacao());
    }

    public void cadastrarMateria(DadosCadastroMateria dados) {
        if(materiaRepository.jaCadastrado(dados.id(), dados.codigo(), dados.semestre())) {
            throw new RegraDeNegocioException("Matéria já cadastrada");
        }

        Materia materia = new Materia(dados);
        materiaRepository.save(materia);
    }

    public List<DadosListagemMateria> listarPorFormacao(Formacao formacao) {
        return materiaRepository.findByFormacao(formacao).stream().map(DadosListagemMateria::new).toList();
    }

    public void excluir(Long id) {
        materiaRepository.deleteById(id);
    }
}
