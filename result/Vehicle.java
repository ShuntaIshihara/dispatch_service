class Vehicle {
	//current 現在地, road 点にいる場合0 移動してる場合移動した距離
	int current;
	int road;
	int next;
	int goal;
	//guest[0]: 客のid, guest[1]: 客の目的地
	int[] guest;
	//empty 0:空車 1:迎車 2:使用中
	int empty;

	Vehicle(){
		current = 0;
		road = 0;
		next = 0;
		goal = 0;
		guest = new int[2];
		guest[0] = -1;
		guest[1] = 0;
		empty = 0;
	}

	int move(){
		//次に向かう場所に１つ進めて情報を更新する
		if(road == 0) return next;

		road--;
		return next;
	}

	void set(Shortest[] st){
		if(road <= 0){
			next = st[current].next[goal];
			road = st[current].time[next];
		}
		else{
			if(st[current].time[goal] + st[next].time[current] - road < st[next].time[goal] + road){
				road = st[next].time[current] - road;
				next = current;
			}
		}
	}

	void check(Shortest[] st){
		//車が次の頂点に着いたとき、次の行動を決定する
		//頂点にいるとき
		if(road <= 0){
			current = next;
			//目的地に着いたとき
			if(current == goal){
				//客を乗せるとき
				if(empty == 1){
					empty = 2;
					goal = guest[1];
				}
				//客を下ろすとき
				else if(empty == 2){
					empty = 0;
					guest[0] = -1;
					guest[1] = 0;
				}
			}
			next = st[current].next[goal];
			road = st[current].time[next];
		}
	}
}
