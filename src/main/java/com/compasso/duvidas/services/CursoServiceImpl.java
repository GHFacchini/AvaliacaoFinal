package com.compasso.duvidas.services;

import java.util.Optional;

import javax.transaction.Transactional;

import com.compasso.duvidas.constants.Categoria;
import com.compasso.duvidas.dto.TurmaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.compasso.duvidas.dto.CursoDTO;
import com.compasso.duvidas.dto.CursoFormDTO;
import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.repositories.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    @Transactional
    public ResponseEntity<CursoDTO> save(CursoFormDTO form) {
        Curso entity = cursoRepository.save(mapper.map(form, Curso.class));
        CursoDTO cursoDTO = new CursoDTO(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDTO);
    }

    @Override
    public Page<CursoDTO> findAll(Pageable page, Categoria categoria) {
        if(categoria == null){
            Page<Curso> cursos = cursoRepository.findAll(page);
            Page<CursoDTO> cursosDTOS = cursos.map(CursoDTO::new);
            return cursosDTOS;
        }else{
            Page<Curso> cursos = cursoRepository.findByCategoria(page, categoria);
            Page<CursoDTO> cursosDTOS = cursos.map(CursoDTO::new);
            return cursosDTOS;
        }
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (!cursoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        return ResponseEntity.ok().body(new CursoDTO(cursoOptional.get()));

    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Long id, CursoFormDTO form) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (!cursoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        Curso entity = cursoOptional.get();
        if (form.getNome() != null)
            entity.setNome(form.getNome());
        if (form.getCategoria() != null)
            entity.setCategoria(form.getCategoria());

        cursoRepository.save(entity);

        return ResponseEntity.ok().body(mapper.map(entity, CursoDTO.class));

    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (!cursoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        cursoRepository.delete(cursoOptional.get());
        return ResponseEntity.ok().body("O curso de id: " + id + " foi deletado");

    }

}
