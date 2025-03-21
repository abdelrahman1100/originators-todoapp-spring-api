package com.masteryhub.todoapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Setter
@Getter
public class UserEntity {

  @Id private String id;

  private String username;

  private String email;

  private String password;

  private List<String> todos = new ArrayList<>();

  private List<String> friends = new ArrayList<>();

  private Integer tokenVersion = 0;

  @Version private Long __v;

  public List<String> getTodosIds() {
    return Collections.unmodifiableList(todos);
  }

  public void setTodosIds(List<String> newTodosIds) {
    this.todos = new ArrayList<>(newTodosIds);
  }

  public void addTodoId(String todoId) {
    this.todos.add(todoId);
  }

  public void removeTodoId(String todoId) {
    this.todos.remove(todoId);
  }

  public void clearTodosIds() {
    this.todos.clear();
  }

  public void addFriend(String username) {
    this.friends.add(username);
  }

  public void removeFriend(String username) {
    this.friends.remove(username);
  }
}
