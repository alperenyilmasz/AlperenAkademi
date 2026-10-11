package com.alperenakademi.yks.repository;

import com.alperenakademi.yks.model.AppData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SerializedAppDataRepository implements AppDataRepository {
    private final Path dataPath;

    public SerializedAppDataRepository() throws IOException {
        Path appDir = Path.of(System.getProperty("user.home"), ".alperen-akademi");
        Files.createDirectories(appDir);
        this.dataPath = appDir.resolve("yks-data-v2.bin");
    }

    @Override
    public AppData load() throws IOException {
        if (!Files.exists(dataPath)) {
            return new AppData();
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(dataPath))) {
            return (AppData) ois.readObject();
        } catch (InvalidClassException ex) {
            // Model serialVersionUID değiştiğinde eski dosyayı yedekleyip temiz başlat.
            backupIncompatibleFile();
            return new AppData();
        } catch (ClassNotFoundException | StreamCorruptedException | EOFException | RuntimeException ex) {
            return new AppData();
        }
    }

    @Override
    public void save(AppData data) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(dataPath))) {
            oos.writeObject(data);
        }
    }

    private void backupIncompatibleFile() {
        try {
            Path backup = dataPath.resolveSibling("yks-data-v2-incompatible-backup.bin");
            Files.copy(dataPath, backup, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(dataPath);
        } catch (IOException ignored) {
        }
    }
}
