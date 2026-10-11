package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.ExamCalendarEvent;
import com.alperenakademi.yks.model.NewsItem;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WebFeedService {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<NewsItem> loadYksNews() {
        String url = "https://news.google.com/rss/search?q=YKS&hl=tr&gl=TR&ceid=TR:tr";
        List<NewsItem> items = parseNewsRss(url, 15);
        if (!items.isEmpty()) {
            return items;
        }
        List<NewsItem> fallback = new ArrayList<>();
        fallback.add(new NewsItem(LocalDate.now(), "YKS ile ilgili haberler bağlantı kurulduğunda listelenecek.", "Sistem"));
        return fallback;
    }

    public List<ExamCalendarEvent> loadTurkeyGeneralExams() {
        String url = "https://news.google.com/rss/search?q=turkiye+geneli+deneme+sinavi&hl=tr&gl=TR&ceid=TR:tr";
        List<ExamCalendarEvent> events = parseExamRss(url, 20);
        if (!events.isEmpty()) {
            return events;
        }
        List<ExamCalendarEvent> fallback = new ArrayList<>();
        ExamCalendarEvent sample = new ExamCalendarEvent();
        sample.setDate(LocalDate.now());
        sample.setPublisher("Sistem");
        sample.setTitle("İnternet bağlantısı olmadığı için takvim verisi çekilemedi.");
        fallback.add(sample);
        return fallback;
    }

    private List<NewsItem> parseNewsRss(String url, int limit) {
        List<NewsItem> results = new ArrayList<>();
        try {
            Document doc = loadDocument(url);
            NodeList itemNodes = doc.getElementsByTagName("item");
            for (int i = 0; i < Math.min(limit, itemNodes.getLength()); i++) {
                NodeList children = itemNodes.item(i).getChildNodes();
                String title = getTagText(children, "title");
                String pubDate = getTagText(children, "pubDate");
                String source = getTagText(children, "source");
                if (source.isBlank()) {
                    source = "Google News";
                }
                String link = getTagText(children, "link");
                results.add(new NewsItem(parseDate(pubDate), cleanTitle(title), source, link));
            }
        } catch (Exception ignored) {
        }
        return results;
    }

    private List<ExamCalendarEvent> parseExamRss(String url, int limit) {
        List<ExamCalendarEvent> results = new ArrayList<>();
        try {
            Document doc = loadDocument(url);
            NodeList itemNodes = doc.getElementsByTagName("item");
            for (int i = 0; i < Math.min(limit, itemNodes.getLength()); i++) {
                NodeList children = itemNodes.item(i).getChildNodes();
                String title = getTagText(children, "title");
                String pubDate = getTagText(children, "pubDate");
                String source = getTagText(children, "source");
                ExamCalendarEvent event = new ExamCalendarEvent();
                event.setDate(parseDate(pubDate));
                event.setPublisher(source.isBlank() ? "Google News" : source);
                event.setTitle(cleanTitle(title));
                event.setLink(getTagText(children, "link"));
                results.add(event);
            }
        } catch (Exception ignored) {
        }
        return results;
    }

    private Document loadDocument(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("User-Agent", "Mozilla/5.0")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(response.body().getBytes()));
    }

    private String getTagText(org.w3c.dom.NodeList children, String tagName) {
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equalsIgnoreCase(tagName)) {
                return children.item(i).getTextContent();
            }
        }
        return "";
    }

    private LocalDate parseDate(String rssDate) {
        try {
            return ZonedDateTime.parse(rssDate, DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDate();
        } catch (Exception ignored) {
            return LocalDate.now();
        }
    }

    private String cleanTitle(String title) {
        if (title == null) {
            return "";
        }
        return title.replace(" - Google News", "").trim();
    }
}
