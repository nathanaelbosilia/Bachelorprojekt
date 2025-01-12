package at.ac.fhstp.eshop.common.exceptions;

public class ArticleUnavailableException extends RuntimeException {
    public ArticleUnavailableException(String message) {
        super(message);
    }
}
