package com.jediorganizer.controller;

import com.jediorganizer.model.Project;
import com.jediorganizer.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Project management operations.
 * Implements Jedi Techniques "Plan" mode functionality.
 */
@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Projects", description = "Project management for Jedi Techniques Plan mode")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Get all projects for the current user
     */
    @GetMapping
    @Operation(summary = "Get all user projects")
    public ResponseEntity<List<Project>> getUserProjects() {
        // TODO: Get user ID from authentication context
        String userId = "demo-user-123"; // Placeholder
        List<Project> projects = projectService.getUserProjects(userId);
        return ResponseEntity.ok(projects);
    }

    /**
     * Get active projects for the current user
     */
    @GetMapping("/active")
    @Operation(summary = "Get active projects")
    public ResponseEntity<List<Project>> getActiveProjects() {
        String userId = "demo-user-123"; // Placeholder
        List<Project> projects = projectService.getActiveProjects(userId);
        return ResponseEntity.ok(projects);
    }

    /**
     * Get project by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get project by ID")
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        String userId = "demo-user-123"; // Placeholder
        Optional<Project> project = projectService.findByIdAndUserId(id, userId);
        return project.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new project
     */
    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            String userId = "demo-user-123"; // Placeholder
            project.setUserId(userId);
            Project createdProject = projectService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update project
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update project")
    public ResponseEntity<Project> updateProject(@PathVariable String id, @RequestBody Project project) {
        try {
            String userId = "demo-user-123"; // Placeholder

            // Verify project exists and belongs to user
            Optional<Project> existingProject = projectService.findByIdAndUserId(id, userId);
            if (existingProject.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            project.setId(id);
            project.setUserId(userId);
            Project updatedProject = projectService.updateProject(project);
            return ResponseEntity.ok(updatedProject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Complete a project
     */
    @PatchMapping("/{id}/complete")
    @Operation(summary = "Complete a project")
    public ResponseEntity<Project> completeProject(@PathVariable String id) {
        try {
            String userId = "demo-user-123"; // Placeholder
            Project project = projectService.completeProject(id, userId);
            return ResponseEntity.ok(project);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Archive a project
     */
    @PatchMapping("/{id}/archive")
    @Operation(summary = "Archive a project")
    public ResponseEntity<Project> archiveProject(@PathVariable String id) {
        try {
            String userId = "demo-user-123"; // Placeholder
            Project project = projectService.archiveProject(id, userId);
            return ResponseEntity.ok(project);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get high priority projects
     */
    @GetMapping("/high-priority")
    @Operation(summary = "Get high priority projects")
    public ResponseEntity<List<Project>> getHighPriorityProjects() {
        String userId = "demo-user-123"; // Placeholder
        List<Project> projects = projectService.getHighPriorityProjects(userId);
        return ResponseEntity.ok(projects);
    }

    /**
     * Get overdue projects
     */
    @GetMapping("/overdue")
    @Operation(summary = "Get overdue projects")
    public ResponseEntity<List<Project>> getOverdueProjects() {
        String userId = "demo-user-123"; // Placeholder
        List<Project> projects = projectService.getOverdueProjects(userId);
        return ResponseEntity.ok(projects);
    }

    /**
     * Search projects by title
     */
    @GetMapping("/search")
    @Operation(summary = "Search projects by title")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String query) {
        String userId = "demo-user-123"; // Placeholder
        List<Project> projects = projectService.searchProjectsByTitle(userId, query);
        return ResponseEntity.ok(projects);
    }

    /**
     * Get project statistics
     */
    @GetMapping("/stats")
    @Operation(summary = "Get project statistics")
    public ResponseEntity<ProjectService.ProjectStatistics> getProjectStatistics() {
        String userId = "demo-user-123"; // Placeholder
        ProjectService.ProjectStatistics stats = projectService.getProjectStatistics(userId);
        return ResponseEntity.ok(stats);
    }

    /**
     * Delete project
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        try {
            String userId = "demo-user-123"; // Placeholder
            projectService.deleteProject(id, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
