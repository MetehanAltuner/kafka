package com.demo.kafka.feature.topic;

import org.springframework.stereotype.Service;
import com.demo.kafka.common.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<TopicDto> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(TopicDto::fromEntity)
                .collect(Collectors.toList());
    }

    public TopicDto getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
        return TopicDto.fromEntity(topic);
    }

    public TopicDto createTopic(TopicDto topicDto) {
        Topic topic = topicDto.toEntity();
        topic = topicRepository.save(topic);
        return TopicDto.fromEntity(topic);
    }

    public TopicDto updateTopic(Long id, TopicDto topicDto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
        topic.setName(topicDto.getName());
        topic.setDescription(topicDto.getDescription());
        topic = topicRepository.save(topic);
        return TopicDto.fromEntity(topic);
    }

    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
        topicRepository.delete(topic);
    }
}

