package dev.giovanni;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/tasks")
public class TasksResource {

    @Inject
    Validator validator;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskEntity> list() {
        return TaskEntity.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional()
    public Response newTaskEntity(TaskEntity TaskEntity) {
        LocalDate today = LocalDate.now();
        LocalDate dateOfCompletion = TaskEntity.dateOfCompletion;

        if (dateOfCompletion != null && dateOfCompletion.isBefore(today)) {
            throw new WebApplicationException("Invalid date of completion", Response.Status.BAD_REQUEST);
        }

        Set<ConstraintViolation<TaskEntity>> violations = validator.validate(TaskEntity);
        if (violations.isEmpty()) {
            TaskEntity createdTaskEntity = new TaskEntity(TaskEntity.title, TaskEntity.description, today,
                    TaskEntity.dateOfCompletion,
                    false);
            createdTaskEntity.persist();
            return Response.status(Status.CREATED).build();
        } else {
            String errorMessage = violations.stream().map(violation -> violation.getMessage()).collect(Collectors.joining(", "));
            return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
        }
    }

    @DELETE
    @Transactional()
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") int id) {
        TaskEntity deletedTask = TaskEntity.findById(id, null);

        if (deletedTask == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        deletedTask.delete();

        return Response.ok(deletedTask, MediaType.APPLICATION_JSON).build();
    }

    @PATCH
    @Transactional()
    @Path("/{id}")
    public Response patchTask(@PathParam("id") int id, TaskUpdateDto taskToUpdateDto) {
        TaskEntity changedTask = TaskEntity.findById(id, null);
        if (changedTask == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (taskToUpdateDto.getTitle() != null) {
            changedTask.title = taskToUpdateDto.getTitle();
        }
        if (taskToUpdateDto.getDescription() != null) {
            changedTask.description = taskToUpdateDto.getDescription();
        }
        if (taskToUpdateDto.getDateOfCompletion() != null) {
            changedTask.dateOfCompletion = taskToUpdateDto.getDateOfCompletion();
        }
        if (taskToUpdateDto.getConcluded() || !taskToUpdateDto.getConcluded()) {
            changedTask.concluded = taskToUpdateDto.getConcluded();
        }

        return Response.ok(changedTask, MediaType.APPLICATION_JSON).build();
    }
}
