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

import lombok.Data;

@Data
@Entity
@Table(name = "sb_accounts")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  @Size(min = 2, max = 64)
  private String name;
  @Column(unique = true)
  @Size(min = 2, max = 64)
  private String subdomain;
  private AccountStatus status;
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdOn;
  @Column(nullable = false, updatable = false)
  private LocalDateTime updatedOn;

  @PrePersist
  public void prePersist() {
    createdOn = LocalDateTime.now(ZoneOffset.UTC);
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
    status = AccountStatus.ACTIVE;
  }

  @PreUpdate
  public void preUpdate() {
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }

  public enum AccountStatus {
    ACTIVE,
    DELETED,
    PAUSED;
  }

}
