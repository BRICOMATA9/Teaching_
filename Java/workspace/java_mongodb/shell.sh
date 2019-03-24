javac -d bin -classpath lib/*.jar -sourcepath src src/com/mkyong/core/App.java
java -classpath bin:lib/mongo-java-driver-2.10.1.jar com/mkyong/core/App
