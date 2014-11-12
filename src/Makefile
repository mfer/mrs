JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MRS.java Simulation.java\

default: classes

classes: $(CLASSES:.java=.class)

run: default
	java MRS

simulation_generate: default
	python gerador.py $(FILENAME) $(nLinks) $(minLength) $(maxLength) $(minZ) $(maxZ) $(escala)
	java Simulation $(FILENAME)

simulation: default
	java Simulation $(FILENAME)

clean:
	$(RM) *.class
