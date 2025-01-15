package com.demo.kafka.feature.topic;

import com.demo.kafka.feature.topic.dto.TopicRequestDto;
import com.demo.kafka.feature.topic.dto.TopicResponseDto;
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

    public TopicResponseDto createTopic(TopicRequestDto requestDto) {
        Topic topic = new Topic();
        topic.setName(requestDto.getName());
        topic.setDescription(requestDto.getDescription());

        Topic savedTopic = topicRepository.save(topic);
        return TopicResponseDto.fromEntity(savedTopic);
    }

    public TopicResponseDto getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
        return TopicResponseDto.fromEntity(topic);
    }

    public List<TopicResponseDto> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(TopicResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public TopicResponseDto updateTopic(Long id, TopicRequestDto requestDto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        topic.setName(requestDto.getName());
        topic.setDescription(requestDto.getDescription());

        Topic updatedTopic = topicRepository.save(topic);
        return TopicResponseDto.fromEntity(updatedTopic);
    }

    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
        topicRepository.delete(topic);
    }
}


