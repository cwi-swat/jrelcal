include ${srcdir}/latex-make-rules

SUFFIXES = .lhs

.tex.lhs:
	sed -e 	's/\%\#/\#/g; s/\\bc\\begin[{]verbatim[}]/\\begin\{code\}/g; s/\\end[{]verbatim[}]\\ec/\\end\{code\}/g' < $< > $@


INCLUDED_PICS = \
	calls.jpg \
	calls2.jpg \
	components.jpg \
	domi.jpg \
	flow.jpg \
	idom.jpg \
	tcalls.jpg


INCLUDED_TEX = \
	Graph.tex \
	GraphViz.tex \
	Set.tex 

INCLUDED_STY = \
	mymacros.tex \
	proof.sty

INCLUDED_BIB = \
	RASS.bib \
	mybibAG.bib \
	mybibHZ.bib

TEX_FILES = RASS.tex $(INCLUDED_TEX)

LHS_FILES = $(TEX_FILES:.tex=.lhs)

HS_FILES = $(LHS_FILES)

EXTRA_DIST = \
	$(TEX_FILES) \
	$(INCLUDED_PICS) \
	$(INCLUDED_STY) \
	$(INCLUDED_BIB) \
	latex-make-rules

CLEANFILES = *~ *.log *.aux *.dvi *.ps *.pdf *.toc *.bbl *.blg *.lhs *.hi *.o \
	$(MAIN)

all:  ${LHS_FILES}

doc: RASS.pdf

RASS.pdf: RASS.tex $(INCLUDED_TEX) $(INCLUDED_BIB) $(INCLUDED_PICS) $(INCLUDED_STY)

dist: RASS.pdf
	cp $< ${DISTDIR}

install:

