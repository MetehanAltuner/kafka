package com.demo.kafka.feature.mapping;

import com.demo.kafka.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mappings")
public class MappingController {

    private final MappingService mappingService;

    public MappingController(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MappingDto>>> getAllMappings() {
        List<MappingDto> mappings = mappingService.getAllMappings();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", mappings));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MappingDto>> getMappingById(@PathVariable Long id) {
        MappingDto mapping = mappingService.getMappingById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", mapping));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MappingDto>> createMapping(@RequestBody MappingDto mappingDto) {
        MappingDto createdMapping = mappingService.createMapping(mappingDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Created successfully", createdMapping));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MappingDto>> updateMapping(@PathVariable Long id, @RequestBody MappingDto mappingDto) {
        MappingDto updatedMapping = mappingService.updateMapping(id, mappingDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Updated successfully", updatedMapping));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMapping(@PathVariable Long id) {
        mappingService.deleteMapping(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", null));
    }
}
