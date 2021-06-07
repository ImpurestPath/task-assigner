package ru.rvr.taskassigner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import ru.rvr.taskassigner.entity.task.ITask;

@Component
public interface ITaskRepository extends CrudRepository<ITask, Long>, PagingAndSortingRepository<ITask, Long> {

}
