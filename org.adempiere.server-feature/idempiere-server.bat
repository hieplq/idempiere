@Echo off

CALL utils\myEnvironment.bat Server

@if not "%JAVA_HOME%" == "" goto JAVA_HOME_OK
@Set JAVA=java
@Echo JAVA_HOME is not set.
@Echo You may not be able to start the server
@Echo Set JAVA_HOME to the directory of your local 1.6 JDK.
goto START

:JAVA_HOME_OK
@Set JAVA=%JAVA_HOME%\bin\java

:START
@Echo =======================================
@Echo Starting iDempiere Server ...
@Echo =======================================

FOR %%c in (idempiere.server*.jar) DO set JARFILE=%%c

@Set VMOPTS=%VMOPTS% -Djetty.home=jettyhome
@Set VMOPTS=%VMOPTS% -Djetty.base=jettyhome
@Set VMOPTS=%VMOPTS% -Dosgi.console=localhost:12612
@Set VMOPTS=%VMOPTS% -Dlaunch.keep=true 
@Set VMOPTS=%VMOPTS% -Dlaunch.storage.dir=bundle-cache

@"%JAVA%" %IDEMPIERE_JAVA_OPTIONS% %VMOPTS% -jar %JARFILE%

