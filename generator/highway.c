#include <stdio.h>
#include "struct.h"
#include <math.h>

void tdown(Edge e, Edge heap[], int k, int length){
	int c = k*2;
	if(c > length) return;

	int r = c+1;
	if(r <= length && heap[r].d > heap[c].d) c = r;

	if(heap[c].d > heap[k].d){
		double w = heap[k].d;
		heap[k].d = heap[c].d;
		heap[c].d = w;
		int i = heap[k].v;
		heap[k].v = heap[c].v;
		heap[c].v = i;
		i = heap[k].u;
		heap[k].u = heap[c].u;
		heap[c].u = i;
		tdown(e, heap, c, length);
	}
}

void mst(int i, int j, Vertex v[], Edge heap[], int length){
	Edge e;
	e.v = i;
	e.u = j;
	double w = v[j].x - v[i].x;
	double h = v[j].y - v[i].y;
	printf("v[%d].x = %f\n", i, v[i].x);
	e.d = (double)sqrt(w*w + h*h);

	if(e.d < heap[1].d){
		heap[i].v = e.v;
		heap[i].u = e.u;
		heap[1].d = e.d;
		tdown(e, heap, 1, length);
	}

	tdown(e, heap, 1, length);
}

void highway(Vertex v[], Edge e[], int NoV){
	Edge heap[NoV];
	int i;
	for(i = 0; i < NoV; i++){
		heap[i].v = -1;
		heap[i].u = -1;
		heap[i].d = (double)10000.0;
		printf("heap[%d], v = %d, u = %d, d = %f\n", i, heap[i].v, heap[i].u, heap[i].d);
	}

	int j;
	for(i = 0; i < NoV; i++){
		for(j = i+1; j < NoV; j++){
			mst(i, j, v, heap, NoV);
		}
	}

	for(i = 0; i < NoV-1; i++){
		e[i].v = heap[i+1].v;
		e[i].u = heap[i+1].u;
		e[i].d = heap[i+1].d * 2.0;
		v[e[i].v].degree++;
		v[e[i].u].degree++;
		printf("e[%d]: v = %d, u = %d, d = %f\n", i, e[i].v, e[i].u, e[i].d);
	}
}
