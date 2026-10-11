package com.alperenakademi.yks;

/**
 * jpackage / jlink paketlemesi için giriş noktası.
 * Ana sınıf doğrudan {@link Application} alt sınıfı olmamalı (classpath modu).
 */
public final class Launcher {
    private Launcher() {
    }

    public static void main(String[] args) {
        MainApp.main(args);
    }
}
