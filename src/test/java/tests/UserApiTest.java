package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import utils.TestDataGenerator;
import utils.TokenManager;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseTest {

    private int userId;
    private int postId;
    private int commentId;
    private int todoId;

    //Create User
    @Test(priority = 1)
    public void createUser() {
        userId =
                given()
                        .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
                        .contentType(ContentType.JSON)
                        .body(TestDataGenerator.newUser())
                .when()
                        .post("/users")
                .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .extract()
                        .path("id");

        System.out.println("User ID: " + userId);
    }

    //Update User
    @Test(priority = 2)
    public void updateUser() {
        given()
                .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Johanes Paulus Updated\"}")
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Johanes Paulus Updated"));
    }

    //Create Post
    @Test(priority = 3)
    public void createPost() {
        postId =
                given()
                        .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
                        .contentType(ContentType.JSON)
                        .body(TestDataGenerator.newPost(userId))
                        .when()
                        .post("/posts")
                        .then()
                        .statusCode(201)
                        .body("user_id", equalTo(userId))
                        .extract()
                        .path("id");

        System.out.println("Post ID: " + postId);
    }

    //Create TODO
    @Test(priority = 4)
    public void createTodo() {
        todoId =
                given()
                        .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
                        .contentType(ContentType.JSON)
                        .body(TestDataGenerator.newTodo(userId))
                .when()
                        .post("/todos")
                .then()
                        .statusCode(201)
                        .body("user_id", equalTo(userId))
                        .extract()
                        .path("id");

        System.out.println("Todo ID: " + todoId);
    }

    //Create Comment
    @Test(priority = 5)
    public void createComment() {
        commentId =
                given()
                        .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
                        .contentType(ContentType.JSON)
                        .body(TestDataGenerator.newComment(postId))
                .when()
                        .post("/comments")
                .then()
                        .statusCode(201)
                        .body("post_id", equalTo(postId))
                        .extract()
                        .path("id");

        System.out.println("Comment ID: " + commentId);
    }

    //Get User, Post, Comment, Todo
    @Test(priority = 6)
    public void getAllCreatedResources() {

        // GET USER
        given()
                .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
            .when()
                .get("/users/" + userId)
            .then()
                .statusCode(200)
                .body("id", equalTo(userId));

        // GET POST
        given()
                .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
            .when()
                .get("/posts/" + postId)
                .then()
                .statusCode(200)
                .body("id", equalTo(postId));

        // GET COMMENT
        given()
                .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
            .when()
                .get("/comments/" + commentId)
            .then()
                .statusCode(200)
                .body("id", equalTo(commentId));

        // GET TODO
        given()
                .log().all()
                .header("Authorization", "Bearer " + TokenManager.ACCESS_TOKEN)
            .when()
                .get("/todos/" + todoId)
            .then()
                .statusCode(200)
                .body("id", equalTo(todoId));
    }
}