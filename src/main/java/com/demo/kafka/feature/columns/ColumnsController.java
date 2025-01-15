package com.demo.kafka.feature.columns;

import com.demo.kafka.common.ApiResponse;
import com.demo.kafka.feature.columns.dto.ColumnsRequestDto;
import com.demo.kafka.feature.columns.dto.ColumnsResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/columns")
public class ColumnsController {

    private final ColumnsService columnsService;

    public ColumnsController(ColumnsService columnsService) {
        this.columnsService = columnsService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ColumnsResponseDto>> createColumn(@RequestBody ColumnsRequestDto requestDto) {
        ColumnsResponseDto responseDto = columnsService.createColumn(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Column created successfully", responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ColumnsResponseDto>> getColumnById(@PathVariable Long id) {
        ColumnsResponseDto responseDto = columnsService.getColumnById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ColumnsResponseDto>>> getAllColumns() {
        List<ColumnsResponseDto> responseDtos = columnsService.getAllColumns();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ColumnsResponseDto>> updateColumn(@PathVariable Long id, @RequestBody ColumnsRequestDto requestDto) {
        ColumnsResponseDto responseDto = columnsService.updateColumn(id, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Column updated successfully", responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteColumn(@PathVariable Long id) {
        columnsService.deleteColumn(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Column deleted successfully", null));
    }
}

