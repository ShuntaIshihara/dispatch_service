class Test {
	static StringBuffer[] s = new StringBuffer[3];
	public static void reverse(int i){
		int f = s[i].indexOf(", ", 0);
		int l = s[i].lastIndexOf(", ", s[i].length());
		System.out.println(l);
		int nf, nl;
		String fs, ls;
		while(true){
			if(f >= l) return;
			nf = s[i].indexOf(", ", f+1);
			nl = s[i].indexOf(", ", l+1);
			if(nf == -1) nf = s[i].length();
			if(nl == -1) nl = s[i].length();
			fs = s[i].substring(f+2, nf);
			ls = s[i].substring(l+2, nl);
			s[i].replace(f+2, f+2+fs.length(), ls);
			s[i].replace(l+2+ls.length()-fs.length(), l+2+ls.length()+ls.length()-fs.length(), fs);
			f = s[i].indexOf(", ", f+1);
			l = s[i].lastIndexOf(", ", l+ls.length()-fs.length()-1);
			System.out.println(l);
		}
	}
	public static void main(String[] args){
		StringBuffer sb = new StringBuffer(", 0, 1, 2, 3, 400, 5");
		System.out.println(sb);
		
		sb.delete(0, 2);
		System.out.println(sb);

		int f = sb.indexOf(", ", 0);
		int l = sb.lastIndexOf(", ", sb.length());
		f = sb.indexOf(", ", f+1);
		l = sb.lastIndexOf(", ", l-1);

		System.out.println("f = " + f + ", l = " + l);

		String s1 = sb.substring(f+2, sb.indexOf(", ", f+1));
		String s2 = sb.substring(l+2, sb.indexOf(", ", l+1));
		System.out.println("s1 = " + s1 + ", s2 = " + s2);

		sb.replace(f+2, f+2+s1.length(), s2);
		sb.replace(l+2+(s2.length()-s1.length()), l+2+s2.length()+(s2.length()-s1.length()), s1);
		System.out.println(sb);

		s[0] = new StringBuffer("");
		s[1] = new StringBuffer(", 100000, 10000, 1000, 100, 10, 1");
		s[2] = new StringBuffer(", 1, 20, 300, 4000, 50000");

		for(int i = 0; i < 3; i++) System.out.println("s[" + i + "] = " + s[i]);
		for(int i = 0; i < 3; i++) reverse(i);
		for(int i = 0; i < 3; i++) System.out.println("s[" + i + "] = " + s[i]);
	}
}
