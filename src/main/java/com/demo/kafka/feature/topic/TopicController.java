package com.demo.kafka.feature.topic;

import com.demo.kafka.common.ApiResponse;
import com.demo.kafka.feature.topic.dto.TopicRequestDto;
import com.demo.kafka.feature.topic.dto.TopicResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TopicResponseDto>> createTopic(@RequestBody TopicRequestDto requestDto) {
        TopicResponseDto responseDto = topicService.createTopic(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Topic created successfully", responseDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicResponseDto>> getTopicById(@PathVariable Long id) {
        TopicResponseDto responseDto = topicService.getTopicById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TopicResponseDto>>> getAllTopics() {
        List<TopicResponseDto> responseDtos = topicService.getAllTopics();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", responseDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicResponseDto>> updateTopic(@PathVariable Long id, @RequestBody TopicRequestDto requestDto) {
        TopicResponseDto responseDto = topicService.updateTopic(id, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Topic updated successfully", responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Topic deleted successfully", null));
    }
}


