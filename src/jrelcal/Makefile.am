javapackage 	= jrelcal
javadir 	= ${prefix}/classes/jrelcal
JAVAROOT        = ${top_builddir}/src
JAVASRCROOT     = ${top_srcdir}/src

EXTRA_DIST      = \
	$(java_JAVA) \
	$(EXTRA_JAVA)

java_JAVA       = \
	AdjacencyTable.java \
	Indexable.java \
	IndexedSet.java \
	MultiAdjacencyTable.java \
	MultiRelation.java \
	OrderedBag.java \
	OrderedSet.java \
	Pair.java \
	Relation.java \
	VertexBag.java \
	VertexSet.java \
	RSFReader.java \
	RSFFormatException.java \
	${EXTRA_JAVA}


CLASSPATH_ENV = CLASSPATH=${JAVAROOT}:${JAVASRCROOT}:${top_srcdir}/jars/junit.jar:
java_packages   = jrelcal
java_dirs       = $(srcdir)
java_files      = $(wildcard $(patsubst %, %/*.java, $(java_dirs)))
java_test_files = $(wildcard $(patsubst %, %/TestAll.java, $(java_dirs)) $(patsubst %, %/*Test.java, $(java_dirs))


EXTRA_JAVA = \
	TestAll.java \
	IndexedSetTest.java \
	MultiRelationTest.java \
	OrderedBagTest.java \
	OrderedSetTest.java \
	RelationTest.java

TESTS_JUNIT = MainTest

TESTS = @TESTS_JUNIT@

EXTRA_TESTS = $(TESTS_JUNIT)

MainTest: Makefile
	echo "#! /bin/sh" > $@
	echo "@JUNIT@ jrelcal.TestAll" >> $@
	chmod +x $@

CLEANFILES=MainTest

