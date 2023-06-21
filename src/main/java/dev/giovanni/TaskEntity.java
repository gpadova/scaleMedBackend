package dev.giovanni;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class TaskEntity extends PanacheEntity{
    @NotBlank(message = "The title must be provided")
    public String title;
    
    @NotBlank(message = "The description must be provided")
    public String description;

    public LocalDate dateOfCreation;

    @NotNull(message = "The date of completion must be provided")
    @Future()
    public LocalDate dateOfCompletion;

    public boolean concluded;

    public TaskEntity() {}

    public TaskEntity(String title, String description, LocalDate dateOfCreation, LocalDate dateOfCompletion, boolean concluded) {
        this.title = title;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.dateOfCompletion = dateOfCompletion;
        this.concluded = concluded;
    }
    
}
