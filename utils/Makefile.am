
bin_SCRIPTS = ${PACKAGE}-config

CLEANFILES = ${PACKAGE}-config

${PACKAGE}-config: Makefile
	@(      echo "#! /bin/sh";\
		echo "echo ${prefix}/share/jrelcal-${VERSION}.jar" \
	) > $@ && chmod +x $@

