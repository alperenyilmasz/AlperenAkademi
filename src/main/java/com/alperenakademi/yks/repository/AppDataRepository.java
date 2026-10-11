package com.alperenakademi.yks.repository;

import com.alperenakademi.yks.model.AppData;

import java.io.IOException;

public interface AppDataRepository {
    AppData load() throws IOException;
    void save(AppData data) throws IOException;
}
