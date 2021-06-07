package ru.rvr.taskassigner.entity.task;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.NoArgsConstructor;
import ru.rvr.taskassigner.entity.user.User;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
public class TimeBasedTask extends SimpleTask{
    private LocalDateTime validUntil;

    public TimeBasedTask(String name, String description, List<User> users, LocalDateTime validUntil) {
        super(name, description, users);
        this.validUntil = validUntil;
    }

    public TimeBasedTask(String name, String description, LocalDateTime validUntil) {
        super(name, description);
        this.validUntil = validUntil;
    }

    @GraphQLQuery(name = "validUntil")
    public LocalDateTime getValidUntil() {
        return validUntil;
    }
}
