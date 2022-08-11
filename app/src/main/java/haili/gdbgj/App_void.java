package haili.gdbgj;

import android.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import android.R;

public class App_void extends Activity 
{
    RelativeLayout mainlinearlayout;
	TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//隐藏系统状态栏
		//setContentView(R.layout.main_app);
		Intent intenr=new Intent(this,MainActivity.class);
		startActivity(intenr);
		finish();
		//text=(TextView)findViewById(R.id.void_text);
		//mainlinearlayout=(RelativeLayout)findViewById(R.id.app_void_LinearLayout);
	}
	
	View textv ;
	
  public void start_game(View view){//按键
	 
   }
	  
	  public void button(View view){
		  delect_View(mainlinearlayout,textv);
	  }
	  
	  
  public static void set_W_H(View view,int width,int height){//改变view的大小
         RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) view.getLayoutParams(); 
         linearParams.width = width;
         linearParams.height = height;
         view.setLayoutParams(linearParams);    }
  
  public  void setbg(View view,int id){ //设置背景;动画渲染
	  view.setBackground(getResources().getDrawable(id));	 }
	 
   public static View add_View(RelativeLayout main,Activity activity,int x,int y,int width,int height){//创建一个view
	      View view=new View(activity);
	      view.setX(x); view.setY(y);
	      view.setBackgroundColor(Color.RED);
	      main.addView(view,width,height);
	    return view;  }
   
	   
	public static void playMp3(Activity activity,int mp3_id){
		MediaPlayer mPlayer=MediaPlayer.create(activity,mp3_id);
		mPlayer.setLooping(true);
		mPlayer.start(); 
	  }
	   
	   
	public static void delect_View(RelativeLayout main,View view){//删除一个view
		   main.removeView(view);
		  }
	
   public static double get_dx(View a,View b){ //获取两个对象的距离,返回值大于0表示b在a右边，反之
       double dx=-1;
	   Float ax=a.getX(),bx=b.getX(); 
	   int aw=a.getWidth(),bw=b.getWidth();
	   if(ax+aw<bx)   dx= bx-ax-aw; 
	   if(ax>bx+bw)   dx=bx+bw-ax;
	   return dx;    }
	  
	public static double get_dy(View a,View b){ //获取两个对象的距离,返回值大于0表示b在a上面，反之
		double dy=-1;
		Float ay=a.getY(),by=b.getY(); 
		int ah=a.getHeight(),bh=b.getHeight();
		return dy;	}
		
    public static boolean signSame(int a,int b){//判断两个数符号是否相同
		if((a>=0&&b>=0)||(a<=0&&b<=0)) return true;
		   else return false;    }
    public static int absolute_value_int(int a){//取绝对值
		if(a>=0) return a; else return -a; 	}
}
