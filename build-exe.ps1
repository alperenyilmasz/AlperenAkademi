# Alperen Akademi YKS - Windows .exe olusturma
# Gereksinim: JDK 21+ (jpackage dahil)
$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

if (-not $env:JAVA_HOME) {
    $javaCmd = Get-Command java -ErrorAction SilentlyContinue
    if ($javaCmd) {
        $javaBin = Split-Path $javaCmd.Source -Parent
        $env:JAVA_HOME = Split-Path $javaBin -Parent
        Write-Host "JAVA_HOME otomatik ayarlandi: $($env:JAVA_HOME)" -ForegroundColor DarkGray
    }
}

function Ensure-MavenWrapper {
    $wrapperJar = Join-Path $PSScriptRoot ".mvn\wrapper\maven-wrapper.jar"
    if (Test-Path $wrapperJar) { return }
    Write-Host "Maven Wrapper indiriliyor..." -ForegroundColor Cyan
    New-Item -ItemType Directory -Force -Path (Split-Path $wrapperJar) | Out-Null
    $props = Get-Content (Join-Path $PSScriptRoot ".mvn\wrapper\maven-wrapper.properties") -Raw
    if ($props -match 'wrapperUrl=(.+)') {
        Invoke-WebRequest -Uri $Matches[1].Trim() -OutFile $wrapperJar -UseBasicParsing
    } else {
        throw "maven-wrapper.properties icinde wrapperUrl bulunamadi."
    }
}

function Test-Jdk {
    $javaCmd = Get-Command java -ErrorAction SilentlyContinue
    if (-not $javaCmd) {
        throw "Java bulunamadi. JDK 21+ kurun (https://adoptium.net)."
    }
    $jpackage = Get-Command jpackage -ErrorAction SilentlyContinue
    if (-not $jpackage) {
        throw "jpackage bulunamadi. Tam JDK kurulumu gerekli (JRE yetmez)."
    }
    Write-Host "Java: $(java -version 2>&1 | Select-Object -First 1)" -ForegroundColor DarkGray
    Write-Host "jpackage: $($jpackage.Source)" -ForegroundColor DarkGray
}

Test-Jdk
Ensure-MavenWrapper

Write-Host ""
Write-Host "Derleniyor ve Windows uygulama paketi olusturuluyor..." -ForegroundColor Cyan
Write-Host "(Ilk calistirmada Maven indirilebilir; birkaç dakika surebilir.)" -ForegroundColor DarkGray
Write-Host ""

& cmd /c "mvnw.cmd clean package -Pnative -DskipTests"
if ($LASTEXITCODE -ne 0) {
    throw "Derleme basarisiz (cikis kodu: $LASTEXITCODE)."
}

$exePath = Join-Path $PSScriptRoot "target\dist\Alperen Akademi YKS\Alperen Akademi YKS.exe"
if (-not (Test-Path $exePath)) {
    throw "Beklenen exe bulunamadi: $exePath"
}

Write-Host ""
Write-Host "Basarili!" -ForegroundColor Green
Write-Host "Calistirilabilir dosya:" -ForegroundColor Green
Write-Host "  $exePath" -ForegroundColor White
Write-Host ""
Write-Host "Bu klasoru USB ile baska bilgisayara kopyalayabilirsiniz;" -ForegroundColor DarkGray
Write-Host "hedef PC'de Java kurulu olmasi gerekmez." -ForegroundColor DarkGray
