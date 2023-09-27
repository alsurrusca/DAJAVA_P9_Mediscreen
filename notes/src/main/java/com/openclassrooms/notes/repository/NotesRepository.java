package com.openclassrooms.notes.repository;

import com.openclassrooms.notes.model.Notes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends MongoRepository<Notes, String> {


    List<Notes> findByPatId(int patId);
}
