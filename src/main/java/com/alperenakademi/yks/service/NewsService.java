package com.alperenakademi.yks.service;

import com.alperenakademi.yks.model.NewsItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsService {
    public List<NewsItem> loadNews() {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem(LocalDate.now(), "ÖSYM sınav takvimi güncellendi.", "ÖSYM"));
        list.add(new NewsItem(LocalDate.now().minusDays(1), "YÖK kontenjan duyurusu yayımlandı.", "YÖK"));
        return list;
    }
}
