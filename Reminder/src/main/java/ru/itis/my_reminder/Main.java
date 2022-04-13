package ru.itis.my_reminder;

import ru.itis.my_reminder.exceptions.NotePersistenceException;
import ru.itis.my_reminder.model.Note;
import ru.itis.my_reminder.repostory.NoteRepository;
import ru.itis.my_reminder.repostory.impl.NoteRepositoryCsvImpl;
import ru.itis.my_reminder.repostory.impl.NoteRepositorySerializableImpl;

import java.io.File;
import java.time.Instant;

public class Main {
    // Добавлять заметки на определенное время
    // Отмечать заметки как выполнено
    // Удалять заметки
    // Получать список всех заметок на день
    // Получать список всех выполненных заметок на день
    public static void main(String[] args) {
        try {
            NoteRepository repository = new NoteRepositorySerializableImpl("database" + File.separator + "notes_database");
            repository.deleteAll();
            Note note1 = new Note("Test1", Instant.now(), false);
            Note note2 = new Note("Test1", Instant.now(), true);
            repository.save(note1);
            repository.save(note2);
            System.out.println(repository.getUndoneByDate(Instant.now()));
        } catch (NotePersistenceException e) {
            System.out.println(e.getMessage());
        }
    }
}
