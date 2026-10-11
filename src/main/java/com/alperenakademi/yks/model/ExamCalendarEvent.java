package com.alperenakademi.yks.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class ExamCalendarEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private LocalDate date;
    private String publisher;
    private String title;
    private String link;

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}
