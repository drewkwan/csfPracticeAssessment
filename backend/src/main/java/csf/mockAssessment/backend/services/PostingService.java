package csf.mockAssessment.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csf.mockAssessment.backend.models.Posting;
import csf.mockAssessment.backend.repositories.PostingRepository;

@Service
public class PostingService {

    @Autowired 
    private PostingRepository postingRepo;

    public boolean createPosting (Posting posting) {
        int count = postingRepo.insertPosting(posting);
        System.out.printf("Insert count:%d\n", count);
        return count>0;
    }
    
    
}
