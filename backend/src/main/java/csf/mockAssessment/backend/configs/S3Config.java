package csf.mockAssessment.backend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
    
    // @Value("${ACCESS_KEY}")
    @Value("${access.key}")
    private String accessKey;

    // @Value("${SECRET_KEY}")
    @Value("${secret.key}")
    private String secretKey;

    @Bean
    public AmazonS3 
     createS3Client() {
        //1. Create credential
        BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);
        System.out.println(">>>>>> acccess key: " + accessKey + "secret key: " + secretKey);

        //2. Create Endpoint
        EndpointConfiguration ep = new EndpointConfiguration("sgp1.digitaloceanspaces.com", "sgp1");
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(ep)
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();
            

    }
}
