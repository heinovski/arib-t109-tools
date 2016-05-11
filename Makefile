build:
	javac src/AribTools.java -d bin/
jar:
	jar -cvmf MANIFEST.MF arib-tools.jar bin/AribTools.class
run:
	java -cp bin/ AribTools periods 3
