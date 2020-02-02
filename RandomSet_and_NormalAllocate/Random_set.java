//車の配置をランダムに決める

import java.util.Random;

class Random_set {
	static void set(Vehicle[] car, Shortest[] st){
		Random rnd = new Random();
		for(int i = 0; i < 20; i++){
			//車が空車のときランダムに配置する
			if(car[i].empty == 0){
				do{
					car[i].goal = rnd.nextInt(st.length);
				}while(car[i].goal == car[i].s);
			}
		}
	}
}
