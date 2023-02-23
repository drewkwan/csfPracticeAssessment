package csf.mockAssessment.backend.models;

import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Posting {

    private String postId;
    private String postingDate;
    private String name;
    private String email;
    private String phone;
    private String title;
    private String description;
    // private byte[] image;
    private String image;

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
    public String getPostingDate() {
        return postingDate;
    }
    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    // public byte[] getImage() {
    //     return image;
    // }
    // public void setImage(byte[] image) {
    //     this.image = image;
    // }

    public JsonObject toJson() {
        return Json.createObjectBuilder()               
                .add("postingId", postId)
                .add("postingDate", postingDate. toString())
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("title", title)
                .add("description", description)
                .add("image", image)
                .build();
    }

    public static Posting fromCache(JsonObject doc) {
        Posting p = new Posting();
        p.setPostId(doc.getString("postingId"));
        p.setPostingDate(doc.getString("postingDate"));
        p.setName(doc.getString("name"));
        p.setEmail(doc.getString("email"));
        p.setPhone(doc.getString("phone"));
        p.setTitle(doc.getString("title"));
        p.setDescription(doc.getString("description"));
        p.setImage(doc.getString("image"));

        return p;
    }

    
    
}
