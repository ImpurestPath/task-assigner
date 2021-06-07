package ru.rvr.taskassigner.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.annotations.WithResolverBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.rvr.taskassigner.entity.task.ITask;
import ru.rvr.taskassigner.entity.task.SimpleTask;
import ru.rvr.taskassigner.repository.ITaskRepository;

import java.util.Collection;

@GraphQLApi
@Service
@WithResolverBuilder(AnnotatedResolverBuilder.class)
public class ITaskService {
    private final ITaskRepository taskRepository;

    public ITaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GraphQLQuery
    public Page<ITask> tasks(@GraphQLArgument(name = "limit", defaultValue = "10") int limit,
                             @GraphQLArgument(name = "offset", defaultValue = "0") int offset){
        return taskRepository.findAll(PageRequest.of(offset / limit, limit));
    }

    @GraphQLMutation
    public void newTask(@GraphQLArgument(name = "name") String name,
                        @GraphQLArgument(name = "description") String description) {
        taskRepository.save(new SimpleTask(name,description));
    }


}
