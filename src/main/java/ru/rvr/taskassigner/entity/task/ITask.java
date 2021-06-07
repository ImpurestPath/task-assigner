package ru.rvr.taskassigner.entity.task;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import ru.rvr.taskassigner.entity.user.User;

import javax.persistence.Entity;
import java.util.Collection;

@GraphQLInterface(name = "ITask", implementationAutoDiscovery = true)
public interface ITask {
    @GraphQLQuery(name = "name")
    String getName();

    @GraphQLQuery(name = "description")
    String getDescription();

    @GraphQLQuery(name = "users")
    Collection<User> getUsers();
}
