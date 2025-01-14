package com.demo.kafka.feature.tables;

import com.demo.kafka.common.ApiResponse;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<TablesDto>>> getAllTables() {
        List<TablesDto> tables = tablesService.getAllTables();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", tables));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TablesDto>> getTableById(@PathVariable Long id) {
        TablesDto table = tablesService.getTableById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", table));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TablesDto>> createTable(@RequestBody TablesDto tablesDto) {
        TablesDto createdTable = tablesService.createTable(tablesDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Created successfully", createdTable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TablesDto>> updateTable(@PathVariable Long id, @RequestBody TablesDto tablesDto) {
        TablesDto updatedTable = tablesService.updateTable(id, tablesDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Updated successfully", updatedTable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTable(@PathVariable Long id) {
        tablesService.deleteTable(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", null));
    }
}
