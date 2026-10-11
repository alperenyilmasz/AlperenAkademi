package com.alperenakademi.yks.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class NewsItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private LocalDate date;
    private String title;
    private String source;
    private String link;

    public NewsItem() {
    }

    public NewsItem(LocalDate date, String title, String source) {
        this.date = date;
        this.title = title;
        this.source = source;
    }

    public NewsItem(LocalDate date, String title, String source, String link) {
        this.date = date;
        this.title = title;
        this.source = source;
        this.link = link;
    }

    public LocalDate getDate() { return date; }
    public String getTitle() { return title; }
    public String getSource() { return source; }
    public String getLink() { return link; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTitle(String title) { this.title = title; }
    public void setSource(String source) { this.source = source; }
    public void setLink(String link) { this.link = link; }
}
