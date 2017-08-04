build:
	javac src/AribTools.java -d bin/
jar: build
	jar -cvmf MANIFEST.MF arib-tools.jar bin/AribTools.class
run: build
	java -cp bin/ AribTools periods 3
