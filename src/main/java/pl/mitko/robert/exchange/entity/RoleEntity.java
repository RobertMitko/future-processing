package pl.mitko.robert.exchange.entity;

import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "role")
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "roles")
  private Set<UserEntity> users;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<UserEntity> getUsers() {
    return users;
  }

  public void setUsers(Set<UserEntity> users) {
    this.users = users;
  }


}

