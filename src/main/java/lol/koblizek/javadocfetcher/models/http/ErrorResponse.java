package lol.koblizek.javadocfetcher.models.http;

public record ErrorResponse(String message, StackTraceElement[] stackTrace) {
}
