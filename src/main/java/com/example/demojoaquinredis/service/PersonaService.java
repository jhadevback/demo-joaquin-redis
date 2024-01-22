package com.example.demojoaquinredis.service;

import com.example.demojoaquinredis.dto.PersonaDTO;
import com.example.demojoaquinredis.model.Persona;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PersonaService {
    Page<PersonaDTO> findAll(Integer page, Integer size, Boolean enablePagination);
    void delete(Persona entity);
    PersonaDTO save(PersonaDTO persona);
    List<PersonaDTO> findAllById(Iterable<Long> ids);
    PersonaDTO finById(long id);

    PersonaDTO update(PersonaDTO persona, Long id);
}
