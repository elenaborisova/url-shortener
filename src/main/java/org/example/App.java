package org.example;

public class App {

    public static void main(String[] args) {
        UrlShortener urlShortener = new UrlShortener();
        System.out.println(urlShortener.shortenUrl("something/something"));
        System.out.println(urlShortener.getShortUrl("something/something"));
    }


}
