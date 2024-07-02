package com.cydeo.controller;


import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper>getTasks(){
        List<TaskDTO>taskDTOList=taskService.listAllTasks();
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "Tasks are listed",
                                taskDTOList,
                                HttpStatus.OK
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper>getTasksById(@PathVariable Long id){
        TaskDTO taskDTO=taskService.findById(id);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "Task found",
                                taskDTO,
                                HttpStatus.OK
                        )
                );

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper>creatTasks(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseWrapper(
                                "Tasks created",
                                HttpStatus.CREATED
                        )
                );
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper>updateTasks(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "Task updated",
                                HttpStatus.OK
                        )
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper>deleteTasks(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "Task deleted",
                                HttpStatus.OK
                        )
                );
    }

    @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper>employeePendingTasks(){
        List<TaskDTO>taskDTOList=taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "these are pending tasks",
                                taskDTOList,
                                HttpStatus.OK
                        )
                );
    }

    @PutMapping("/employee/update")
    public ResponseEntity<ResponseWrapper>employeeUpdateTasks(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "Task updated",
                                HttpStatus.OK
                        )
                );
    }

    @GetMapping("/employee/archive")
    public ResponseEntity<ResponseWrapper>employeeArchiveTasks(){
        List<TaskDTO>taskDTOList=taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "these are archived tasks",
                                taskDTOList,
                                HttpStatus.OK
                        )
                );

    }
}
