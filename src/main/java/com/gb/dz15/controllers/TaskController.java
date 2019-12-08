package com.gb.dz15.controllers;

import com.gb.dz15.entities.Employee;
import com.gb.dz15.entities.Task;
import com.gb.dz15.repositories.specifications.TaskSpecifications;
import com.gb.dz15.services.EmployeeService;
import com.gb.dz15.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {
    private TaskService taskService;
    private EmployeeService employeeService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // http://localhost:8189/app/tasks/show
    @RequestMapping(path = "tasks/show", method = RequestMethod.GET)
    public String showTasks(Model model,
                            @RequestParam(defaultValue = "1") Long pageNumber,
                            @RequestParam(required = false) Long id,
                            @RequestParam(required = false) Task.TaskStatus status) {
        int productsPerPage = 5;
        if (pageNumber < 1L) {
            pageNumber = 1L;
        }
        ArrayList<Task> tasks = new ArrayList<>();
        Specification<Task> spec = Specification.where(null);
        if (id != null) {
            spec = spec.and(TaskSpecifications.idHaved(id));
        } else if (status != null) {
            spec = spec.and(TaskSpecifications.statusHaved(status));
        }
        Page<Task> taskPage = taskService.getFilteredTasks(spec, PageRequest.of(pageNumber.intValue() - 1, productsPerPage, Sort.Direction.ASC, "name"));

        model.addAttribute("taskPage", taskPage);
        model.addAttribute("taskStatuses", Task.TaskStatus.values());
        return "task_templates/all_tasks";
    }

    // http://localhost:8189/app/tasks/add
    @RequestMapping(path = "tasks/add", method = RequestMethod.GET)
    public String addTask(Model model) {
        List<Employee> employees = employeeService.getAll();

        Task task = new Task();
        task.owner = employees.get(0);
        task.assignee = employees.get(0);
        task.status = Task.TaskStatus.Open;

        model.addAttribute("task", task);
        model.addAttribute("taskStatuses", Task.TaskStatus.values());
        model.addAttribute("employees", employees);
        return "task_templates/add_task";
    }

    @PostMapping(path = "tasks/add")
    public String addTaskResult(@ModelAttribute("task") Task task,
                                @RequestParam Long ownerId,
                                @RequestParam Long assigneeId,
                                @RequestParam Task.TaskStatus status,
                                Model model) {
        task.owner = employeeService.getOneById(ownerId);
        task.assignee = employeeService.getOneById(assigneeId);
        task.status = status;
        taskService.save(task);
        return "task_templates/add_task_result";
    }

    // http://localhost:8189/app/tasks/{id}
    @RequestMapping(path = "tasks/{id}", method = RequestMethod.GET)
    public String taskInfo(Model model, @PathVariable Long id) {
        Task task = taskService.getOneById(id);
        model.addAttribute("task", task);
        return "task_templates/task_info";
    }

    // http://localhost:8189/app/tasks/remove/{id}
    @RequestMapping(path = "tasks/remove/{id}", method = RequestMethod.GET)
    public String removeTask(Model model, @PathVariable Long id) {
        Task deletedTask = taskService.getOneById(id);
        taskService.delete(deletedTask);
        model.addAttribute("deletedTaskId", id);
        return "task_templates/remove_task_result";
    }
}

