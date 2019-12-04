#include <stdio.h>
#include <stdlib.h>
#include "struct.h"

void adj_v(Vertex v[], int seed, int NoV){
	srand((unsigned)seed);
	int i;
	for (i = 0; i < NoV; i++){
		v[i].x += rand()%1000001 / 1000000.0;
		v[i].y += rand()%1000001 / 1000000.0;
	}
}
