package com.demo.kafka.feature.database;

import com.demo.kafka.common.ApiResponse;
import com.demo.kafka.feature.database.dto.DatabaseRequestDto;
import com.demo.kafka.feature.database.dto.DatabaseResponseDto;
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

    @PostMapping
    public ResponseEntity<ApiResponse<DatabaseResponseDto>> createDatabase(@RequestBody DatabaseRequestDto requestDto) {
        DatabaseResponseDto responseDto = databaseService.createDatabase(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Database created successfully", responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DatabaseResponseDto>> getDatabaseById(@PathVariable Long id) {
        DatabaseResponseDto responseDto = databaseService.getDatabaseById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DatabaseResponseDto>>> getAllDatabases() {
        List<DatabaseResponseDto> responseDtos = databaseService.getAllDatabases();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DatabaseResponseDto>> updateDatabase(@PathVariable Long id, @RequestBody DatabaseRequestDto requestDto) {
        DatabaseResponseDto responseDto = databaseService.updateDatabase(id, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Database updated successfully", responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDatabase(@PathVariable Long id) {
        databaseService.deleteDatabase(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Database deleted successfully", null));
    }
}

