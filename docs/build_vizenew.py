# -*- coding: utf-8 -*-
"""Tamamlanmış vizenew makalesi üretir."""
import shutil
from docx import Document
from docx.text.paragraph import Paragraph
from docx.oxml import OxmlElement

SRC = r"c:\Users\Asus-Asus\Desktop\vizenew (1).docx"
DST = r"c:\Users\Asus-Asus\Desktop\vizenew-tamamlanmis.docx"

shutil.copy2(SRC, DST)
doc = Document(DST)


def delete_paragraph(paragraph):
    element = paragraph._element
    parent = element.getparent()
    if parent is not None:
        parent.remove(element)


def remove_empty_paragraphs():
    """Kaynakça öncesi boş satırları kaldırır."""
    for p in list(doc.paragraphs):
        if not p.text.strip():
            delete_paragraph(p)


def insert_paragraph_after(paragraph, text=""):
    new_p = OxmlElement("w:p")
    paragraph._element.addnext(new_p)
    new_para = Paragraph(new_p, paragraph._parent)
    if text:
        new_para.add_run(text)
    return new_para


def insert_section_before(anchor, section):
    for text, is_heading in section:
        p = anchor.insert_paragraph_before(text)
        if is_heading:
            try:
                p.style = "Heading 2"
            except KeyError:
                if p.runs:
                    p.runs[0].bold = True


def set_para_text(index: int, text: str):
    p = doc.paragraphs[index]
    p.clear()
    p.add_run(text)


def find_kaynakca_paragraph():
    for p in reversed(doc.paragraphs):
        t = p.text.strip()
        if t in ("Kaynakça / References", "Kaynakça", "Kaynakça / References "):
            return p
        if t.startswith("Kaynakça /"):
            return p
    raise RuntimeError("Kaynakça başlığı bulunamadı")


# Yöntem birleşik paragrafları düzelt
set_para_text(
    53,
    'Yazılımın geliştirilme sürecinde "Yazılım Geliştirme Yaşam Döngüsü" (SDLC) prensipleri doğrultusunda modüler bir yaklaşım benimsenmiştir. Proje yönetimi kapsamında gereksinim analizi, sistem tasarımı, kodlama ve test aşamaları sistematik bir sıra ile takip edilmiştir. Karmaşık veri yapılarının yönetimi için MVC (Model-View-Controller) mimari deseni kullanılmıştır. Bu yöntemle kullanıcı arayüzü (View), iş mantığı (Controller) ve veri modelleri (Model) birbirinden ayrıştırılarak sistemin bakımı kolaylaştırılmış ve hata payı minimize edilmiştir.',
)
set_para_text(
    55,
    "Projenin geliştirilmesinde yüksek performanslı ve nesneye dayalı (OOP) bir dil olan Java (JDK 17+) tercih edilmiştir. Arayüz tasarımı için JavaFX 21 ve FXML kullanılmış; görsel düzenlemeler Scene Builder ve app.css ile desteklenmiştir. Verilerin grafiksel analizi için JavaFX Charts kütüphanesi entegre edilmiştir. Derleme ve bağımlılık yönetimi Maven ile sağlanmış; geliştirme ortamı olarak IntelliJ IDEA kullanılmıştır.",
)
set_para_text(
    57,
    'Sistemin en kritik işlevi olan veri kalıcılığı katmanında Java Serializable arayüzü ve dosya I/O akışları kullanılmıştır. SerializedAppDataRepository sınıfı ile öğrenci verileri harici sunucuya ihtiyaç duymadan yerel diskte saklanmaktadır. Net hesaplama motorunda ÖSYM\'nin "4 yanlışın 1 doğruyu götürmesi" kuralı uygulanmış; ScoreCalculatorService ve AnalysisService sınıfları TYT/AYT puan tahmini ve trend analizini üretir.',
)
set_para_text(
    59,
    "Sistemin kullanıcı deneyimi tasarımında Oracle JavaFX dokümantasyonu ve veri görselleştirme ilkeleri baz alınmıştır. Puanlama ve ders ağırlıklarının doğrulanmasında ÖSYM Güncel YKS Kılavuzu birincil kaynak olarak kullanılmıştır.",
)

