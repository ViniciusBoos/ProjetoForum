package br.com.site.forum.repository;

import br.com.site.forum.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);
}
