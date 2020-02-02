import java.util.List;
import java.util.ArrayList;
class FCFS {
	static void allocate(List<Oder> oder, Vehicle[] car, Shortest[] st){
		while(0 < oder.size()){
			Oder o = oder.get(0);
			int index = -1;
			int min = Integer.MAX_VALUE;
			for(int j = 0; j < car.length; j++){
				if(car[j].empty != 0) continue;
				if(min > st[car[j].s].time[o.point]){
					min = st[car[j].s].time[o.point];
					index = j;
				}
			}
			if(index == -1) return;
			car[index].empty = 1;
			car[index].goal = o.point;
			car[index].passenger[0] = o.id;
			car[index].passenger[1] = o.goal;
			oder.remove(0);
		}
	}
}
