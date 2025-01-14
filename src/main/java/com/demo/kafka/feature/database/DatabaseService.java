package com.demo.kafka.feature.database;

import com.demo.kafka.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseService {

    private final DatabaseRepository databaseRepository;

    public DatabaseService(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    public List<DatabaseDto> getAllDatabases() {
        return databaseRepository.findAll().stream()
                .map(DatabaseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DatabaseDto getDatabaseById(Long id) {
        Database database = databaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Database not found"));
        return DatabaseDto.fromEntity(database);
    }

    public DatabaseDto createDatabase(DatabaseDto databaseDto) {
        Database database = databaseDto.toEntity();
        database = databaseRepository.save(database);
        return DatabaseDto.fromEntity(database);
    }

    public DatabaseDto updateDatabase(Long id, DatabaseDto databaseDto) {
        Database database = databaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Database not found"));
        database.setName(databaseDto.getName());
        database.setConnectionUrl(databaseDto.getConnectionUrl());
        database = databaseRepository.save(database);
        return DatabaseDto.fromEntity(database);
    }

    public void deleteDatabase(Long id) {
        Database database = databaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Database not found"));
        databaseRepository.delete(database);
    }
}
