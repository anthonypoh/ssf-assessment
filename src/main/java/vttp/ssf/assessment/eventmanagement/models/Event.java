package vttp.ssf.assessment.eventmanagement.models;

public class Event {

  private Integer eventId;
  private String eventName;
  private Integer eventSize;
  private Long eventDate;
  private Integer participants;

  public Integer getEventId() {
    return this.eventId;
  }

  public void setEventId(Integer eventId) {
    this.eventId = eventId;
  }

  public String getEventName() {
    return this.eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public Integer getEventSize() {
    return this.eventSize;
  }

  public void setEventSize(Integer eventSize) {
    this.eventSize = eventSize;
  }

  public Long getEventDate() {
    return this.eventDate;
  }

  public void setEventDate(Long eventDate) {
    this.eventDate = eventDate;
  }

  public Integer getParticipants() {
    return this.participants;
  }

  public void setParticipants(Integer participants) {
    this.participants = participants;
  }
}
