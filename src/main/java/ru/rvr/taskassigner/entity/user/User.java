package ru.rvr.taskassigner.entity.user;

import io.leangen.graphql.annotations.GraphQLQuery;
import ru.rvr.taskassigner.entity.task.ITask;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String username;

    @GraphQLQuery(name = "id")
    public long getId() {
        return id;
    }

    @GraphQLQuery(name = "username")
    public String getUsername() {
        return username;
    }
}
