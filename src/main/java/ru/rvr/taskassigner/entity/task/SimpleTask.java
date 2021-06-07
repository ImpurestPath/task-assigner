package ru.rvr.taskassigner.entity.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.rvr.taskassigner.entity.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "simple_tasks")
public class SimpleTask implements ITask{
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

    public SimpleTask(String name, String description, List<User> users) {
        this.name = name;
        this.description = description;
        this.users = users;
    }

    public SimpleTask(String name, String description) {
        this.name = name;
        this.description = description;
        this.users = new ArrayList<>();
    }
}
