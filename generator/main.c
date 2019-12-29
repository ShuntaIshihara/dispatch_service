/* 入力:[filename] [case_type] [seed] */
/* 入力はコマンドライン引数で受け取る */
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/* struct.hには構造体の宣言がされている */
#include "struct.h"

/* main.hには関数のプロトタイプ宣言をまとめてある */
#include "main.h"

#define Tmax 10000

typedef struct stack{
	int s;
	int g;
	int id;
	struct stack *next;
}Stack;

int main(int argc, char *argv[]){
/* コマンドライン引数の数が正しいかチェックする */
	if (argc != 4){
		fputs("Deffirent number of command line arguments: $ ./generator [filename] [casetype] [seed]\n", stderr);
		exit(1);
	}

/* 第一引数が正しいかチェックする */
	if (check_filename(argv[1])){
		fputs("filename is not correct: *.txt\n", stderr);
		exit(1);
	}

/* 第二引数が正しいかチェックする */
	int type = atoi(argv[2]);
	if (type != 1 && type != 2){
		fputs("case_ype is not correct: '1' or '2'\n", stderr);
		exit(1);
	}

/* 第三引数が正しいかチェックする */
	int seed = atoi(argv[3]);
	if(seed < 1 || seed > RAND_MAX){
		fputs("seed is not correct: 1 <= seed <= RAND_MAX(>= 32767)\n", stderr);
		exit(1);
	}

/* [filename]で指定したファイルに書き込む */
	FILE *fp;
	fp = fopen(argv[1], "w");

/* [seed]と[case_type]から頂点と辺の数を決める */
	srand((unsigned) seed);
	int NoV = 0;
	if (type == 1) NoV = rand() % 200 + 200;
	else NoV = 400; 
	int NoE = (rand()%101 + 300) * NoV / 200;

/* 頂点の生成 */
/* |V| = R^2 + r を満たす最大の非負整数Rを見つける(ただしrも非負整数) */
	int R = max_square(NoV);

/* 頂点vを宣言して初期化する*/
	Vertex v[NoV];
	init_v(v, NoV, R);

/* 頂点の場所を調整する */
	adj_v(v, seed, NoV);

/* 道路の作成 */
	Edge e[NoE];
	road(v, e, NoV, NoE);

	int i;
	for(i = 0; i < NoE; i++){
		int dis = (int)e[i].d;
		if(dis == 0) dis = 1;
		fprintf(fp, "%d %d %d\n", e[i].v, e[i].u, dis);
	}


/* 注文の決定 */
	
	/*注文頻度の決定*/
	int frequency[NoV];
	for(i = 0; i < NoV; i++){
		frequency[i] = 1;
	}
	int cx = R/4+rand()%(R/2);
	int cy = R/4+rand()%(R/2);

	int w;
	int h;
	int dis;
	for(i = 0; i < NoV; i++){
		w = v[i].x - cx;
		h = v[i].y - cy;
		dis = (int)sqrt(w*w + h*h);
		if(dis <= R/8 + rand()%(R/8)) frequency[i] = 2;
	}

	/*重みの総和*/
	int weight = 0;
	for(i = 0; i < NoV; i++){
		weight += frequency[i];
	}

	/*注文の抽選*/
	int last = Tmax * 95 / 100;
	int peak = rand() % last;
	int t;
	int r;
	int count = 0;
	int id = 0;
	for(t = 0; t < Tmax; t++){
		r = rand()%100;
		count = 0;
		Stack *head;
		head = NULL;
		if(r < poder(t, last, peak)){
			for(i = 0; i < NoV; i++){
				if(rand()%weight < frequency[i]){
					int g = rand()%NoV;
					if(i == g) continue;
					Stack *cell;
					cell = (Stack*)malloc(sizeof(Stack));
					cell->s = i;
					cell->g = g;
					cell->id = id;
					id++;
					count++;
					cell->next = head;
					head = cell;	
				}
			}
		}
		fprintf(fp, "%d\n", count);
		while(head != NULL){
			fprintf(fp, "%d %d %d\n", head->s, head->g, head->id);
			head = head->next;
		}
		free(head);
	}

	fclose(fp);

	return 0;
}
