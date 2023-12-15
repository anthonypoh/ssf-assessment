package vttp.ssf.assessment.eventmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.Participant;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Controller
@RequestMapping(path = "/events")
public class RegistrationController {

  @Autowired
  private RedisRepository redisRepo;

  // TODO: Task 6
  @GetMapping(path = "register/{id}")
  public ModelAndView getMethodName(@PathVariable int id)
    throws JsonMappingException, JsonProcessingException {
    ModelAndView mav = new ModelAndView("eventregister");
    Participant participant = new Participant();
    mav.addObject("id", id);
    mav.addObject("participant", participant);
    mav.addObject("event", redisRepo.getEvent(id - 1));
    return mav;
  }

  // TODO: Task 7
  @PostMapping(path = "register/{id}")
  public ModelAndView processRegistration(
    @Valid @ModelAttribute("participant") Participant participant,
    BindingResult result,
    @PathVariable int id
  ) throws JsonMappingException, JsonProcessingException {
    ModelAndView mav = new ModelAndView("errorregistration");
    id -= 1;
    Event event = redisRepo.getEvent(id);
    mav.addObject("event", event);

    // Form Validation
    if (result.hasErrors()) {
      mav.addObject("id", id);
      mav.setViewName("eventregister");
      return mav;
    }

    // Server-side validation
    int age = participant.checkAge(participant.getDOB());
    Integer eventSize = redisRepo.getEventSize(id);
    Integer participants = redisRepo.getParticipants(id);
    boolean exceedSize =
      (participants + participant.getNumberOfTickets()) >= eventSize;

    List<String> errorList = new ArrayList<>();
    if (age < 21) errorList.add("You must be 21 years old and above.");
    if (exceedSize) errorList.add(
      "Your request for tickets exceeded the event size."
    );

    if ((age >= 21) && (!exceedSize)) {
      redisRepo.incrParticipants(id, participant.getNumberOfTickets());
      mav.setViewName("successregistration");
    } else {
      mav.addObject("errors", errorList);
    }

    return mav;
  }
}
