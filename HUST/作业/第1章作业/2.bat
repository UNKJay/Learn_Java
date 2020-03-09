set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_151
set PROJECT_HOME=E:\chapter1
set path=%path%;%JAVA_HOME%\bin
set classpath=%classpath%;%PROJECT_HOME%\jar\run.jar

java -classpath "%classpath%" homework.ch1.Welcome
pause