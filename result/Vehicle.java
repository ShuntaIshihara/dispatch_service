class Vehicle {
	//current 現在地, road 点にいる場合0 移動してる場合移動した距離
	int s;
	int sd;
	int n;
	int nd;
	int goal;
	//passenger[0]: 客のid, passenger[1]: 客の目的地
	int[] passenger;
	//empty 0:空車 1:迎車 2:使用中
	int empty;

	Vehicle(){
		s = 0;
		sd = 0;
		n = 0;
		nd = 0;
		goal = 0;
		passenger = new int[2];
		passenger[0] = -1;
		passenger[1] = 0;
		empty = 0;
	}

	int move(Shortest[] st){
		if(s == n) return st[s].next[goal];

		if(st[s].time[goal] + sd < st[n].time[goal] + nd)
			return s;
		return n;
	}

	void renew(Shortest[] st, int go){
		if(s == n){
			n = go;
			nd = st[s].time[n];
		}

		if(go == s){sd--; nd++;}
		if(go == n){sd++; nd--;}

		if(sd == 0){n = s; nd = 0;}
		if(nd == 0){s = n; sd = 0;}
	}

	void check(){
		if(s == goal && s == n){
			if(empty == 2){
				empty = 0;
			}
			else if(empty == 1){
				empty = 2;
				goal = passenger[1];
			}
		}
	}
}
