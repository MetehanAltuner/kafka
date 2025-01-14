package com.demo.kafka.feature.tables;

import com.demo.kafka.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TablesService {

    private final TablesRepository tablesRepository;

    public TablesService(TablesRepository tablesRepository) {
        this.tablesRepository = tablesRepository;
    }

    public List<TablesDto> getAllTables() {
        return tablesRepository.findAll().stream()
                .map(TablesDto::fromEntity)
                .collect(Collectors.toList());
    }

    public TablesDto getTableById(Long id) {
        Tables table = tablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));
        return TablesDto.fromEntity(table);
    }

    public TablesDto createTable(TablesDto tablesDto) {
        Tables table = tablesDto.toEntity();
        table = tablesRepository.save(table);
        return TablesDto.fromEntity(table);
    }

    public TablesDto updateTable(Long id, TablesDto tablesDto) {
        Tables table = tablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));
        table.setName(tablesDto.getName());
        table.setDatabase(tablesDto.getDatabase());
        table = tablesRepository.save(table);
        return TablesDto.fromEntity(table);
    }

    public void deleteTable(Long id) {
        Tables table = tablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));
        tablesRepository.delete(table);
    }
}

