package csf.mockAssessment.backend.controllers;

import java.io.IOException;
import java.lang.StackWalker.Option;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.Response;

import csf.mockAssessment.backend.models.Posting;
import csf.mockAssessment.backend.repositories.RedisCache;
import csf.mockAssessment.backend.services.PostingService;
import csf.mockAssessment.backend.services.S3Services;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping(path="/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class PostingController {


    @Autowired
    private PostingService postingSvc;

    @Autowired
    private S3Services s3Svc;

    @Autowired
    private RedisCache redisCache;

    @PostMapping(path="/posting", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> insertPosting(
        @RequestPart MultipartFile image,
        @RequestPart String name,
        @RequestPart String email,
        @RequestPart String phone,
        @RequestPart String title,
        @RequestPart String description        
    ) {
        Date currDate = new Date();
        //create the posting
        Posting p = new Posting();
        p.setPostId(UUID.randomUUID().toString().substring(0,8));
        p.setPostingDate(currDate.toString());
        p.setName(name);
        p.setEmail(email);
        p.setDescription(description);
        p.setPhone(phone);
        p.setTitle(title);
        // try{
        //     p.setImage(image.getBytes());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        
        //Saving the image in s3 and get the key to set the image
        String key="";

        try {
            key = s3Svc.upload(image);
        } catch(IOException e) {
            e.printStackTrace();
        }  
        p.setImage("https://drewkwan.sgp1.digitaloceanspaces.com/mockAssessment%2F"+key);
        //the imageurl is hardcoded with my bucket. Change accordingly

        //save to redis
        redisCache.cache(p);

        //return payload

        JsonObject payload = p.toJson();
        return ResponseEntity.ok(payload.toString());
        
        
        // //insert the post, if it fails return error
        // if (!postingSvc.createPosting(p)) {
        //     JsonObject error = Json.createObjectBuilder().add("error", "Could not create post for posting!").build();
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.toString());
        // }

    }

    @GetMapping(path="/posting/{postId}")
    @ResponseBody
    public ResponseEntity<String> getPosting(@PathVariable String postId) {
        Optional<Posting> opt = redisCache.get(postId);
        if (opt.isEmpty()) {
            JsonObject error = Json.createObjectBuilder().add("error", "Could not find the posting").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
        }
        return ResponseEntity.ok(opt.get().toJson().toString());

    }

    @PutMapping(path="/posting/{postId}", produces=MediaType.APPLICATION_JSON_VALUE) 
    @ResponseBody
    public ResponseEntity<String> putPosting(@PathVariable String postId) {
        //retrieve the posting from Redis with the postId
        Optional<Posting> opt = redisCache.get(postId);
        //if posting not found, return error 
        if (opt.isEmpty()) {
            JsonObject error = Json.createObjectBuilder().add("error", "Posting id %s not found".formatted(postId)).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
        }
        Posting p = opt.get();
        //delete the posting from redis
        redisCache.removeFromCache(postId);
        //save the detauls of the posting in mysql
        if(!postingSvc.createPosting(p)) {
            JsonObject error = Json.createObjectBuilder().add("error", "Posting for %s failed".formatted(postId)).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.toString());
        }
        //send an ok status code
        JsonObject payload = Json.createObjectBuilder().add("message", "Accepted %s".formatted(postId)).build();
        return ResponseEntity.ok(payload.toString());

    }



}
