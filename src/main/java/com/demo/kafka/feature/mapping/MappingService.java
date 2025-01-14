package com.demo.kafka.feature.mapping;

import com.demo.kafka.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingService {

    private final MappingRepository mappingRepository;

    public MappingService(MappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    public List<MappingDto> getAllMappings() {
        return mappingRepository.findAll().stream()
                .map(MappingDto::fromEntity)
                .collect(Collectors.toList());
    }

    public MappingDto getMappingById(Long id) {
        Mapping mapping = mappingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mapping not found"));
        return MappingDto.fromEntity(mapping);
    }

    public MappingDto createMapping(MappingDto mappingDto) {
        Mapping mapping = mappingDto.toEntity();
        mapping = mappingRepository.save(mapping);
        return MappingDto.fromEntity(mapping);
    }

    public MappingDto updateMapping(Long id, MappingDto mappingDto) {
        Mapping mapping = mappingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mapping not found"));
        mapping.setTopic(mappingDto.getTopic());
        mapping.setSourceColumn(mappingDto.getSourceColumn());
        mapping.setTargetColumn(mappingDto.getTargetColumn());
        mapping = mappingRepository.save(mapping);
        return MappingDto.fromEntity(mapping);
    }

    public void deleteMapping(Long id) {
        Mapping mapping = mappingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mapping not found"));
        mappingRepository.delete(mapping);
    }
}
