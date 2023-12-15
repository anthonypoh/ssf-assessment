package vttp.ssf.assessment.eventmanagement.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

  @Autowired
  @Qualifier("myredis")
  private RedisTemplate<String, String> template;

  // TODO: Task 2
  public void saveRecord(Event event) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String eventJson = objectMapper.writeValueAsString(event);

    ListOperations<String, String> list = template.opsForList();
    list.rightPush("events", eventJson);
  }

  // TODO: Task 3
  public Long getNumberOfEvents() {
    ListOperations<String, String> list = template.opsForList();
    return list.size("events");
  }

  // TODO: Task 4
  public Event getEvent(Integer index)
    throws JsonMappingException, JsonProcessingException {
    ListOperations<String, String> list = template.opsForList();
    String eventJson = list.index("events", index);

    Event event = parseJsonEvent(eventJson);
    return event;
  }

  public Event parseJsonEvent(String eventJson)
    throws JsonMappingException, JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    Event event = objectMapper.readValue(eventJson, Event.class);
    return event;
  }

  // My methods for task 7
  public Integer getEventSize(Integer index)
    throws JsonMappingException, JsonProcessingException {
    Event event = getEvent(index);
    return event.getEventSize();
  }

  public Integer getParticipants(Integer index)
    throws JsonMappingException, JsonProcessingException {
    Event event = getEvent(index);
    return event.getParticipants();
  }

  public void incrParticipants(Integer index, Integer participants)
    throws JsonMappingException, JsonProcessingException {
    ListOperations<String, String> list = template.opsForList();

    Event event = getEvent(index);
    event.setParticipants(event.getParticipants() + participants);

    ObjectMapper objectMapper = new ObjectMapper();
    String eventJson = objectMapper.writeValueAsString(event);

    list.set("events", index, eventJson);
  }
}
