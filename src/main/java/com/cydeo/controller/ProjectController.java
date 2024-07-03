package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>getAllProjects() {
        List<ProjectDTO>projectDTOList=projectService.listAllProjects();
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "projects are listed",
                        projectDTOList,
                        HttpStatus.OK
                ));
    }
    @GetMapping("/{code}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>getProjectByCode(@PathVariable String code ) {
        ProjectDTO projectDTO=projectService.getByProjectCode(code);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "project found",
                        projectDTO,
                        HttpStatus.OK
                ));
    }
    @GetMapping("/manager/project-status")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>getProjectByManager() {
        List<ProjectDTO> projectDTOList=projectService.listAllProjectDetails();
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "project found",
                        projectDTOList,
                        HttpStatus.OK
                ));
    }
    @PutMapping("/manager/complete/{code}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>managerCompleteProject(@PathVariable String code) {
        projectService.complete(code);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "project completed",
                        HttpStatus.OK
                ));
    }


    @PostMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>createProject(@RequestBody ProjectDTO projectDTO) {
        projectService.save(projectDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper(
                        "project created",
                        HttpStatus.CREATED
                ));
    }

    @PutMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>updateProject(@RequestBody ProjectDTO projectDTO) {
        projectService.update(projectDTO);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "project updated",
                        HttpStatus.OK
                ));
    }

    @DeleteMapping("/{code}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>deleteProject(@PathVariable String code) {
        projectService.delete(code);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "project deleted",
                        HttpStatus.OK
                ));
    }

}
