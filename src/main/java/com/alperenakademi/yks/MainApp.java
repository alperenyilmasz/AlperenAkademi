package com.alperenakademi.yks;

import com.alperenakademi.yks.controller.MainController;
import com.alperenakademi.yks.service.AppStateService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AppStateService appStateService = new AppStateService();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/MainLayout.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 760);
        scene.getStylesheets().add(MainApp.class.getResource("/view/app.css").toExternalForm());
        MainController controller = loader.getController();
        controller.initialize(appStateService);
        stage.setTitle("Alperen Akademi - YKS Performans Takip Sistemi");
        stage.setScene(scene);
        stage.setMinWidth(1080);
        stage.setMinHeight(700);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
