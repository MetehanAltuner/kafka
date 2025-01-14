package com.demo.kafka.feature.columns;

import com.demo.kafka.common.ApiResponse;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<ColumnsDto>>> getAllColumns() {
        List<ColumnsDto> columns = columnsService.getAllColumns();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", columns));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ColumnsDto>> getColumnById(@PathVariable Long id) {
        ColumnsDto column = columnsService.getColumnById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", column));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ColumnsDto>> createColumn(@RequestBody ColumnsDto columnsDto) {
        ColumnsDto createdColumn = columnsService.createColumn(columnsDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Created successfully", createdColumn));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ColumnsDto>> updateColumn(@PathVariable Long id, @RequestBody ColumnsDto columnsDto) {
        ColumnsDto updatedColumn = columnsService.updateColumn(id, columnsDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Updated successfully", updatedColumn));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteColumn(@PathVariable Long id) {
        columnsService.deleteColumn(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", null));
    }
}
