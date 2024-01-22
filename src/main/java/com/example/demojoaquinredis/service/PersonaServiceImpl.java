package com.example.demojoaquinredis.service;

import com.example.demojoaquinredis.dto.PersonaDTO;
import com.example.demojoaquinredis.model.Persona;
import com.example.demojoaquinredis.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableCaching
public class PersonaServiceImpl implements PersonaService{

    private final PersonaRepository personaRepository;
    private final ModelMapper modelMapper;


    @Override
    @Cacheable(value = "findAll", key = "'allPersonDTO' + #page + ':' + #size")
    public Page<PersonaDTO> findAll(Integer page, Integer size, Boolean enablePagination) {
        return personaRepository.findAll(enablePagination ? PageRequest.of(page,size): Pageable.unpaged()).map(this::convertToDto);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "findAll", allEntries = true),
            @CacheEvict(value = "persona", key = "#entity.id")
    })
    public void delete(Persona entity) {
        personaRepository.delete(entity);
    }

    @Override
    @CacheEvict(value = "findAll", allEntries = true)
    public PersonaDTO save(PersonaDTO persona) {

        return convertToDto(personaRepository.save(this.modelMapper.map(persona, Persona.class)));

    }

    @Override
    public List<PersonaDTO> findAllById(Iterable<Long> ids) {

        return personaRepository.findAllById(ids).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    @Override
    @Cacheable("persona")
    public PersonaDTO finById(long id) {
        return personaRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "findAll", allEntries = true),
            @CacheEvict(value = "persona", key = "#id")
    })
    public PersonaDTO update(PersonaDTO persona, Long id) {

        Optional<Persona> personaFound = personaRepository.findById(id);

        if (personaFound.isEmpty()) {
            throw new RuntimeException("No se encontró el elemento con ID: " + id);
        }

        BeanUtils.copyProperties(persona, personaFound.get(), "id");
        return convertToDto(personaRepository.save(personaFound.get()));

    }

    private PersonaDTO convertToDto(Persona persona) {
        return modelMapper.map(persona, PersonaDTO.class);
    }

    private Persona convertToEntity(PersonaDTO personaDTO) {
        return modelMapper.map(personaDTO, Persona.class);
    }

}
