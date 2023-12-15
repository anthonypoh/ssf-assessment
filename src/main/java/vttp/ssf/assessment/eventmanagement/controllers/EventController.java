package vttp.ssf.assessment.eventmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Controller
@RequestMapping(path = "/events/listing")
public class EventController {

  @Autowired
  private RedisRepository redisRepo;

  //TODO: Task 5
  @GetMapping
  public ModelAndView displayEvents()
    throws JsonMappingException, JsonProcessingException {
    ModelAndView mav = new ModelAndView("eventlisting");
    List<Event> events = new ArrayList<>();

    Long eventCount = redisRepo.getNumberOfEvents();
    for (int i = 0; i < eventCount; i++) {
      events.add(redisRepo.getEvent(i));
    }
    mav.addObject("events", events);
    return mav;
  }

  @GetMapping(path = "/{id}")
  public String getMethodName(@PathVariable int id) {
    return "redirect:/events/register/{id}";
  }
}
