#include <stdio.h>
#include "struct.h"

void init_v(Vertex v[], int length){
	int i;
	for (i = 0; i < length; i++){
		v[i].x = 0.0;
		v[i].y = 0.0;
		v[i].degree = 0;
	}
}
