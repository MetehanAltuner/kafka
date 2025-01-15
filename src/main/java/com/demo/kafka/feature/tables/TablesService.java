package com.demo.kafka.feature.tables;

import com.demo.kafka.feature.database.Database;
import com.demo.kafka.feature.database.DatabaseRepository;
import com.demo.kafka.common.exception.ResourceNotFoundException;
import com.demo.kafka.feature.tables.dto.TablesRequestDto;
import com.demo.kafka.feature.tables.dto.TablesResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TablesService {

    private final TablesRepository tablesRepository;
    private final DatabaseRepository databaseRepository;

    public TablesService(TablesRepository tablesRepository, DatabaseRepository databaseRepository) {
        this.tablesRepository = tablesRepository;
        this.databaseRepository = databaseRepository;
    }

    public TablesResponseDto createTable(TablesRequestDto requestDto) {
        Database database = databaseRepository.findById(requestDto.getDatabaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Database not found"));

        Tables table = new Tables();
        table.setName(requestDto.getName());
        table.setDatabase(database);

        Tables savedTable = tablesRepository.save(table);
        return TablesResponseDto.fromEntity(savedTable);
    }

    public TablesResponseDto getTableById(Long id) {
        Tables table = tablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));
        return TablesResponseDto.fromEntity(table);
    }

    public List<TablesResponseDto> getAllTables() {
        return tablesRepository.findAll().stream()
                .map(TablesResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public TablesResponseDto updateTable(Long id, TablesRequestDto requestDto) {
        Tables table = tablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));

        Database database = databaseRepository.findById(requestDto.getDatabaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Database not found"));

        table.setName(requestDto.getName());
        table.setDatabase(database);

        Tables updatedTable = tablesRepository.save(table);
        return TablesResponseDto.fromEntity(updatedTable);
    }

    public void deleteTable(Long id) {
        Tables table = tablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));
        tablesRepository.delete(table);
    }
}