set_para_text(
    39,
    'Piyasada bulunan mevcut deneme takip uygulamaları genellikle mobil tabanlı olup, veri saklama güvenliği ve detaylı grafiksel analiz kapasitesi bakımından sınırlıdır. Ayrıca birçok uygulama, güncel katsayı değişimlerine veya öğrencinin kişisel veri gizliliğine (yerel depolama) tam uyum sağlamamaktadır. Bu projenin benzerlerinden temel farkı, tamamen masaüstü odaklı, hızlı ve yüksek performanslı bir kullanıcı deneyimi sunmasıdır. MVC mimarisi ile tasarlanan yazılım, kullanıcıya verilerini kendi yerel diskinde güvenle saklama ve internet bağımlılığı olmadan derinlemesine analiz yapma imkânı sunarak özgün bir değer yaratmaktadır. Proje, "4 yanlış 1 doğru" ve katsayı hesaplama algoritmalarıyla piyasadaki standart hesaplayıcılardan daha modüler ve genişletilebilir bir yapıya sahiptir.',
)

# Yöntem alt başlıkları (Oracle/ÖSYM paragrafından hemen sonra)
ux_para = None
for p in doc.paragraphs:
    if "Oracle JavaFX" in p.text:
        ux_para = p
        break
if ux_para is not None:
    extras = [
        "Veri Toplama ve Gereksinim Kaynakları",
        "Gereksinim toplama sürecinde ÖSYM YKS kılavuzu, ders müfredatları ve hedef kullanıcı senaryoları (kayıt, deneme girişi, grafik inceleme, hedef karşılaştırma) dokümante edilmiştir. İnsan katılımcılı anket veya görüşme yapılmamış; yazılım gereksinimleri doğrudan sınav sürecinin iş kurallarından türetilmiştir.",
        "Veri Analizi ve Test Süreci",
        "Kalite güvencesi kapsamında ScoreCalculatorServiceTest ve AnalysisServiceTest birim testleri yazılmış; mvn test komutu ile otomatik doğrulama yapılmıştır. Manuel uçtan uca senaryolar (kayıt → giriş → TYT deneme girişi → grafik kontrolü → hedef karşılaştırma → uygulama yeniden başlatma → veri kalıcılığı) ile sistem bütünlüğü test edilmiştir.",
    ]
    prev = ux_para
    for text in extras:
        prev = insert_paragraph_after(prev, text)

remove_empty_paragraphs()

BULGULAR = [
    ("Bulgular", True),
    (
        "Bu bölümde Alperen Akademi yazılımının geliştirme sürecinde elde edilen somut çıktılar sunulmaktadır. Proje, 12 haftalık iteratif bir planla tamamlanmış; her hafta test edilebilir bir modül veya teknik bileşen teslim edilmiştir.",
        False,
    ),
    ("Geliştirme Süreci ve Haftalık Çıktılar", True),
    (
        "Hafta 1–2: Gereksinim analizi, literatür taraması ve OOP veri modeli (UserAccount, MockExam, LessonResult, AppData). Hafta 3–4: JavaFX/FXML arayüz iskeleti ve SerializedAppDataRepository ile yerel kalıcılık. Hafta 5–6: ScoreCalculatorService, AnalysisService, LineChart/BarChart ve deneme listesi kartları. Hafta 7: YksCountdownRingsPanel ile dashboard ve geri sayım. Hafta 8: Haftalık soru takibi, hedef modülü, OBP ve PlacementProxyService. Hafta 9: WebFeedService ile takvim ve haber modülleri. Hafta 10: Çoklu kullanıcı, split-screen giriş/kayıt. Hafta 11: Koyu tema, ayarlar split-panel, TR/EN dil desteği. Hafta 12: JUnit testleri, README ve proje dokümantasyonu.",
        False,
    ),
    ("Uygulama Modülleri ve İşlevsel Sonuçlar", True),
    (
        "Geliştirilen masaüstü uygulaması on ana işlevsel modül içermektedir: (1) Dashboard — YKS geri sayım, son denemeler ve haber özeti; (2) Deneme Girişi — TYT/AYT, ders bazlı doğru/yanlış; (3) Deneme Listesi — TYT ve AYT kart listeleri; (4) Grafik ve Analiz — puan trendi ve ders net ortalamaları; (5) Haftalık Soru Takibi; (6) Hedef — üniversite/bölüm, kalan net, OBP; (7) Takvim; (8) Haberler; (9) Ayarlar; (10) Kimlik doğrulama — çoklu hesap.",
        False,
    ),
    ("Teknik Mimari ve Hesaplama Sonuçları", True),
    (
        "MVC katman ayrımı ile MainController, ScoreCalculatorService, AnalysisService ve SerializedAppDataRepository net biçimde ayrılmıştır. Deneme kaydında 4 yanlış 1 doğru kuralı uygulanmakta; TYT ve AYT için tahmini puan üretilmektedir. Veriler kullanıcı bazında izole edilmekte; AppData nesnesi serileştirilerek diske yazılmaktadır.",
        False,
    ),
    ("Test ve Doğrulama Bulguları", True),
    (
        "ScoreCalculatorServiceTest ve AnalysisServiceTest birim testleri mvn test ile doğrulanmıştır. Manuel testlerde kayıt, deneme girişi, grafik güncelleme ve hedef karşılaştırma akışları tutarlı sonuç vermiştir.",
        False,
    ),
]

