package com.demo.kafka.feature.database;

import com.demo.kafka.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/databases")
public class DatabaseController {

    private final DatabaseService databaseService;

    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DatabaseDto>>> getAllDatabases() {
        List<DatabaseDto> databases = databaseService.getAllDatabases();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", databases));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DatabaseDto>> getDatabaseById(@PathVariable Long id) {
        DatabaseDto database = databaseService.getDatabaseById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", database));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DatabaseDto>> createDatabase(@RequestBody DatabaseDto databaseDto) {
        DatabaseDto createdDatabase = databaseService.createDatabase(databaseDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Created successfully", createdDatabase));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DatabaseDto>> updateDatabase(@PathVariable Long id, @RequestBody DatabaseDto databaseDto) {
        DatabaseDto updatedDatabase = databaseService.updateDatabase(id, databaseDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Updated successfully", updatedDatabase));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDatabase(@PathVariable Long id) {
        databaseService.deleteDatabase(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", null));
    }
}
