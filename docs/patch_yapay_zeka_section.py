# -*- coding: utf-8 -*-
"""Haftalik gelisim Moodle HTML'e Yapay Zeka Etkilesimlerimiz bolumunu ekler."""
import base64
import re
from pathlib import Path

HTML = Path(__file__).resolve().parent / "haftalik-gelisim-moodle.html"
IMG_DIR = Path(__file__).resolve().parent / "images" / "yapay-zeka-etkilesim"

TOC_INSERT = """        <div style="margin-left: 20px; margin-bottom: 8px;"><strong style="color: #c8d7f4;">7.13.</strong> <a href="#sec-yapay-zeka-etkilesim" style="color: #93c5fd; text-decoration: none;">Yapay Zeka Etkileşimlerimiz</a></div>

"""

MARKER_BEFORE_FOOTER = """    <div style="text-align: center; margin-top: 28px; padding-top: 20px; border-top: 1px solid rgba(148,163,184,0.12); font-size: 0.82em; color: #5c6f8f;">
      Alperen Akademi · YKS Deneme Performans ve Gelişim Takip Sistemi · 12 Haftalık Gelişim Raporu
    </div>"""


def img_data_uri(path: Path) -> str:
    data = base64.b64encode(path.read_bytes()).decode("ascii")
    return f"data:image/png;base64,{data}"


def img_cell(path: Path, alt: str) -> str:
    uri = img_data_uri(path)
    return (
        f'<td style="width: 33.33%; padding: 8px; vertical-align: middle; text-align: center;">'
        f'<div style="min-height: 200px; height: 200px; display: table; width: 100%; '
        f'background: rgba(11,18,32,0.55); border-radius: 10px; '
        f'border: 1px solid rgba(148,163,184,0.2); box-sizing: border-box;">'
        f'<div style="display: table-cell; vertical-align: middle; text-align: center; padding: 6px;">'
        f'<img src="{uri}" alt="{alt}" '
        f'style="max-width: 100%; max-height: 188px; width: auto; height: auto; '
        f'object-fit: contain; display: inline-block; margin: 0 auto; border-radius: 6px;">'
        f"</div></div></td>"
    )


def build_section() -> str:
    paths = [IMG_DIR / f"ai-{i}.png" for i in range(1, 7)]
    for p in paths:
        if not p.exists():
            raise FileNotFoundError(p)

    alts = [
        "Zoom toplantısı — ekip görüşmesi",
        "Gemini ile birim test görüşmesi",
        "Zoom — proje koordinasyonu",
        "Birlikte yapay zeka ile kod geliştirme",
        "ChatGPT — Mockito ve test mimarisi",
        "ChatGPT — JUnit 5 birim testleri",
    ]
    cells = [img_cell(p, a) for p, a in zip(paths, alts)]
    row1 = "".join(cells[:3])
    row2 = "".join(cells[3:])

    return f"""
    <!-- Yapay Zeka Etkilesimlerimiz -->
    <div id="sec-yapay-zeka-etkilesim" style="margin-top: 28px; margin-bottom: 24px; padding: 24px; border: 1px solid rgba(148,163,184,0.14); border-radius: 12px; background: linear-gradient(to bottom right, rgba(22,32,53,0.9), rgba(15,23,42,0.95));">
      <h3 style="color: #f8fbff; border-left: 4px solid #6366f1; padding-left: 14px; margin: 0 0 14px 0; font-size: 1.2em;">Yapay Zeka Etkileşimlerimiz</h3>
      <p style="font-size: 0.95em; color: #c8d7f4; margin: 0 0 18px 0; text-align: justify; line-height: 1.65;">Yapay zekayı ekibimizin üçüncü bir üyesi gibi kullandık. Haftalık geliştirme sürecinde Zoom görüşmeleri, birlikte kodlama oturumları ve ChatGPT / Gemini ile yapılan teknik görüşmeler; analiz, test yazımı ve mimari kararlarımızın ayrılmaz parçası oldu.</p>
      <table role="presentation" style="width: 100%; max-width: 960px; margin: 0 auto; border-collapse: separate; border-spacing: 6px; table-layout: fixed;">
        <tr>{row1}</tr>
        <tr>{row2}</tr>
      </table>
    </div>

"""


def main():
    text = HTML.read_text(encoding="utf-8")

    if 'id="sec-yapay-zeka-etkilesim"' in text:
        text = re.sub(
            r"\s*<!-- Yapay Zeka Etkilesimlerimiz -->[\s\S]*?id=\"sec-yapay-zeka-etkilesim\"[\s\S]*?</div>\s*\n",
            "\n",
            text,
            count=1,
        )

    if "7.13." not in text:
        anchor = '        <div style="margin-left: 20px; margin-bottom: 3px;"><strong style="color: #c8d7f4;">7.12.</strong> <a href="#hafta-12"'
        if anchor not in text:
            raise RuntimeError("TOC anchor for hafta-12 not found")
        # Insert after full hafta-12 line block
        old = """        <div style="margin-left: 20px; margin-bottom: 3px;"><strong style="color: #c8d7f4;">7.12.</strong> <a href="#hafta-12" style="color: #93c5fd; text-decoration: none;">Hafta 12 — Test ve Final Dokümantasyon</a></div>

      </div>"""
        new = """        <div style="margin-left: 20px; margin-bottom: 3px;"><strong style="color: #c8d7f4;">7.12.</strong> <a href="#hafta-12" style="color: #93c5fd; text-decoration: none;">Hafta 12 — Test ve Final Dokümantasyon</a></div>
""" + TOC_INSERT + """      </div>"""
        if old not in text:
            raise RuntimeError("TOC block not found")
        text = text.replace(old, new, 1)

    if 'id="sec-yapay-zeka-etkilesim"' not in text:
        section = build_section()
        if MARKER_BEFORE_FOOTER not in text:
            raise RuntimeError("Footer marker not found")
        text = text.replace(MARKER_BEFORE_FOOTER, section + MARKER_BEFORE_FOOTER, 1)

    HTML.write_text(text, encoding="utf-8")
    print("Updated:", HTML)


if __name__ == "__main__":
    main()
