package in.co.balkishan.springbootstrap.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

import lombok.Data;

@Data
@Entity
@Table(name = "sb_users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  @Size(min = 2, max = 64)
  private String firstName;
  @Size(min = 2, max = 64)
  private String lastName;
  @Email
  private String email;
  @Size(min = 2, max = 256)
  private String address;
  @Size(min = 2, max = 64)
  private String city;
  @Size(min = 2, max = 64)
  private String state;
  @Size(min = 2, max = 64)
  private String country;
  private UserStatus status;
  @Column(nullable = false)
  private Boolean isVerified;
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdOn;
  @Column(nullable = false, updatable = false)
  private LocalDateTime updatedOn;

  @PrePersist
  public void prePersist() {
    createdOn = LocalDateTime.now(ZoneOffset.UTC);
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
    status = UserStatus.ACTIVE;
    isVerified = false;
  }

  @PreUpdate
  public void preUpdate() {
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }

  public enum UserStatus {
    ACTIVE,
    DELETED,
    PAUSED;
  }

}
