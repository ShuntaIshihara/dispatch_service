class DistanceSet {
	static void set(Vehicle[] car, Shortest[] st, int[] index_list, int[] count_list){
		int V = index_list.length;
		boolean[] v = new boolean[V];
		for(int i = 0; i < V; i++)
			v[i] = false;

		for(int n = 0; n < car.length; n++){
			if(car[n].empty != 0) continue;
			int index = -1;
			int max = 0;
			for(int i = 0; i < V; i++){
				if(v[index_list[i]]) continue;
				int count = 0;
				for(int j = 0; j < V; j++){
					if(v[index_list[i]]) count++;
				}
				int w = count_list[i] - count;
				if(max < w){
					max = w;
					index = index_list[i];
				}
			}
			car[n].goal = index;
			v[index] = true;
		}
	}
}
