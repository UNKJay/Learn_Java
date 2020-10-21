set JAVA_HOME=D:\jdk1.8.0_231_64bit
set PROJECT_HOME=.
set path=%path%;%JAVA_HOME%\bin
set classpath=%PROJECT_HOME%\chapter1\jar\run.jar

rem 由于MANIFEST.MF里指定了Main-Class: homework.ch1.Welcome（注意:后面跟空格），所有可以用下面命令执行
java -jar %PROJECT_HOME%\chapter1\jar\run.jar -classpath %classpath%

rem 如果MANIFEST.MF里，没有指定Main-Class，可以按下面运行
java  -classpath %classpath% homework.ch1.Welcome
