package com.demo.kafka.feature.columns;

import com.demo.kafka.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColumnsService {

    private final ColumnsRepository columnsRepository;

    public ColumnsService(ColumnsRepository columnsRepository) {
        this.columnsRepository = columnsRepository;
    }

    public List<ColumnsDto> getAllColumns() {
        return columnsRepository.findAll().stream()
                .map(ColumnsDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ColumnsDto getColumnById(Long id) {
        Columns column = columnsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found"));
        return ColumnsDto.fromEntity(column);
    }

    public ColumnsDto createColumn(ColumnsDto columnsDto) {
        Columns column = columnsDto.toEntity();
        column = columnsRepository.save(column);
        return ColumnsDto.fromEntity(column);
    }

    public ColumnsDto updateColumn(Long id, ColumnsDto columnsDto) {
        Columns column = columnsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found"));
        column.setName(columnsDto.getName());
        column.setTable(columnsDto.getTable());
        column = columnsRepository.save(column);
        return ColumnsDto.fromEntity(column);
    }

    public void deleteColumn(Long id) {
        Columns column = columnsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found"));
        columnsRepository.delete(column);
    }
}

