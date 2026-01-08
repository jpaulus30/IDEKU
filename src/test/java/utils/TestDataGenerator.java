package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestDataGenerator {

    public static Map<String, Object> newUser() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Johanes Paulus");
        data.put("email", "email_" + UUID.randomUUID() + "@mail.com");
        data.put("gender", "male");
        data.put("status", "active");
        return data;
    }

    public static Map<String, Object> newPost(int userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("title", "Create Post");
        data.put("body", "Post created by automation test");
        return data;
    }

    public static Map<String, Object> newComment(int postId) {
        Map<String, Object> data = new HashMap<>();
        data.put("post_id", postId);
        data.put("name", "Create Comment");
        data.put("email", "comment_" + UUID.randomUUID() + "@mail.com");
        data.put("body", "Automation comment");
        return data;
    }

    public static Map<String, Object> newTodo(int userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("title", "Create Todo");
        data.put("status", "pending");
        return data;
    }
}