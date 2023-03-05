package org.example;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class UrlShortener {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHARS.length();
    private static final int SHORT_URL_SIZE = 7;
    private static final String BASE_URL = "http://localhost:8080/";

    private final ConcurrentMap<String, String> shortToLongMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, String> longToShortMap = new ConcurrentHashMap<>();

    public String getBaseUrl() {
        return BASE_URL;
    }

    public String getShortUrl(String longUrl) {
        if (!longToShortMap.containsKey(longUrl)) {
            throw new IllegalStateException("The short URL you are trying to access does not exist.");
        }

        return BASE_URL + longToShortMap.get(longUrl);
    }

    public String getLongUrl(String shortUrl) {
        String shortUrlHash = shortUrl.substring(BASE_URL.length());

        if (!shortToLongMap.containsKey(shortUrlHash)) {
            throw new IllegalStateException("The long URL you are trying to access does not exist.");
        }

        return shortToLongMap.get(shortUrlHash);
    }

    public synchronized String shortenUrl(String longUrl) {
        if (longToShortMap.containsKey(longUrl)) {
            return BASE_URL + longToShortMap.get(longUrl);
        }

        String shortUrlHash = generateHash();
        while (shortToLongMap.containsKey(shortUrlHash)) {
            shortUrlHash = generateHash();
        }

        shortToLongMap.put(shortUrlHash, longUrl);
        longToShortMap.put(longUrl, shortUrlHash);

        return BASE_URL + shortUrlHash;
    }

    private String generateHash() {
        Random random = new Random();
        StringBuilder shortUrlHash = new StringBuilder();

        for (int i = 0; i < SHORT_URL_SIZE; i++) {
            shortUrlHash.append(CHARS.charAt(random.nextInt(BASE)));
        }

        return shortUrlHash.toString();
    }
}