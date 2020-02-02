//注文が発生した場所に一番近い車が向かう
class NomalAllocate {
	static void allocate(Oder[] oder, Vehicle[] car, Shortest[] st){
		for(int o = 0; o < oder.length; o++){
			if(oder[o].exist){
			//その頂点で注文が発生していれば
				int amount = oder[o].id.size();
				//その頂点での注文の数だけ車を向かわせる
				for(int a = 0; a < amount; a++){
					int min = Integer.MAX_VALUE;
					int index = -1;
					for(int c = 0; c < car.length; c++){
						if(car[c].empty != 0) continue;
						if(min > st[car[c].s].time[o]){
							min = st[car[c].s].time[o];
							index = c;
						}
					}
					if(index == -1) return;
					//一番近くにいる車を向かわせる
					car[index].empty = 1;
					car[index].goal = o;
					car[index].passenger[0] = oder[o].id.get(0);
					oder[o].id.remove(0);
					car[index].passenger[1] = oder[o].goal.get(0);
					oder[o].goal.remove(0);
					if(oder[o].id.size() == 0)
						oder[o].exist = false;
				}
			}
		}
	}
}
