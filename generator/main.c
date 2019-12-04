/* 入力:[filename] [case_type] [seed] */
/* 入力はコマンドライン引数で受け取る */
#include <stdio.h>
#include <stdlib.h>

//#define MAXDEGREE 5

typedef struct {
	double x;
	double y;
	int degree;
} Vertex;

typedef struct {
	int v;
	int u;
	int d;
} Edge;

/* main.hには関数のプロトタイプ宣言をまとめてある */
#include "main.h"

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
//	FILE *fp;
//	fp = fopen(argv[1], "w");

/* [seed]と[case_type]から頂点と辺の数を決める */
//	srand((unsigned) seed);
//	if (argv[2] == '1') NoV = rand() % 200 + 200;
//	else NoV = 400; 
//	NoE = NoV * 2 / (rand() % 2 + 3);

/* 頂点の生成 */
/* |V| = R^2 + r を満たす最大の非負整数Rを見つける(ただしrも非負整数) */
//	R = max_square(NoV)
/* 頂点vを宣言して初期化する*/
//	Vertex v[NoV];
//	init_v(v, NoV);
/* 頂点の場所を調整する */
//	adj_v(v);

/* 高速道路の生成 */
//	Edge e[NoE];
//	highway(v, e);

/* その他の道路の生成 */
//	general_road(v, e);

/* 注文の決定 */
//	oder();	

//	fclose(fp);

	return 0;
}
