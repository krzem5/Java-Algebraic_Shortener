@echo off
cls
if exist build rmdir /s /q build
mkdir build
cd src
javac -d ../build com/krzem/algebraic_shortener/Main.java&&jar cvmf ../manifest.mf ../build/algebraic_shortener.jar -C ../build *&&goto run
cd ..
goto end
:run
cd ..
pushd "build"
for /D %%D in ("*") do (
	rd /S /Q "%%~D"
)
for %%F in ("*") do (
	if /I not "%%~nxF"=="algebraic_shortener.jar" del "%%~F"
)
popd
cls
java -jar build/algebraic_shortener.jar
:end
