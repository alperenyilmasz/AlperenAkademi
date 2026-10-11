package com.alperenakademi.yks.ui.countdown;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Küçük başlık (üst), dairesel progress ring, ortada büyük rakam.
 * Arka plan açık, track koyu gri, renkli yay progress.
 */
public class CircularCountdownRing extends VBox {

    private static final Color TRACK_GRAY = Color.web("#535353");
    private static final double STROKE = 12;
    private static final double INNER_PAD = 6;

    private final Label titleLabel;
    private final Label valueLabel;
    private final RingPane ringPane;

    public CircularCountdownRing(String title, Color accent) {
        setSpacing(6);
        setAlignment(Pos.TOP_CENTER);

        titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        titleLabel.setTextFill(Color.web("#475569"));

        ringPane = new RingPane(accent);
        StackPane centerStack = new StackPane(ringPane);
        centerStack.setAlignment(Pos.CENTER);

        valueLabel = new Label("0");
        valueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        valueLabel.setTextFill(Color.BLACK);
        valueLabel.getStyleClass().add("countdown-ring-value");
        // Global .label stylesheet kuralını ezmek için (JavaFX önceliği)
        valueLabel.setStyle("-fx-text-fill: #000000;");
        valueLabel.setMouseTransparent(true);

        StackPane layered = new StackPane(centerStack, valueLabel);
        layered.setAlignment(Pos.CENTER);
        layered.setMinSize(120, 120);
        layered.setPrefSize(128, 128);
        layered.setMaxSize(128, 128);

        getChildren().addAll(titleLabel, layered);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setValueText(String text) {
        valueLabel.setText(text);
    }

    public void setProgress(double progress01) {
        ringPane.setProgress(progress01);
    }

    /**
     * İç daire + yay çizimi.
     */
    private static final class RingPane extends Pane {
        private final Canvas canvas;
        private final Color accent;
        private double progress = 0;

        RingPane(Color accent) {
            this.accent = accent;
            canvas = new Canvas();
            getChildren().add(canvas);
        }

        void setProgress(double p) {
            this.progress = Math.max(0, Math.min(1, p));
            redraw();
        }

        @Override
        protected void layoutChildren() {
            super.layoutChildren();
            double w = snapSizeX(getWidth());
            double h = snapSizeY(getHeight());
            canvas.setWidth(w);
            canvas.setHeight(h);
            canvas.setLayoutX(0);
            canvas.setLayoutY(0);
            redraw();
        }

        private void redraw() {
            double w = canvas.getWidth();
            double h = canvas.getHeight();
            if (w <= 0 || h <= 0) {
                return;
            }
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, w, h);

            double cx = w / 2;
            double cy = h / 2;
            double r = Math.min(w, h) / 2 - STROKE / 2 - INNER_PAD;
            double d = 2 * r;

            // Açık iç zemin
            gc.setFill(Color.web("#F8FAFC"));
            gc.fillOval(cx - r + STROKE / 2, cy - r + STROKE / 2, d - STROKE, d - STROKE);

            // Tam halka — koyu gri track
            gc.setStroke(TRACK_GRAY);
            gc.setLineWidth(STROKE);
            gc.setLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
            gc.strokeOval(cx - r, cy - r, d, d);

            // Renkli progress: JavaFX'te 0° = 3 o'clock; 12 o'clock = -90°; saat yönü pozitif extent
            if (progress > 0) {
                double extent = 360 * progress;
                gc.setStroke(accent);
                gc.setLineWidth(STROKE);
                gc.setLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
                gc.strokeArc(cx - r, cy - r, d, d, -90, extent, ArcType.OPEN);
            }
        }
    }
}
