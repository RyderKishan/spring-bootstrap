package in.co.balkishan.springbootstrap.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

import lombok.Data;

@Data
@Entity
@Table(name = "sb_users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  @Size(min = 2, max = 64)
  private String firstName;
  @Size(min = 2, max = 64)
  private String lastName;
  @Column(unique = true, nullable = false)
  @Email
  private String email;
  private String password;
  private String address;
  @Size(min = 2, max = 64)
  private String city;
  @Size(min = 2, max = 64)
  private String state;
  @Size(min = 2, max = 64)
  private String country;
  @Enumerated(EnumType.ORDINAL)
  private UserStatus status;
  @Column(nullable = false)
  private Boolean isVerified;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdOn;
  @Column(nullable = false, updatable = false)
  private LocalDateTime updatedOn;

  private Boolean isCredentialsExpired;
  private Boolean isAccountLocked;
  private Boolean isAccountExpired;

  @PrePersist
  public void prePersist() {
    createdOn = LocalDateTime.now(ZoneOffset.UTC);
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
    status = UserStatus.ACTIVE;
    isVerified = true;
    isAccountExpired = false;
    isAccountLocked = false;
    isCredentialsExpired = false;
  }

  @PreUpdate
  public void preUpdate() {
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }

  public enum UserStatus {
    ACTIVE,
    DELETED,
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !this.isAccountExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !this.isAccountLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !this.isCredentialsExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.status == UserStatus.ACTIVE;
  }

}
