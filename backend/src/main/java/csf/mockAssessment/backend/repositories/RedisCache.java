package csf.mockAssessment.backend.repositories;

import java.io.StringReader;
import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import csf.mockAssessment.backend.configs.RedisConfig;
import csf.mockAssessment.backend.models.Posting;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class RedisCache {
    @Autowired
    @Qualifier(RedisConfig.CACHE_POSTINGS)
    private RedisTemplate<String, String> redisTemplate;

    public void cache(Posting posting) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(posting.getPostId(), posting.toJson().toString(), Duration.ofMinutes(15));
    }

    public Optional<Posting> get (String postId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get(postId);
        if (value==null) {
            System.out.printf("No post with such id %s".formatted(postId));
            return Optional.empty();
        }

        JsonReader reader = Json.createReader(new StringReader(value));
        JsonObject results = reader.readObject();
        Posting p = Posting.fromCache(results);
        return Optional.of(p);
    }

    public void removeFromCache(String postId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.getAndDelete(postId);
    }
}
