set JAVA_HOME=D:\jdk1.8.0_231_64bit
set PROJECT_HOME=D:\IdeaWorkspace\JavaCourse
set path=%path%;%JAVA_HOME%\bin

mkdir %PROJECT_HOME%\temp
mkdir %PROJECT_HOME%\temp\homework
mkdir %PROJECT_HOME%\temp\homework\ch1
copy %PROJECT_HOME%\bin\production\JavaCourse\homework\ch1\*.class  %PROJECT_HOME%\temp\homework\ch1\


jar -cvf run.jar -C %PROJECT_HOME%\temp\ .

rmdir %PROJECT_HOME%\temp /S/Q
