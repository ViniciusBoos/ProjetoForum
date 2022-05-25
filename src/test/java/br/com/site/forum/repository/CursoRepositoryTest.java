package br.com.site.forum.repository;

import br.com.site.forum.modelo.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void deveBuscarUmCursoNoBancoDeDados() {
        Curso curso = cursoRepository.findByNome("HTML 5");
        Assertions.assertNotNull(curso);
        Assertions.assertEquals("HTML 5", curso.getNome());
    }


    @Test
    public void naoDeveBuscarUmCursoNaoPresenteNoBancoDeDados() {
        Curso curso = cursoRepository.findByNome("JPA");
        Assertions.assertNull(curso);

    }
}
