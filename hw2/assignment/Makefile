ASSIGNMENTS		= $(wildcard *.md)
ASSIGNMENT_HTML	= $(ASSIGNMENTS:%.md=%.html)

all: $(ASSIGNMENT_HTML)

%.html: %.md
	pandoc -o $@ $<
	sed "s/’/'/g" $@ | sed "s/‘/'/g" | sed 's/”/"/g' | sed 's/“/"/g' > temp
	mv temp $@
