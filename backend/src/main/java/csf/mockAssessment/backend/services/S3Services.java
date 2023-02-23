package csf.mockAssessment.backend.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Services {

    @Autowired
    private AmazonS3 s3Client;
    
    public String upload(MultipartFile file) throws IOException {
        //User Data
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "drewkwan");
        userData.put("originalFileName", file.getOriginalFilename());

       //metadata
       ObjectMetadata metadata = new ObjectMetadata();
       metadata.setContentLength(file.getSize());
       metadata.setContentType(file.getContentType());
       metadata.setUserMetadata(userData);
       
       String key = UUID.randomUUID().toString().substring(0,8);
       System.out.println(key);

       //create put request
       PutObjectRequest putReq = new PutObjectRequest("drewkwan", "mockAssessment/%s".formatted(key), file.getInputStream(), metadata);

       //create with public access
       putReq.withCannedAcl(CannedAccessControlList.PublicRead);
       s3Client.putObject(putReq);
       System.out.println(">>>>>s3 file upload instance created");
       return key;
    }

}
