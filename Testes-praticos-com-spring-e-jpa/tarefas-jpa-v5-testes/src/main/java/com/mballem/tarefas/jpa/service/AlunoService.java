package com.mballem.tarefas.jpa.service;

import com.mballem.tarefas.jpa.domain.Aluno;
import com.mballem.tarefas.jpa.repository.AlunoRepository;
import com.mballem.tarefas.jpa.specification.AlunoSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    /**
     * TESTE 30 - Complete o metodo com a operação solicitada
     */
    @Transactional(readOnly = true)
    public boolean hasAlunoByMatriculaAndAnoLetivo(String matricula, int ano) {
        Specification<Aluno> spec = Specification.where(
                AlunoSpecifications.likeMatricula(matricula).and(AlunoSpecifications.equalAnoLetivo(ano))
        );
        return this.repository.exists(spec);
    }

    /**
     * TESTE 31 - Complete o metodo com a operação solicitada
     */
    @Transactional(readOnly = true)
    public Aluno findAlunoBolsistaByMatriculaAndUf(String matricula, String uf) {
        Specification<Aluno> spec = Specification.allOf(
                AlunoSpecifications.likeMatricula(matricula),
                AlunoSpecifications.likeUF(uf),
                AlunoSpecifications.isBolsista()
        );
        return this.repository.findOne(spec).orElseGet(Aluno::new);
    }

    /**
     * TESTE 32 - Complete o metodo com a operação solicitada
     */
    @Transactional(readOnly = true)
    public List<Aluno> findAlunoByAnoLetivoAndDataDeNascimento(int anoLetivo, LocalDate inicio, LocalDate fim) {
        Specification<Aluno> spec = Specification.anyOf(
                AlunoSpecifications.equalAnoLetivo(anoLetivo),
                AlunoSpecifications.betweenDataNascimento(inicio, fim)
        );
        return this.repository.findAll(spec);
    }
    /**
     * TESTE 32 - Complete o metodo com a operação solicitada
     */
//    public List<Aluno> findAlunoByAnoLetivoAndDataDeNascimento(int anoLetivo, LocalDate inicio, LocalDate fim) {
//        // 1. Cria a specification chamando nosso método estático
//        Specification<Aluno> spec = AlunoSpecifications.comAnoLetivoOuNascidoEntre(anoLetivo, inicio, fim);
//        // 2. Executa a consulta no repositório com a specification montada
//        return this.repository.findAll(spec);
//    }

    /**
     * TESTE 33 - Complete o metodo com a operação solicitada
     */
    @Transactional(readOnly = true)
    public List<Aluno> findAlunosByArmarios(List<Integer> numeros) {
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        return this.repository.findAll(AlunoSpecifications.inArmarios(numeros), sort);
    }

    /**
     * TESTE 34 - Complete o metodo com a operação solicitada
     */
    @Transactional(readOnly = true)
    public long totalDeAlunosByAnoLetivoAndUF(int anoLetivo, String uf) {
        Specification<Aluno> spec = Specification.allOf(
                AlunoSpecifications.equalAnoLetivo(anoLetivo),
                AlunoSpecifications.likeUF(uf)
        );
        return this.repository.count(spec);
    }

    /**
     * TESTE 35 - Complete o metodo com a operação solicitada
     */
    @Transactional(readOnly = true)
    public List<Aluno> findAlunosNaoBolsistasByUfAndCidadeAndAnoLetivo(String uf, String cidade, int anoLetivo) {
        Specification<Aluno> spec = Specification.allOf(
                AlunoSpecifications.likeUF(uf),
                AlunoSpecifications.likeCidade(cidade),
                AlunoSpecifications.isNotBolsista(),
                AlunoSpecifications.equalAnoLetivo(anoLetivo)
                );
        Sort sort = Sort.by( "nome").ascending().and(Sort.by("anoLetivo").descending());
        return this.repository.findAll(spec,sort);
    }

}