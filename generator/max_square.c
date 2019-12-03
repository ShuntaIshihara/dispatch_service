#include <stdio.h>
#include <stdlib.h>

int max_square(int NoV){
	int i;
	for(i = 14; i*i <= NoV; i++);
	return (i-1);
}
