#include <stdio.h>

typedef struct {
	int x;
	int y;
}S1;

typedef struct {
	S1 s;
}S2;

int main(void){
	S1 s1 = {1, 1};
	S2 s2 = {s1};

	printf("s1 = {%d, %d}\n", s1.x, s1.y);
	printf("s2 = {%d, %d}\n", s2.s.x, s2.s.y);

	s1.x = 10;
	s1.y = 10;
	printf("s1 = {%d, %d}\n", s1.x, s1.y);
	printf("s2 = {%d, %d}\n", s2.s.x, s2.s.y);

	return 0;
}
