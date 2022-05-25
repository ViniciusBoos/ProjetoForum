package br.com.site.forum.controller;

import br.com.site.forum.dto.AtualizarTopicoForm;
import br.com.site.forum.dto.TopicoDto;
import br.com.site.forum.dto.TopicoDtoComDetalhes;
import br.com.site.forum.dto.TopicoForm;
import br.com.site.forum.modelo.Topico;
import br.com.site.forum.repository.CursoRepository;
import br.com.site.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Cacheable(value = "TopicosListaCache")
    public Page<TopicoDto> topicos(@RequestParam(required = false) String nomeCurso,
                                   @PageableDefault(size=10, page=0, sort="id", direction = Sort.Direction.ASC)
                                           Pageable pagina) {

        if(nomeCurso != null) {
            return TopicoDto.converter(topicoRepository.findByCursoNome(nomeCurso, pagina));
        } else {

            return TopicoDto.converter(topicoRepository.findAll(pagina));
        }

    }

    @PostMapping
    @CacheEvict(value= "TopicosListaCache", allEntries = true)
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDtoComDetalhes> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if(topico.isPresent()) {
            return ResponseEntity.ok(new TopicoDtoComDetalhes(topico.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @CacheEvict(value= "TopicosListaCache", allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,
                                               @RequestBody @Valid AtualizarTopicoForm form) {
        Optional<Topico> optional = topicoRepository.findById(id);

        if(optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value= "TopicosListaCache", allEntries = true)
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
