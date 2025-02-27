package org.example.todolist.mappers;

import org.example.todolist.dto.NoteRequestDto;
import org.example.todolist.dto.NoteResponseDto;
import org.example.todolist.model.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public Note fromDto(NoteRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return note;
    }

    public NoteResponseDto toDto(Note note) {
        if (note == null) {
            return null;
        }
        return new NoteResponseDto(note.getId(), note.getTitle(), note.getContent());
    }
}