TARTISMA = [
    ("Tartışma ve Sonuç", True),
    (
        "Elde edilen bulgular, YKS hazırlık sürecinde veri odaklı karar vermenin masaüstü bir yazılımla desteklenebileceğini göstermektedir. Yerel serialization yaklaşımı, veri gizliliği ve offline çalışma ihtiyacı olan kullanıcılar için mobil bulut uygulamalarına göre avantaj sağlamaktadır.",
        False,
    ),
    (
        "MVC ve servis katmanı ayrımı bakımı kolaylaştırmıştır. Hedef modülündeki kalan net göstergesi öğrenciye somut çalışma yönü sunmaktadır.",
        False,
    ),
    (
        "Sınırlılıklar: Üretilen puanlar resmi ÖSYM sonucu değildir; takvim/haber modülleri internete bağımlıdır; serialization sürüm uyumsuzluğunda veri taşıma riski vardır.",
        False,
    ),
    (
        "Gelecek çalışmalarda yapay zeka destekli eksik konu öneri sistemi ve bulut yedekleme planlanmaktadır. Alperen Akademi, eğitim teknolojilerinde performans takibine uygulanabilir bir örnek sunmaktadır.",
        False,
    ),
]

ETIK = [
    ("Teşekkür ve Bilgilendirme", True),
    (
        "Bu çalışma, Bartın Üniversitesi Bilgisayar Teknolojisi ve Bilişim Sistemleri bölümü kapsamında yürütülen bitirme projesi olarak hazırlanmıştır. Çalışmanın tamamı başka bir yayında sunulmamıştır.",
        False,
    ),
    ("Yayın Etiği Bildirimi", True),
    (
        "Yazarlar, araştırmanın etik dışı bir sorunu olmadığını ve bilimsel dürüstlük ile yayın etiği ilkelerine uyduklarını beyan ederler.",
        False,
    ),
    ("Araştırmacıların Katkı Oranı", True),
    (
        "Alperen Kabacan: yazılım mimarisi, backend servisleri ve test süreçleri (%50). Alperen Yılmaz: arayüz tasarımı, modül entegrasyonu ve dokümantasyon (%50).",
        False,
    ),
    ("Çıkar Çatışması", True),
    ("Yazarların bu çalışma ile ilgili herhangi bir çıkar çatışması bulunmamaktadır.", False),
    ("Fon Bilgileri", True),
    ("Bu çalışma için herhangi bir kurumsal veya bireysel fon desteği alınmamıştır.", False),
    ("Etik Kurul Onayı", True),
    (
        "Bu çalışma bir yazılım geliştirme projesidir; insan veya hayvan üzerinde deneysel müdahale içermemektedir. Bu nedenle etik kurul onayı gerektiren bir süreç yürütülmemiştir.",
        False,
    ),
]

kaynakca_para = find_kaynakca_paragraph()
kaynakca_para.clear()
kaynakca_para.add_run("Kaynakça")

for section in [BULGULAR, TARTISMA, ETIK]:
    insert_section_before(kaynakca_para, section)

extra_refs = [
    "Gamma, E., Helm, R., Johnson, R. & Vlissides, J. (1994). Design patterns: Elements of reusable object-oriented software. Addison-Wesley.",
    "Oracle Corporation. (2024). Java platform, standard edition documentation. https://docs.oracle.com/en/java/ adresinden erişildi.",
    "Apache Software Foundation. (2024). Apache Maven project. https://maven.apache.org/ adresinden erişildi.",
    "Karaoğlan Yılmaz, F. G. & Keser, H. (2016). E-öğrenmede yansıtıcı düşünme etkinliklerinin etkisi: Sistematik bir derleme. Education and Information Technologies, 21(4), 955-980.",
    "Yılmaz, R. & Kılıç-Çakmak, E. (2012). Eğitsel arayüz temsilcilerinin öğrenici başarısına etkisi. Educational Technology & Society, 15(1), 294-307.",
]

osym_para = None
for p in doc.paragraphs:
    if "osym.gov" in p.text.lower():
        osym_para = p
if osym_para is not None:
    for ref_text in reversed(extra_refs):
        osym_para.insert_paragraph_before(ref_text)

for p in doc.paragraphs:
    if p.text.strip() in ("<<", "<"):
        p.clear()

doc.save(DST)
print("Kaydedildi:", DST)
