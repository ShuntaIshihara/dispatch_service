#include <stdio.h>
#include "struct.h"
#include <stdlib.h>

void init_v(Vertex v[], int length, int R){
	int i;
	for (i = 0; i < length; i++){
		if (i <= R*R){
			v[i].x = (double)(i%(R));
			v[i].y = (double)((int)i/(R));
			v[i].degree = 0;
		}
		else if (rand() % 2){
			v[i].x = (double)(R);
			v[i].y = (double)(rand()%(R+1));
			v[i].degree = 0;
		}
		else{
			v[i].y = (double)(R);
			v[i].x = (double)(rand()%(R+1));
			v[i].degree = 0;
		}
	}
}
