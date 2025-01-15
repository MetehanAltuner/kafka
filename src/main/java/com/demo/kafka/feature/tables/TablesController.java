package com.demo.kafka.feature.tables;

import com.demo.kafka.common.ApiResponse;
import com.demo.kafka.feature.tables.dto.TablesRequestDto;
import com.demo.kafka.feature.tables.dto.TablesResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TablesController {

    private final TablesService tablesService;

    public TablesController(TablesService tablesService) {
        this.tablesService = tablesService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TablesResponseDto>> createTable(@RequestBody TablesRequestDto requestDto) {
        TablesResponseDto responseDto = tablesService.createTable(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Table created successfully", responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TablesResponseDto>> getTableById(@PathVariable Long id) {
        TablesResponseDto responseDto = tablesService.getTableById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TablesResponseDto>>> getAllTables() {
        List<TablesResponseDto> responseDtos = tablesService.getAllTables();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TablesResponseDto>> updateTable(@PathVariable Long id, @RequestBody TablesRequestDto requestDto) {
        TablesResponseDto responseDto = tablesService.updateTable(id, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Table updated successfully", responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTable(@PathVariable Long id) {
        tablesService.deleteTable(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Table deleted successfully", null));
    }
}
