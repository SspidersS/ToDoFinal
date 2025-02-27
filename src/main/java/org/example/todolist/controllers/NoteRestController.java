package org.example.todolist.controllers;

import org.example.todolist.dto.NoteRequestDto;
import org.example.todolist.dto.NoteResponseDto;
import org.example.todolist.mappers.NoteMapper;
import org.example.todolist.model.Note;
import org.example.todolist.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteRestController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NoteRestController(NoteService noteService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.noteMapper = noteMapper;
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getAllNotes() {
        List<Note> notes = noteService.listAll();
        List<NoteResponseDto> responseDtos = notes.stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable("id") long id) {
        Note note = noteService.getById(id);
        if (note != null) {
            NoteResponseDto responseDto = noteMapper.toDto(note);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@RequestBody NoteRequestDto noteRequestDto) {
        Note note = noteMapper.fromDto(noteRequestDto);
        Note createdNote = noteService.add(note);
        NoteResponseDto responseDto = noteMapper.toDto(createdNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable("id") long id,
                                                      @RequestBody NoteRequestDto noteRequestDto) {
        Note note = noteMapper.fromDto(noteRequestDto);
        note.setId(id);
        Note updatedNote = noteService.update(note);
        NoteResponseDto responseDto = noteMapper.toDto(updatedNote);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") long id) {
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

