package csf.mockAssessment.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static csf.mockAssessment.backend.repositories.Queries.*;


import csf.mockAssessment.backend.models.Posting;

@Repository
public class PostingRepository {
    
    @Autowired 
    private JdbcTemplate jdbcTemplate;

    public Integer insertPosting(Posting posting) {
        return jdbcTemplate.update(SQL_INSERT_POSTING,
                                    posting.getPostId(),
                                    posting.getPostingDate(),
                                    posting.getName(),
                                    posting.getEmail(),
                                    posting.getPhone(),
                                    posting.getTitle(),
                                    posting.getDescription(),
                                    posting.getImage());
                                    // new ByteArrayInputStream(posting.getImage()));
    }
}
