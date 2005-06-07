# $Id$

SUBDIRS = jars src utils doc

ACLOCAL_FLAGS = -I .

EXTRA_DIST = reconf package acincludejava.m4

noinst_SCRIPTS = ${PACKAGE}-${VERSION}.pkg

CLEANFILES = ${PACKAGE}-${VERSION}.pkg

${PACKAGE}-${VERSION}.pkg: package Makefile
	cp $< $@

