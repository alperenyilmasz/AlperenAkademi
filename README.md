# EN EN EN AlperenAkademi - YKS Progress & Performance Tracker

AlperenAkademi is a desktop application built with Java and JavaFX, designed to empower students preparing for the Turkish Higher Education Institutions Exam (YKS - TYT & AYT). The software serves as a comprehensive academic companion, allowing students to log their daily question counts, record mock exam results, and monitor their progress toward their dream universities.

# 🎯 Key Purpose & Vision

Preparing for competitive exams requires consistent tracking and data-driven adjustments. AlperenAkademi bridges the gap between daily effort and target outcomes by turning study logs into actionable visual insights, helping students clearly see where they stand and what is needed to reach their target university departments.

# ✨ Core Features

Mock Exam Management: Log TYT and AYT practice exam scores, section-by-section breakdown (Turkish, Math, Science, Social Sciences), and net scores.

Daily Question Counter: Track the total number of questions solved each day categorized by subject and topic.

Interactive Data Visualization: Dynamic JavaFX charts and graphs displaying performance trends, net score trajectories, and subject mastery over time.

Target University Tracker: Set target faculties/departments, compare current performance with required net thresholds, and identify exact score deficits to focus on.

Progress Analytics: Historical performance logs to help students spot weak areas and plan effective revision sessions.

# 🛠️ Built With

Language: Java

UI Framework: JavaFX

Build Tool: Maven / Wrapper

Architecture: MVC (Model-View-Controller) pattern for maintainable desktop GUI development

# TR TR TR YKS Deneme Performans ve Gelisim Takip Sistemi

JavaFX + FXML + MVC mimarisi ile gelistirilmis masaustu uygulamasidir.

## Ozellikler

- TYT/AYT deneme girisi, net ve tahmini puan hesaplama
- Son 5/10 deneme ortalamasi ve grafik analizi
- YKS geri sayim sayaci
- Haftalik ders-konu soru takibi
- Hedef net/puan karsilastirma ve geri bildirim
- Turkiye geneli deneme takvimi
- Haber akisi (OSYM/YOK odakli)
- Yerel veri kaliciligi (Java Serialization)

## Calistirma (gelistirme)

1. JDK 21 kurulu olmali.
2. Proje klasorunde:
   - `mvnw.cmd clean javafx:run` (Maven yoksa wrapper yeterli)
3. IntelliJ/Eclipse icinden `MainApp` veya `Launcher` da calistirilabilir.

## Windows .exe olusturma (dagitim)

Tiklanabilir masaustu uygulamasi icin (Java kurulu olmayan PC'lerde de calisir):

1. **JDK 21** tam kurulumu gerekli (`jpackage` dahil; sadece JRE yetmez).
2. PowerShell'de proje klasorunde:
   ```powershell
   .\build-exe.ps1
   ```
   veya:
   ```cmd
   mvnw.cmd clean package -Pnative -DskipTests
   ```
3. Olusan program:
   ```
   target\dist\Alperen Akademi YKS\Alperen Akademi YKS.exe
   ```
4. Dagitim: `target\dist\Alperen Akademi YKS` klasorunun tamamini kopyalayin (exe yaninda `app` ve `runtime` klasorleri de gerekli).

Ilk derlemede Maven ve bagimliliklar indirilir; birkaç dakika surebilir.

## Test

- `mvn test`

## Proje Yapisi

- `src/main/java/com/alperenakademi/yks/model`
- `src/main/java/com/alperenakademi/yks/service`
- `src/main/java/com/alperenakademi/yks/repository`
- `src/main/java/com/alperenakademi/yks/controller`
- `src/main/resources/view`
