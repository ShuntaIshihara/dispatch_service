# Makefile for generator
OBJS = main.o

.PHONY: all
all: main

main: $(OBJS)
	gcc -o generator $(OBJS)

main.o: main.c
	gcc -c $< 

