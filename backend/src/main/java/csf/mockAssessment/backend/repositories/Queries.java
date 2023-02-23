package csf.mockAssessment.backend.repositories;

public class Queries {
    public static final String SQL_INSERT_POSTING = "INSERT INTO postings(posting_id, posting_date, name, email, phone, title, description, image) values(?,?,?,?,?,?,?,?)";
}
