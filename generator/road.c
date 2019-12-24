#include <stdio.h>
#include <stdlib.h>
#include "struct.h"
#include <math.h>

typedef struct binary{
	Edge e;
	struct binary *right;
	struct binary *left;
}Binary;

typedef struct {
	int cost;
	Edge e;
}Cost;

/*ユークリッド距離を計算してint型で返す*/
int distance(int v, int u, Vertex ver[]){
	double x = ver[u].x - ver[v].x;
	double y = ver[u].y - ver[v].y;
	double d = sqrt(x*x + y*y);
	return (int)d+1;
}

/*頂点の辺の数がmaxの値を超えていれば大きな数を渡す*/
int g(int i, Vertex ver[]){
	if(ver[i].degree < 5) return 1;
	return 1000;
}

/*頂点の元のx座標,y座標を足したものが偶数か奇数かで色付けをする*/
int color(int v, Vertex ver[]){
	int x = (int)ver[v].x;
	int y = (int)ver[v].y;
	if(x+y % 2 == 0) return 0;
	return 1;
}

/*なるべく道路が交差しないように縦横方向の隣の頂点を結ぶようにする*/
int f(int v, int u, Vertex ver[]){
	if(color(v, ver) == color(u, ver)) return 5;
	return 1;
}

/*辺のcostを計算する。costはHitachiの問題文を参照するように*/
int cost(int v, int u, Vertex ver[]){
	return distance(v, u, ver) * ver[v].degree * ver[u].degree * f(v, u, ver) * g(u, ver) * g(v, ver);
}

int b;
/*最小全域木の辺以外を配列に格納する*/
void init_cg(Binary *root, Edge cg[]){
	if(root->left != NULL) init_cg(root->left, cg);
	if(root->e.v != -1){
		cg[b] = root->e;
		b++;
	}
	if(root->right != NULL) init_cg(root->right, cg);
}

/*コストの値を更新する*/
void renew(Edge cg[], int c[], Vertex v[]){
	int i;
	for(i = 0; cg[i].v != -1; i++){
		c[i] = cost(cg[i].v, cg[i].u, v);
	}
}

/*一番値の小さいコストのインデックスを探す*/
int min_search(int cost[]){
	int i;
	int index = 0;
	int min = cost[0];
	for(i = 1; cost[i] != -1; i++){
		if(cost[i] < min){
			min = cost[i];
			index = i;
		}
	}
	return index;
}

/*最小全域木以外の辺を作る。作り方はhitachiの問題文を参照するように*/
void general_road(Binary *root, Edge e[], Vertex v[], int NoV, int NoE){
	Edge cg[NoV*(NoV-1)/2];
	int cost[NoV*(NoV-1)/2];
	b = 0;
	init_cg(root, cg);
	cg[b].v = -1;
	cost[b] = -1;
	b--;

	int i;
	for(i = 0; i < NoE-NoV+1; i++){
		/*コストの値を更新する*/
		renew(cg, cost, v);
		/*コストの値が一番小さいインデックスを探す*/
		int index = min_search(cost);
		/*辺に代入していく*/
		e[NoV-1+i] = cg[index];
		/*選ばれた頂点の次数を増やす*/
		v[cg[index].v].degree += 1;
		v[cg[index].u].degree += 1;
		/*同じ辺を二度選ぶことがないように選ばれた辺と最後にある辺の位置を入れ替える*/
		cg[index] = cg[b];
		cost[index] = cost[b];
		/*配列の最後とわかるように-1を代入しておく*/
		cost[b] = -1;
		cg[b].v = -1;
		b--;
	}
}

/*データxが属する木の根を得る*/
int find(int x, int par[]){
	if(x == par[x]) return x;
	return par[x] = find(par[x], par);
}

/*データが異なる木に属しているか*/
int is_diffirent(int x, int y, int par[]){
	/*親が違ければtrue*/
	return find(x, par) != find(y, par);
}

/*頂点xの木と頂点yの木を結合する*/
void unit(int x, int y, int par[], int size[]){
	/*根ノードを取得する*/
	int i = find(x, par);
	int j = find(y, par);

	/*大きい木にマージする*/
	if(size[i] < size[j]){
		int w = i;
		i = j;
		j = w;
	}

	par[j] = i;
	size[i] += size[j];
}

int a = 0;
void highway(Binary *root, Edge e[], int par[], int size[], int length, Vertex v[]){
	if(a >= length) return;

	if(root->left != NULL) highway(root->left, e, par, size, length, v);

	/*２つの頂点が異なる木に属している時*/
	if(is_diffirent(root->e.v, root->e.u, par)){
		e[a].v = root->e.v;
		e[a].u = root->e.u;
		e[a].d = root->e.d * 2;
		a += 1;
		v[root->e.v].degree += 1;
		v[root->e.u].degree += 1;
		unit(root->e.v, root->e.u, par, size);
		root->e.v = -1;
		root->e.u = -1;
	}

	if(root->right != NULL) highway(root->right, e, par, size, length, v);
}

/*二分木に新たに値を追加する関数*/
void b_tree(Edge e, Binary *root){
	Binary *node;
	node = (Binary*)malloc(sizeof(Binary));

	if(e.d < root->e.d){
		if(root->left != NULL) b_tree(e, root->left);
		else{
			node->e = e;
			node->left = NULL;
			node->right = NULL;
			root->left = node;
		}
	}
	else{
		if(root->right != NULL) b_tree(e, root->right);
		else{
			node->e = e;
			node->left = NULL;
			node->right = NULL;
			root->right = node;
		}
	}
}

/*テスト用
void tostring(Binary *root){
	if(root->left != NULL) tostring(root->left);
	printf("v = %3d, u = %3d, d = %f\n", root->e.v, root->e.u, root->e.d);
	if(root->right != NULL) tostring(root->right);
}
*/

void road(Vertex v[], Edge e[], int NoV, int NoE){
	/*頂点が木に含まれているのかの情報を保持するデータ構造*/
	int i;
	Edge w;
	w.v = 0;
	w.u = NoV/2;
	double x = v[NoV/2].x - v[0].x;
	double y = v[NoV/2].y - v[0].y;
	w.d = sqrt(x*x + y*y);

	Binary *root;
	root = (Binary*)malloc(sizeof(Binary));
	root->e = w;
	root->left = NULL;
	root->right = NULL;

	/*完全グラフを作成する*/
	/*グラフの辺は左の子が小さい値、右の子が大きい値となるように二分木によるデータ構造で格納される*/
	int j;
	for(i = 0; i < NoV; i++){
		for(j = i+1; j < NoV; j++){
			if(i == 0 && j == NoV/2) continue;
			w.v = i;
			w.u = j;
			double x = v[j].x - v[i].x;
			double y = v[j].y - v[i].y;
			w.d = sqrt(x*x + y*y);
			b_tree(w, root);
		}
	}

	/*par[i]: データiの親が入っている。par[i] == iのときは根ノード*/
	int par[NoV];
	/*size[i]: データiを根とする木のサイズ。iが根以外のときは無意味な値*/
	int size[NoV];
	for(i = 0; i < NoV; i++){
		size[i] = 1;
		par[i] = i;
	}
	highway(root, e, par, size, NoV-1, v);
	general_road(root, e, v, NoV, NoE);
	free(root);
}
