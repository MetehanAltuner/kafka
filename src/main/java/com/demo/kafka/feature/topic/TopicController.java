package com.demo.kafka.feature.topic;

import com.demo.kafka.common.ApiResponse;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<TopicDto>>> getAllTopics() {
        List<TopicDto> topics = topicService.getAllTopics();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", topics));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicDto>> getTopicById(@PathVariable Long id) {
        TopicDto topic = topicService.getTopicById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", topic));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TopicDto>> createTopic(@RequestBody TopicDto topicDto) {
        TopicDto createdTopic = topicService.createTopic(topicDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Created successfully", createdTopic));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicDto>> updateTopic(@PathVariable Long id, @RequestBody TopicDto topicDto) {
        TopicDto updatedTopic = topicService.updateTopic(id, topicDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Updated successfully", updatedTopic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", null));
    }
}

