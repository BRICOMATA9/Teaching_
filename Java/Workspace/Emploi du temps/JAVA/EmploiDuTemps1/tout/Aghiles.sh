cd ~/Bureau/JAVA/EmploiDuTemps1/tout

export CLASSPATH=$CLASSPATH:/home/aghiles/Bureau/JAVA/EmploiDuTemps1/tout/lib/mysql.jar
export CLASSPATH=$CLASSPATH:/home/aghiles/Bureau/JAVA/EmploiDuTemps1/tout
export CLASSPATH=$CLASSPATH:/home/aghiles/Bureau/JAVA/EmploiDuTemps1/tout/bin

javac -d bin -sourcepath src src/Systeme/Client.java
java Systeme.Client

javac -d bin -sourcepath src src/Systeme/Gestion_BDD.java
java Systeme.Gestion_BDD