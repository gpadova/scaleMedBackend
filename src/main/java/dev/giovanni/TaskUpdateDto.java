package dev.giovanni;

import java.time.LocalDate;

public class TaskUpdateDto {
    private String title;
    private String description;
    private LocalDate dateOfCompletion;
    private boolean concluded;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfCompletion() {
        return this.dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDate dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public boolean getConcluded() {
        return this.concluded;
    }

    public void setConcluded(boolean concluded) {
        this.concluded = concluded;
    }
}
