package vttp.ssf.assessment.eventmanagement.services;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Service
public class DatabaseService {

  // TODO: Task 1
  public List<Event> readFile(String filePath) {
    List<Event> eventList = new ArrayList<>();

    try (JsonReader jsonReader = Json.createReader(new FileReader(filePath))) {
      JsonArray jsonArray = jsonReader.readArray();

      for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
        Event event = new Event();
        event.setEventId(jsonObject.getInt("eventId"));
        event.setEventName(jsonObject.getString("eventName"));
        event.setEventSize(jsonObject.getInt("eventSize"));

        JsonNumber jsonEventDate = jsonObject.getJsonNumber("eventDate");
        Long eventDate = jsonEventDate.longValue();

        event.setEventDate(eventDate);
        event.setParticipants(jsonObject.getInt("participants"));

        eventList.add(event);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return eventList;
  }
}
