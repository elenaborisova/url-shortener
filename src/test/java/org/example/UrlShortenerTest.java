package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class UrlShortenerTest {

    private UrlShortener urlShortener;

    @BeforeEach
    public void setUp() {
        urlShortener = new UrlShortener();
    }

    @Test
    public void testShortenUrl_whenDoesntExist_shouldSaveAndReturnShortUrl() {
        String longUrl = "something/something";

        String shortUrl = urlShortener.shortenUrl(longUrl);

        Assertions.assertEquals(7, shortUrl.length() - urlShortener.getBaseUrl().length());
        Assertions.assertEquals(shortUrl, urlShortener.getShortUrl(longUrl));
        Assertions.assertEquals(longUrl, urlShortener.getLongUrl(shortUrl));
    }

    @Test
    public void testShortenUrl_whenExists_shouldReturnShortUrl() {
        String longUrl = "something/something";

        urlShortener.shortenUrl(longUrl);
        String shortUrl = urlShortener.shortenUrl(longUrl);

        Assertions.assertEquals(shortUrl, urlShortener.getShortUrl(longUrl));
        Assertions.assertEquals(longUrl, urlShortener.getLongUrl(shortUrl));
    }

    @Test
    public void testGetShortUrl_whenExists_shouldReturnShortUrl() {
        String longUrl = "something/something";

        String shortUrl = urlShortener.shortenUrl(longUrl);

        String res = urlShortener.getShortUrl(longUrl);

        Assertions.assertEquals(shortUrl, res);
    }

    @Test
    public void testGetShortUrl_whenDosNotExists_shouldThrowException() {
        String longUrl = "something/something";

        Throwable exception = Assertions.assertThrows(IllegalStateException.class, () -> urlShortener.getShortUrl(longUrl));
        Assertions.assertEquals("The short URL you are trying to access does not exist.", exception.getMessage());
    }

    @Test
    public void testGetLongUrl_whenExists_shouldReturnLongUrl() {
        String longUrl = "something/something";

        String shortUrl = urlShortener.shortenUrl(longUrl);

        String res = urlShortener.getLongUrl(shortUrl);

        Assertions.assertEquals(longUrl, res);
    }

    @Test
    public void testGetLongUrl_whenDosNotExists_shouldThrowException() {
        String shortUrl = urlShortener.getBaseUrl() + "12a456j";

        Throwable exception = Assertions.assertThrows(IllegalStateException.class, () -> urlShortener.getLongUrl(shortUrl));
        Assertions.assertEquals("The long URL you are trying to access does not exist.", exception.getMessage());
    }
}
