echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/algebraic_shortener/Main.java&&java com/krzem/algebraic_shortener/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"