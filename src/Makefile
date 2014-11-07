JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MRS.java \

default: classes

classes: $(CLASSES:.java=.class)

run: default
	java MRS

clean:
	$(RM) *.class