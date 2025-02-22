package org.example.todolist.repositories;

import org.example.todolist.enteties.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}