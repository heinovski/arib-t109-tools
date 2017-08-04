JAVA = java
JFLAGS = -g
JAVAC = javac
JAR = jar

.SUFFIXES: .java .class

SOURCES = \
        src/AribTools.java

CLASSES = $(SOURCES:src/%.java=bin/%.class)

JAR_FILE = \
		arib-tools.jar

#.java.class:
#	$(JAVAC) $(JFLAGS) $*.java -d bin

bin/%.class: src/%.java
	mkdir -p $(@D)
	$(JAVAC) $(JFLAGS) $< -d $(@D)

default: classes

all: jar

classes: $(CLASSES)

%.jar: MANIFEST.MF $(CLASSES)
	$(JAR) -cvfm $@ $< -C $(word 2, $(^D)) .

jar: $(JAR_FILE)

run: bin/AribTools.class
	$(JAVA) -cp $(<D) $(patsubst %.class, %, $(<F)) periods 3

clean:
	$(RM) $(CLASSES)
	$(RM) -r bin

cleanall: clean
	$(RM) $(JAR_FILE)

.PHONY: default all classes jar run clean
