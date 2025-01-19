package at.ac.fhstp.eshop.ordering.exceptions;

public class ArticleUnavailableException extends RuntimeException {
    public ArticleUnavailableException(String message) {
        super(message);
    }
}
