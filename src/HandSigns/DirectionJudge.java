package HandSigns;

public class DirectionJudge {
	int flag = 0;
	public DirectionJudge(int startx,int starty,int endx,int endy){
		int a = (int)Math.pow(3, 0.5);
		int lx = Math.abs(endx - startx);
		int ly = Math.abs(endy - starty);
		if(ly/a <= lx && lx/a <= ly){
			if(endx > startx && endy > starty) flag = 2;
			else if(endx > startx && endy < starty) flag = 4;
			else if(endx < startx && endy > starty) flag = 8;
			else flag = 6;
		}
		else if(ly/a > lx && lx/a < ly){
			if(endy > starty) flag = 1;
			else flag = 5;
		}
		else{
			if(endx > startx) flag = 3;
			else flag = 7;
		}
	}
	public int getFlag(){
		return flag;
	}
}
