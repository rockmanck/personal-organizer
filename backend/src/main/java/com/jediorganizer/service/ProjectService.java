package com.jediorganizer.service;

import com.jediorganizer.model.Project;
import com.jediorganizer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Project entity business logic.
 * Handles project management operations implementing Jedi Techniques "Plan" mode.
 */
@Service
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    
    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    /**
     * Create a new project
     */
    public Project createProject(Project project) {
        if (project.getUserId() == null) {
            throw new IllegalArgumentException("Project must have a user ID");
        }
        return projectRepository.save(project);
    }
    
    /**
     * Find project by ID
     */
    public Optional<Project> findById(String id) {
        return projectRepository.findById(id);
    }
    
    /**
     * Find project by ID and verify user ownership
     */
    public Optional<Project> findByIdAndUserId(String id, String userId) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent() && project.get().getUserId().equals(userId)) {
            return project;
        }
        return Optional.empty();
    }
    
    /**
     * Get all projects for a user
     */
    public List<Project> getUserProjects(String userId) {
        return projectRepository.findByUserId(userId);
    }
    
    /**
     * Get active projects for a user
     */
    public List<Project> getActiveProjects(String userId) {
        return projectRepository.findByUserIdAndStatus(userId, Project.ProjectStatus.ACTIVE);
    }
    
    /**
     * Get projects by status
     */
    public List<Project> getProjectsByStatus(String userId, Project.ProjectStatus status) {
        return projectRepository.findByUserIdAndStatus(userId, status);
    }
    
    /**
     * Get high priority projects (priority 1-2)
     */
    public List<Project> getHighPriorityProjects(String userId) {
        return projectRepository.findByUserIdAndPriorityLessThanEqual(userId, 2);
    }
    
    /**
     * Get overdue projects
     */
    public List<Project> getOverdueProjects(String userId) {
        return projectRepository.findByUserIdAndDueDateBeforeAndStatusNot(
            userId, 
            LocalDateTime.now(), 
            Project.ProjectStatus.COMPLETED
        );
    }
    
    /**
     * Update project
     */
    public Project updateProject(Project project) {
        if (project.getId() == null) {
            throw new IllegalArgumentException("Project ID cannot be null for update");
        }
        return projectRepository.save(project);
    }
    
    /**
     * Update project status
     */
    public Project updateProjectStatus(String projectId, String userId, Project.ProjectStatus status) {
        Optional<Project> projectOpt = findByIdAndUserId(projectId, userId);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            project.setStatus(status);
            return projectRepository.save(project);
        }
        throw new IllegalArgumentException("Project not found or access denied");
    }
    
    /**
     * Complete a project
     */
    public Project completeProject(String projectId, String userId) {
        return updateProjectStatus(projectId, userId, Project.ProjectStatus.COMPLETED);
    }
    
    /**
     * Archive a project
     */
    public Project archiveProject(String projectId, String userId) {
        return updateProjectStatus(projectId, userId, Project.ProjectStatus.ARCHIVED);
    }
    
    /**
     * Put project on hold
     */
    public Project putProjectOnHold(String projectId, String userId) {
        return updateProjectStatus(projectId, userId, Project.ProjectStatus.ON_HOLD);
    }
    
    /**
     * Reactivate project
     */
    public Project reactivateProject(String projectId, String userId) {
        return updateProjectStatus(projectId, userId, Project.ProjectStatus.ACTIVE);
    }
    
    /**
     * Search projects by title
     */
    public List<Project> searchProjectsByTitle(String userId, String searchTerm) {
        return projectRepository.findByUserIdAndTitleContainingIgnoreCase(userId, searchTerm);
    }
    
    /**
     * Get recently updated projects
     */
    public List<Project> getRecentlyUpdatedProjects(String userId, int daysBack) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysBack);
        return projectRepository.findByUserIdAndUpdatedAtAfter(userId, cutoffDate);
    }
    
    /**
     * Get new projects (created within specified days)
     */
    public List<Project> getNewProjects(String userId, int daysBack) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysBack);
        return projectRepository.findByUserIdAndCreatedAtAfter(userId, cutoffDate);
    }
    
    /**
     * Get project statistics for a user
     */
    public ProjectStatistics getProjectStatistics(String userId) {
        long totalProjects = projectRepository.findByUserId(userId).size();
        long activeProjects = projectRepository.countByUserIdAndStatus(userId, Project.ProjectStatus.ACTIVE);
        long completedProjects = projectRepository.countByUserIdAndStatus(userId, Project.ProjectStatus.COMPLETED);
        long onHoldProjects = projectRepository.countByUserIdAndStatus(userId, Project.ProjectStatus.ON_HOLD);
        long archivedProjects = projectRepository.countByUserIdAndStatus(userId, Project.ProjectStatus.ARCHIVED);
        
        List<Project> overdueProjects = getOverdueProjects(userId);
        List<Project> highPriorityProjects = getHighPriorityProjects(userId);
        
        return new ProjectStatistics(
            totalProjects, activeProjects, completedProjects, onHoldProjects, archivedProjects,
            overdueProjects.size(), highPriorityProjects.size()
        );
    }
    
    /**
     * Delete project (use with caution)
     */
    public void deleteProject(String projectId, String userId) {
        Optional<Project> project = findByIdAndUserId(projectId, userId);
        if (project.isPresent()) {
            projectRepository.deleteById(projectId);
        } else {
            throw new IllegalArgumentException("Project not found or access denied");
        }
    }
    
    /**
     * Project statistics data class
     */
    public static class ProjectStatistics {
        private final long totalProjects;
        private final long activeProjects;
        private final long completedProjects;
        private final long onHoldProjects;
        private final long archivedProjects;
        private final long overdueProjects;
        private final long highPriorityProjects;
        
        public ProjectStatistics(long totalProjects, long activeProjects, long completedProjects,
                               long onHoldProjects, long archivedProjects, long overdueProjects,
                               long highPriorityProjects) {
            this.totalProjects = totalProjects;
            this.activeProjects = activeProjects;
            this.completedProjects = completedProjects;
            this.onHoldProjects = onHoldProjects;
            this.archivedProjects = archivedProjects;
            this.overdueProjects = overdueProjects;
            this.highPriorityProjects = highPriorityProjects;
        }
        
        // Getters
        public long getTotalProjects() { return totalProjects; }
        public long getActiveProjects() { return activeProjects; }
        public long getCompletedProjects() { return completedProjects; }
        public long getOnHoldProjects() { return onHoldProjects; }
        public long getArchivedProjects() { return archivedProjects; }
        public long getOverdueProjects() { return overdueProjects; }
        public long getHighPriorityProjects() { return highPriorityProjects; }
    }
}
