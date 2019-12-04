# Makefile for generator
OBJS = \
main.o \
check_filename.o \
max_square.o 
#init_v.o \
#adj_v.o \
#highway.o \
#general_road.o \
#oder.o \


.PHONY: all
all: generator

generator: $(OBJS)
	gcc -o generator $(OBJS)

main.o: main.c
	gcc -c $< 

main.o: main.h

check_filename.o: check_filename.c
	gcc -c $<

max_square.o: max_square.c
	gcc -c $<

.PHONY: clean
clean:
	rm -rf generator $(OBJS)
