package com.gb.dz15.services;

import com.gb.dz15.entities.Task;
import com.gb.dz15.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository repository;

    public TaskService() {
    }

    @Autowired
    public void setProductRepository(TaskRepository TaskRepository) {
        this.repository = TaskRepository;
    }

    public Task save(Task Task) {
        return repository.save(Task);
    }

    public void delete(Task Task) {
        repository.delete(Task);
    }

    public List<Task> getAll() {
        return (List<Task>) repository.findAll();
    }

    public Task getOneById(Long id) {
        return repository.findById(id).get();
    }

    public Page<Task> getFilteredTasks(Specification<Task> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

}

