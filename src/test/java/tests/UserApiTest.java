package tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import utils.RequestSpec;
import utils.TestDataGenerator;
import utils.TokenManager;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest {

    private int userId;
    private int postId;
    private int commentId;
    private int todoId;

    //Create User
    @Test(priority = 1)
    public void createUser() {
        userId =
                given()
                        .spec(RequestSpec.spec())
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
                .spec(RequestSpec.spec())
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
                        .spec(RequestSpec.spec())
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
                        .spec(RequestSpec.spec())
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
                        .spec(RequestSpec.spec())
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
                .spec(RequestSpec.spec())
            .when()
                .get("/users/" + userId)
            .then()
                .statusCode(200)
                .body("id", equalTo(userId));

        // GET POST
        given()
                .spec(RequestSpec.spec())
            .when()
                .get("/posts/" + postId)
                .then()
                .statusCode(200)
                .body("id", equalTo(postId));

        // GET COMMENT
        given()
                .spec(RequestSpec.spec())
            .when()
                .get("/comments/" + commentId)
            .then()
                .statusCode(200)
                .body("id", equalTo(commentId));

        // GET TODO
        given()
                .log().all()
                .spec(RequestSpec.spec())
            .when()
                .get("/todos/" + todoId)
            .then()
                .statusCode(200)
                .body("id", equalTo(todoId));
    }
}