package vttp.ssf.assessment.eventmanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner {

  @Value("${file.path}")
  private String filePath;

  @Autowired
  private DatabaseService dbService;

  @Autowired
  private RedisRepository redisRepo;

  public static void main(String[] args) {
    SpringApplication.run(EventmanagementApplication.class, args);
  }

  // TODO: Task 1
  @Override
  public void run(String... args) throws JsonProcessingException {
    List<Event> eventList = dbService.readFile(filePath);

    for (Event event : eventList) {
      System.out.printf(
        "Event Id: %d\nEvent Name: %s\nEvent Size: %d\nEvent Date: %d\nParticipants: %s\n\n",
        event.getEventId(),
        event.getEventName(),
        event.getEventSize(),
        event.getEventDate(),
        event.getParticipants()
      );
    }

    for (Event event : eventList) {
      redisRepo.saveRecord(event);
    }

    System.out.printf("Number of events: %d\n", redisRepo.getNumberOfEvents());
  }
}
