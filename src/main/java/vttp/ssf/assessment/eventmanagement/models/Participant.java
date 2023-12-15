package vttp.ssf.assessment.eventmanagement.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Participant {

  @NotEmpty(message = "Full name is required.")
  @Size(
    min = 5,
    max = 25,
    message = "Full name must be between 5 and 25 characters."
  )
  private String fullName;

  @Past(message = "Birth date must be a past date that is earlier than today.")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date DOB;

  @NotEmpty(message = "Email is required.")
  @Email(message = "Invalid email format.")
  @Size(max = 50, message = "Email length exceeded 50 characters.")
  private String email;

  @Pattern(
    regexp = "(8|9)[0-9]{7}",
    message = "Invalid phone number. Only Singapore numbers are allowed."
  )
  private String mobileNo;

  private String gender;

  @Min(1)
  @Max(3)
  private Integer numberOfTickets;

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Date getDOB() {
    return this.DOB;
  }

  public void setDOB(Date DOB) {
    this.DOB = DOB;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobileNo() {
    return this.mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Integer getNumberOfTickets() {
    return this.numberOfTickets;
  }

  public void setNumberOfTickets(Integer numberOfTickets) {
    this.numberOfTickets = numberOfTickets;
  }

  public int checkAge(Date DOB) {
    LocalDate today = LocalDate.now();
    LocalDate birthday = DOB
      .toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
    Period age = Period.between(birthday, today);
    int years = age.getYears();
    return years;
  }
}
