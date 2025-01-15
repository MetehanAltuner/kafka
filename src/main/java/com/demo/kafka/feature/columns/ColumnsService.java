package com.demo.kafka.feature.columns;

import com.demo.kafka.common.exception.ResourceNotFoundException;
import com.demo.kafka.feature.columns.dto.ColumnsRequestDto;
import com.demo.kafka.feature.columns.dto.ColumnsResponseDto;
import com.demo.kafka.feature.tables.Tables;
import com.demo.kafka.feature.tables.TablesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColumnsService {

    private final ColumnsRepository columnsRepository;
    private final TablesRepository tablesRepository;

    public ColumnsService(ColumnsRepository columnsRepository, TablesRepository tablesRepository) {
        this.columnsRepository = columnsRepository;
        this.tablesRepository = tablesRepository;
    }

    public ColumnsResponseDto createColumn(ColumnsRequestDto requestDto) {
        Tables table = tablesRepository.findById(requestDto.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));

        Columns column = new Columns();
        column.setName(requestDto.getName());
        column.setPrimaryKey(requestDto.isPrimaryKey());
        column.setTable(table);

        Columns savedColumn = columnsRepository.save(column);
        return ColumnsResponseDto.fromEntity(savedColumn);
    }

    public ColumnsResponseDto getColumnById(Long id) {
        Columns column = columnsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found"));
        return ColumnsResponseDto.fromEntity(column);
    }

    public List<ColumnsResponseDto> getAllColumns() {
        return columnsRepository.findAll().stream()
                .map(ColumnsResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ColumnsResponseDto updateColumn(Long id, ColumnsRequestDto requestDto) {
        Columns column = columnsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found"));

        Tables table = tablesRepository.findById(requestDto.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found"));

        column.setName(requestDto.getName());
        column.setPrimaryKey(requestDto.isPrimaryKey());
        column.setTable(table);

        Columns updatedColumn = columnsRepository.save(column);
        return ColumnsResponseDto.fromEntity(updatedColumn);
    }

    public void deleteColumn(Long id) {
        Columns column = columnsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found"));
        columnsRepository.delete(column);
    }
}


