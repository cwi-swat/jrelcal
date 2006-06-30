# $Id$

include $(top_srcdir)/java_make_rules

SUBDIRS = jars src utils doc

ACLOCAL_FLAGS = -I .


JARFILE = JRelCal-${VERSION}.jar
JARFILE_ENV = JAR=${JARFILE}
JUNIT = ${top_srcdir}/jars/junit.jar
EXTERNAL_JARS = ${JUNIT}
JAVA_PACKAGES=jrelcal

data_DATA = ${JARFILE}
noinst_SCRIPTS = ${PACKAGE}-${VERSION}.pkg

EXTRA_DIST = reconf package acincludejava.m4 build.xml ${JARFILE} 

CLEANFILES = ${PACKAGE}-${VERSION}.pkg

${PACKAGE}-${VERSION}.pkg: package Makefile
	cp $< $@

