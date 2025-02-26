package org.example.todolist.services;

import org.example.todolist.exceptions.NoteNotFoundException;
import org.example.todolist.model.Note;
import org.example.todolist.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public Note add(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException("Note with id " + id + " not found.");
        }
        noteRepository.deleteById(id);
    }

    @Transactional
    public void update(Note note) {
        Note existingNote = noteRepository.findById(note.getId())
                .orElseThrow(() -> new NoteNotFoundException("Note with id " + note.getId() + " not found."));
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        noteRepository.save(existingNote);
    }

    public Note getById(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException ("Note with id " + id + " not found."));
    }
}