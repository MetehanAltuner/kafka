package com.demo.kafka.feature.mapping;

import com.demo.kafka.common.ApiResponse;
import com.demo.kafka.feature.mapping.dto.MappingRequestDto;
import com.demo.kafka.feature.mapping.dto.MappingResponseDto;
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

    @PostMapping
    public ResponseEntity<ApiResponse<MappingResponseDto>> createMapping(@RequestBody MappingRequestDto requestDto) {
        MappingResponseDto responseDto = mappingService.createMapping(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mapping created successfully", responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MappingResponseDto>> getMappingById(@PathVariable Long id) {
        MappingResponseDto responseDto = mappingService.getMappingById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MappingResponseDto>>> getAllMappings() {
        List<MappingResponseDto> responseDtos = mappingService.getAllMappings();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MappingResponseDto>> updateMapping(@PathVariable Long id, @RequestBody MappingRequestDto requestDto) {
        MappingResponseDto responseDto = mappingService.updateMapping(id, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mapping updated successfully", responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMapping(@PathVariable Long id) {
        mappingService.deleteMapping(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mapping deleted successfully", null));
    }
}

