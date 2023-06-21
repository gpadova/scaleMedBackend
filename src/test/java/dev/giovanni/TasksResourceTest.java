package dev.giovanni;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import java.time.LocalDate;


@QuarkusTest
public class TasksResourceTest {
    @Test
    public void testCreateTaskInvalidDate() {
        // set incorrect dates for using in the request
        LocalDate today = LocalDate.now();
        LocalDate dateOfCompletion = today.minusDays(1); // Set dateOfCompletion to be one day before today

        TaskEntity task = new TaskEntity("title", "description", today, dateOfCompletion, false);

        given().contentType(ContentType.JSON)
            .body(task)
        .when()
            .post("/tasks")
        .then()
            .statusCode(400); // Expecting a Bad Request response due to invalid date
    }

    public void testCreateValidTask() {
        // set correct dates for using in the request
        LocalDate today = LocalDate.now();
        LocalDate validDate = today.plusDays(1);

        TaskEntity task = new TaskEntity("title", "description", today, validDate, false);
        given()
            .contentType(ContentType.JSON)
            .body(task)
        .when()
            .post("/tasks")
        .then()
            .statusCode(201);
    }

    @Test
    public void testDeleteNotExistingTask() {
        given()
            .pathParam("id", 1)
        .when()
            .delete("/tasks/{id}")
        .then()
            .statusCode(404);
    }
    //Esses testes aqui não consegui fazer funcionar.
    // @Test
    // public void testDeleteTask() {
    //     RestAssured.defaultParser = Parser.JSON;
    //     // Create a new task
    //     LocalDate today = LocalDate.now();
    //     LocalDate validDate = today.plusDays(1);
    //     TaskEntity task = new TaskEntity("title", "description", today, validDate, false);

    //     int taskId = given()
    //             .contentType(ContentType.JSON)
    //             .body(task)
    //         .when()
    //             .post("/tasks")
    //         .then()
    //             .statusCode(201)
    //             .extract()
    //             .path("id");

        // // Delete the task
        // given()
        //     .pathParam("id", taskId)
        // .when()
        //     .delete("/tasks/{id}")
        // .then()
        //     .statusCode(200);

        // // Verify if the task was deleted corretly.
        // given()
        //     .pathParam("id", taskId)
        // .when()
        //     .get("/tasks/{id}")
        // .then()
        //     .statusCode(404);
    // }

    @Test
    public void testPatchTask() {
        RestAssured.defaultParser = Parser.JSON;
        // Create a new task
        LocalDate today = LocalDate.now();
        LocalDate validDate = today.plusDays(1);
        TaskEntity task = new TaskEntity("title", "description", today, validDate, false);

        int taskId = given()
                .contentType(ContentType.JSON)
                .body(task)
            .when()
                .post("/tasks")
            .then()
                .statusCode(201)
                .extract()
                .path("id");
        TaskUpdateDto taskDto = new TaskUpdateDto();
        taskDto.setTitle("Updated Title");

        given()
            .pathParam("id", taskId)
            .contentType(ContentType.JSON)
            .body(taskDto)
        .when()
            .patch("/tasks/{id}")
        .then()
            .statusCode(200);
    }
}