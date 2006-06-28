# $Id$

SUBDIRS = jars src utils doc

ACLOCAL_FLAGS = -I .


JARFILE = JRelCal-${VERSION}.jar
JARFILE_ENV = JAR=${JARFILE}
JUNIT = ${top_srcdir}/jars/junit.jar
ANT_ENV = JARS=${JUNIT} CLASSPATH=${JUNIT}
PACKAGE_VERSION = PKG_VERSION=${VERSION}

data_DATA = ${JARFILE}
noinst_SCRIPTS = ${PACKAGE}-${VERSION}.pkg

EXTRA_DIST = reconf package acincludejava.m4 build.xml ${JARFILE} 


CLEANFILES = ${PACKAGE}-${VERSION}.pkg

${PACKAGE}-${VERSION}.pkg: package Makefile
	cp $< $@

if MAINTAINER_MODE

${JARFILE}: dummy
	${ANT_ENV} ${JARFILE_ENV} ant dist

dummy:


check:
	${ANT_ENV} ${JARFILE_ENV} ant check


.PHONY: doc
doc:
	${ANT_ENV} ${JARFILE_ENV} ant doc

clean-local:
	${JARFILE_ENV} ant clean

endif


