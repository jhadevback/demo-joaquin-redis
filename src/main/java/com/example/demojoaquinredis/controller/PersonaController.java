package com.example.demojoaquinredis.controller;

import com.example.demojoaquinredis.dto.PersonaDTO;
import com.example.demojoaquinredis.model.Persona;
import com.example.demojoaquinredis.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("persona/")
@Tag(name = "Persona", description = "Manage personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @PostMapping
    @Operation(summary = "crear personas")
    public ResponseEntity<PersonaDTO> create (@Valid @RequestBody PersonaDTO persona) {

        try {
            PersonaDTO newPersona = personaService.save(persona);
            return ResponseEntity.created(new URI("/api/persona" + newPersona.getDocumentIdentityNumber())).body(persona);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping
    @Operation(summary = "listar personas paginacion")
    public ResponseEntity<Page<PersonaDTO>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "false") Boolean enablePagination
    ) {
        return ResponseEntity.ok(personaService.findAll(page,size,enablePagination));
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonaDTO> getById(@PathVariable("id") long id) {

        PersonaDTO personaDTO = personaService.finById(id);

        if (personaDTO==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(personaDTO);
    }

    @PutMapping("{id}")
    @Operation(summary = "update personas")
    public ResponseEntity<PersonaDTO> update (@Valid @RequestBody PersonaDTO persona, @PathVariable("id") Long id) {

        try {
            PersonaDTO updatePersona = personaService.update(persona, id);
            return ResponseEntity.ok(updatePersona);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping
    @Operation(summary = "eliminar personas")
    public ResponseEntity<Void> deleteById(@RequestBody Persona persona){
        try {
            personaService.delete(persona);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }





}
