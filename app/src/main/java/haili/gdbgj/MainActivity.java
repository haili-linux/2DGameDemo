package haili.gdbgj;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import haili.gdbgj.*;
import android.graphics.*;
import android.graphics.drawable.*;

public class MainActivity extends Activity{
        RelativeLayout mainlineary;
     	TextView main_view; //主角
     	TextView rock;
    	Button button_attackd,button_up,button_down,button_left,button_right;
	//状态变量 
	boolean is_running_letf=false,is_running_right=false,is_running_up=false,is_running_down=false,is_attracking=false;
	boolean is_attrack_value=false;//攻击造成是否有效，到攻击生效的帧时才有效
         	@Override
    public void onCreate(Bundle savedInstanceState){
               super.onCreate(savedInstanceState);
	  	
	        	//隐藏标题栏,需要在加载布局前执行
		      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			  //隐藏系统通知栏
			  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			  
              setContentView(R.layout.main);
		
	        	
		    
		      main_view = (TextView)findViewById(R.id.mainTextView1);
		      rock = (TextView)findViewById(R.id.mainTextView2_rock);
	   	
		       button_attackd=(Button)findViewById(R.id.button_attack_id);
			   
		       button_up = (Button)findViewById(R.id.button_move_up);
			   button_up.setOnTouchListener(buttonListener_up);
			   
			   button_down = (Button)findViewById(R.id.button_move_down);
			   button_down.setOnTouchListener(buttonListener_down);
			   
			  button_left = (Button)findViewById(R.id.button_move_left);
	   		  button_left.setOnTouchListener(buttonListener_left);
			   
			  button_right = (Button)findViewById(R.id.button_move_right);
			  button_right.setOnTouchListener(buttonListener_right);
			  mainlineary=(RelativeLayout)findViewById(R.id.mainLinearLayout);
			  earth=getwindow_height_or_widht(false,this)*95/100;//地平面坐标
			for(int i=0;i<is_using.length;i++) {is_using[i]=false; is_using_render[i]=false; }
			//set_layout();
		 }//onCreate end
	
	  
	
	int nc=0;
	float v=0;
	
	
//#######方法执行区#############方法执行区#############方法执行区#############方法执行区#############方法执行区######
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
						main_view.setX(main_view.getX()+1);
						main_view.setY(main_view.getY()+1);
						 break;
				case 1: // move_end(rock,20,0,-1,0,60); 
				attracked(); //攻击效果生效
						break;
			
				case 3:  //text.setY(text.getY()-msg.arg1); //上
				         up_move(main_view,msg.arg1,0);
				        break;
						
				case 4:  //text.setY(text.getY()+msg.arg1); //下
				         down_move(main_view,msg.arg1,0);
				        break;
				
				case 5: // text.setX(text.getX()-msg.arg1); //左
				        left_move(main_view,msg.arg1,0);
				        break;
						
				case 6: // text.setX(text.getX()+msg.arg1);  //右
				        right_move(main_view,msg.arg1,0);
				        break;
											
			    case 7:   main_view.setText(msg.arg1+"");
					     
						 break;
			    case 8:  
					     setbg(main_view,date_id.tamamo.walk_tamamo_r[msg.arg1]);
						 break;  
				case 9:    //攻击动作实现
						  if(msg.arg1<=5)  setbg(main_view,date_id.tamamo.badao[msg.arg1]);
					      if(msg.arg1==6/*动作结束标志*/) {
							 setbg(main_view,date_id.tamamo.badao[0]);
							  nc=0;  }
					 break;
					 
				case 10:
					break;
					
			    case 11: //渲染 render方法
					setbg((View)msg.obj,msg.arg1);
					break;
					
		        case 12: View view=(View)msg.obj; //运动 move方法
		   		         move(view,msg.arg1,msg.arg2,msg.sendingUid);
			         break;
				default:  
					break;
			}
		}
	};  //msg end 
	
	
	
	public void setimfor(int what,int arg1/*速度*/,int arg2){
		            Message message = new Message();
		            message.what = what;
	             	message.arg1=arg1;//速度
					message.arg2=arg2;
		            handler.sendMessage(message);       }
	
//END#######游戏方法执行区#############方法执行区#############方法执行区#############方法执行区#############方法执行区######

	
		  
		 
		 


//########按键操作###############操作###############操作###############操作###############操作###############操作#######
	final int fps = 60;  int v1=3*60/fps;  int v2=12*60/fps;
	final int sleep_time=1000/fps;	 
		
	 public void button_attack(View attack)
	      {   //攻击			   
			if(!is_attracking) {
				   new Thread(new Runnable(){
						   public void run(){
							   for(int i=0;i<=6/*动作帧数*/;i++){   
							       if(i!=6)  is_attracking=true; else is_attracking=false;
								   if(i==5) setimfor(1,0,0); //第五帧时攻击生效
								   setimfor(9,i,0);
								   try{   Thread.sleep(500/6/*动作时间0.6秒*/); }catch (InterruptedException e){ }
							   }
						   }
					   }).start();
				   }
		     }
			 
			 
		public void attracked(){//攻击生效方法
			    float dx;
			 if(main_view.getX()<rock.getX())
					    dx=main_view.getX()+main_view.getWidth()-rock.getX();
					  else 
					    dx=main_view.getX()-rock.getX()-rock.getWidth();
					
				float dy=main_view.getY()-rock.getY();
					
			     if(dx<0){  dx=dx*(-1);  }
			     if(dy<0){  dy=dy*(-1);  }
				
				rock.setText(dx+"");
				  if(dx<100  &&  dy<200)
					  if(rock.getX()>main_view.getX())
							move_end(rock,20,0,-1,0,60);
		      		    else
							move_end(rock,-20,0,1,0,60);				
			  } 
				

		  
		 View[] vi=new View[4];
	public void button_cs(View view){ 
		//main_view.setWidth(main_view.getWidth()-100);
		//rendering(rock,date_id.tamamo.walk_tamamo_r,3000*5,true,6);
		//move_end(rock,3,0,0,0,180*5);
		//move_end(main_view,3,0,0,0,180*5);
		vi[0]= App_void.add_View(mainlineary,this,getwindow_height_or_widht(true,this)*10/100,0,500,290);
		//App_void.set_W_H(vi[0],500,200);
		//vi[0].setX(500);  vi[0].setY(500);
		//vi[0].setX(-300);
		Toast.makeText(this,"x:"+vi[0].getX()+"   y:"+vi[0].getY(),2000).show();
		//move_end(vi[0],-2,2,-1,1,60);
	
		
	}
   

				
	public void button(View bartions){  set_layout();
	      //按键button方法   重置
	        rock.setX(500); rock.setY(610);  
			App_void.delect_View(mainlineary,vi[0]);
			
		
	}
	
	
     int time_up=0;
	private OnTouchListener buttonListener_up = new OnTouchListener() {
		public boolean onTouch(View arg0, MotionEvent event) {
			int action = event.getAction();
			Thread thread = new Thread(new Runnable(){
					public void run(){
						do{   	if(time_up>20)  setimfor(3,12,0);
								else   setimfor(3,3,0);
								time_up++; //记录按住的时间
								try {  Thread.sleep(sleep_time);   }catch (InterruptedException e){     }
								}while(is_running_up);	}    });
			if (action == MotionEvent.ACTION_DOWN) {// 按下 处理相关逻辑
				is_running_up=true; setbg(button_up,R.drawable.button_move1);
				thread.start();
			} else if (action == MotionEvent.ACTION_UP) { // 松开 todo 处理相关逻辑 
				is_running_up=false;
				time_up=0;  setbg(button_up,R.drawable.button_move);
			}
			return false; 
		}  };

	
	  int time_down=0;
	private OnTouchListener buttonListener_down = new OnTouchListener() {
		public boolean onTouch(View arg0, MotionEvent event) {
			int action = event.getAction();
			Thread thread=	new Thread(new Runnable(){
					public void run(){
						do{   	if(time_down>20)  setimfor(4,12,0);
						  		else  setimfor(4,3,0);
								time_down++;
								try {Thread.sleep(sleep_time); }catch (InterruptedException e){     }
								}while(is_running_down);   }	});
			if (action == MotionEvent.ACTION_DOWN) {// 按下 处理相关逻辑
				is_running_down=true;   setbg(button_down,R.drawable.button_move1);
				 thread.start();
			} else if (action == MotionEvent.ACTION_UP) { // 松开 todo 处理相关逻辑 
				is_running_down=false;
				time_down=0;   setbg(button_down,R.drawable.button_move);//设置背景
			}
			return false; 
		}  };
	
	
    int time_left=0;
	private OnTouchListener buttonListener_left = new OnTouchListener() {
		public boolean onTouch(View arg0, MotionEvent event) {
			int action = event.getAction();
			Thread thread=	new Thread(new Runnable(){
					public void run(){
						do{     if(time_left>20) setimfor(5,12,0);
							    else  setimfor(5,3,0);
								time_left++;
								try{ Thread.sleep(sleep_time); }catch (InterruptedException e){     }
						}while(is_running_letf);	} });
			if (action == MotionEvent.ACTION_DOWN) {// 按下 处理相关逻辑
				is_running_letf=true;  setbg(button_left,R.drawable.button_move1);
                thread.start();
			} else if (action == MotionEvent.ACTION_UP) { // 松开 todo 处理相关逻辑 
				is_running_letf=false;
				time_left=0;  setbg(button_left,R.drawable.button_move);
			}
			return false; 
		}   };
	
     int time_right=0;
	private OnTouchListener buttonListener_right = new OnTouchListener() {
		public boolean onTouch(View arg0, MotionEvent event) {
			int action = event.getAction();
			Thread thread=	new Thread(new Runnable(){
					public void run(){ int i=0;
						do{  	if(time_right>10) setimfor(6,4,0);
								else  setimfor(6,3,0);
								if(time_right%10==0&&!is_attracking) { setimfor(8,i,0); i++;}//渲染，如果正在攻击则不渲染走路动作
								if(i>=10) i=0;
								time_right++;
								try{  Thread.sleep(sleep_time);} catch (InterruptedException e){     }
						}while(is_running_right);	}	});
			if (action == MotionEvent.ACTION_DOWN) {// 按下 处理相关逻辑
				is_running_right=true;  setbg(button_right,R.drawable.button_move1);
                thread.start();
			} else if (action == MotionEvent.ACTION_UP) { // 松开 todo 处理相关逻辑 
				is_running_right=false;
				time_right=0;   setbg(button_right,R.drawable.button_move);
				setbg(main_view,date_id.tamamo.walk_tamamo_r[8]);
			}
			return false; 
		}  }; //移动:  setimfor()→handler→[?_move(),setbg()]
	
	
//########操作end###############操作end###############操作end###############操作end###############操作end#######

	
	
	
//#####@@@@@游戏渲染，运动方法区#@@#@#@#@#@#####@@@@@渲染，运动方法区#@@#@#@#@#@#####@@@@@渲染，运动方法区#@@#@#@#@#@#####@@@@@渲染，运动方法区#@@#@#@#@#@#
	int sdcard=10; //内存大小
	int[][] v_game=new int[sdcard][2]; int[][] a_game=new int[sdcard][2]; //二维数组，储存中间变量Vx,Vx,  ax,ay
	View[] view_game=new View[sdcard]; 
	int[] time_game=new int[sdcard];
	int use=0; //储存中间变量的个数
	Boolean[]is_using=new Boolean[sdcard];//在oncreate初始化,判断某个内存地址是否已使用
	float earth;//地平面坐标,在oncreate方法初始化
	
	
	//   move_end()→setimfor_game()→→handler(12)→move()  #==========================================================================================
	public void move_end(View view,int Vx,int Vy,int Ax,int Ay,int time){//运动对象，速度(x,y)，加速度(x,y)，运动时间                  
		    int n_=99;
		   for(int i=use;i<sdcard;i++)if(!is_using[i]){ n_=i;break; }  //选用空地址
			final int n=n_;
		    use++; is_using[n]=true; //标记这个地址位使用
		    view_game[n]=view;  time_game[n]=time;
		    v_game[n][0]=Vx;    v_game[n][1]=Vy;
		    a_game[n][0]=Ax;    a_game[n][1]=Ay;
		new Thread(new Runnable(){
			public void run(){ 
				      boolean signx=App_void.signSame(v_game[n][0],a_game[n][0]);//判断速度和加速度符号是否相同
				      boolean signy=App_void.signSame(v_game[n][1],a_game[n][1]);
                   for(int i=0;i<time_game[n];i++){
					   
					   if(v_game[n][0]==0&&v_game[n][1]==0){  break;  }//速度为0，退出
				       
					    if(signx) v_game[n][0]+=a_game[n][0];    //vx
						  else if(App_void.absolute_value_int(v_game[n][0])>App_void.absolute_value_int(a_game[n][0]))
						     v_game[n][0]+=a_game[n][0]; else v_game[n][0]=0;
					
					    if(signy) v_game[n][1]+=a_game[n][1];    //vy
					   	  else { if(App_void.absolute_value_int(v_game[n][1])>App_void.absolute_value_int(a_game[n][1]))
							 v_game[n][1]+=a_game[n][1]; else v_game[n][1]=0; }
							 
					  // setimfor(7,i);
					   setimfor_game(12,view_game[n],v_game[n][0],v_game[n][1],n);
					  try{ Thread.sleep(sleep_time); }catch(Exception e){ }
				   }  use--; is_using[n]=false; //执行结束.清空内存
				}
			}).start();    }
	
			
	public void setimfor_game(int what,View obj,int Vx,int Vy,int n){
		   Message message = new Message();
		   message.what = what;   message.arg1=Vx;
		   message.arg2=Vy;       message.obj=obj;
		   message.sendingUid=n;//传递使用的内存地址
		   handler.sendMessage(message);    }
			
	public void move(View obj,int Vx,int Vy,int n){
		  obj.setX(obj.getX()+Vx);  float h=obj.getHeight();
		  float y=obj.getY()+h;
		  float y1=earth-h;
		if(y+Vy>earth){  //不能超过地平线,达到地平线时把Vy降为0，以便停止运动渲染
			   obj.setY(y1);  v_game[n][1]=0;  }
			 else{
				 if(y+Vy==y1) v_game[n][1]=0; 
				   else obj.setY(y-h+Vy);
			    }  
		}
	//## move_end()→setimfor_game()→handler(12)→move() ==================================================================================================================
				
	
	//## rendering()→setimfor_render()→handler(11)→setbg() ======================================================
	View[] view_render=new View[sdcard];//储存渲染对象
    int[][] drawable_render=new int[sdcard][];//储存动作帧组合
	int[] time_render=new int[sdcard];//储存渲染时间
	Boolean[] y_n_render=new Boolean[sdcard];//储存，是否循环渲染一个动作
	int[] fps_renders=new int[sdcard];
	int use_render=0;//记录已经使用的内存
	Boolean[] is_using_render=new Boolean[sdcard];//在oncreate初始化,判断某个内存地址是否已使用
	
	
	public void rendering(View view,int drawable_id[],int time,Boolean y_n,int fps_render){//渲染
	        int n_=99;
		    for(int i=use_render;i<sdcard;i++)if(!is_using_render[i]){ n_=i;break; }   
		    final int n=n_; 
		    use_render++; is_using_render[n]=true;
		    view_render[n]=view;   drawable_render[n]=drawable_id;
			time_render[n]=time;   y_n_render[n]=y_n;  fps_renders[n]=fps_render;
		  new Thread(new Runnable(){
			  public void run(){ 
			          if(y_n_render[n]){  
					     if(fps_renders[n]>0){ 
						       int sleep=1000/fps_renders[n];
					           int number=drawable_render[n].length;
					         for(int i=0;i<time_render[n]*fps_renders[n]/1000;i++){
							   setimfor_render(11,view_render[n],drawable_render[n][i%number]);
				               try{ Thread.sleep(sleep); }catch(Exception e){   } }
					  	 }
					  }else{
			             int sleep=time_render[n]/drawable_render[n].length;//获取fps
			             if(sleep<sleep_time) sleep=sleep_time;   //如果fps过高，延长渲染时间以降低fpa 
				         for(int i=0;i<drawable_render[n].length;i++){
					        setimfor_render(11,view_render[n],drawable_render[n][i]);
					        try{ Thread.sleep(sleep); }catch(Exception e){ }  }
				     }use_render--; is_using_render[n]=false; //渲染完成，释放内存
			      } 
		  }).start();    }
	
	public void setimfor_render(int what,Object view,int drawable_id){
		    Message message = new Message();
		    message.what=what;  message.arg1=drawable_id;
			message.obj=view;  
			handler.sendMessage(message);  }
//## rendering()→setimfor_render()→handler(11)→setbg() =======================================================
			
	public  void setbg(View view,int id){ //设置背景;动画渲染
		view.setBackground(getResources().getDrawable(id));		}
	
		
	public void up_move(View textview, int v, int a/*减速度*/)//角色移动用，匀速运动方法
	         {    if(a>0){   textview.setY((textview.getY()-v));
					         if(v>a)  v=v-a;  else  v=0;      
					 }else textview.setY((textview.getY()-v));	 	 }
	
    public void down_move(View textview, int v, int a/*减速度*/
	)
	   { if(textview.getY()+textview.getHeight()<earth)//不能超过屏幕%95
	              if(a>0){ textview.setY((textview.getY()+v));
			       if(v>a)  v=v-a;  else  v=0;       
				}else  textview.setY((textview.getY()+v));	    }
	
  public void left_move(View textview, int v, int a/*减速度*/)
	 {	if(a>0){   textview.setX((textview.getX()-v));
		           if(v>a)  v=v-a;  else v=0;       
				   } else	 textview.setX((textview.getX()-v));	    }
	
   public void right_move(View textview, int v, int a/*减速度*/)
	      {   if(a>0){ textview.setX((textview.getX()+v));
			           if(v>a) v=v-a;   else v=0;
		   }else textview.setX((textview.getX()+v));	  }
	
 
//END END ####@@@@@游戏渲染，运动方法区#@@#@#@#@#@#####@@@@@渲染，运动方法区#@@#@#@#@#@#####@@@@@渲染，运动方法区#@@#@#@#@#@#####@@@@@渲染，运动方法区#@@#@#@#@#@#
	
	
	
//######系统设置方法区#############系统设置方法区#############系统设置方法区#############系统设置方法区#############系统设置方法区#############系统设置方法区#######
	
	//public void mainlayout(View layout){  hide_system_bar();    }
	
	public void hide_system_bar(){//隐藏系统通知栏
	        	View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
          	 if(decorView.getSystemUiVisibility()!=option)
                      decorView.setSystemUiVisibility(option);      }
			 
	public static int getwindow_height_or_widht(boolean a,Activity act){//获取屏幕长宽，true返回width,false返回height
		WindowManager wm1 = act.getWindowManager();
		if(a) return wm1.getDefaultDisplay().getWidth();
		else return wm1.getDefaultDisplay().getHeight();   }
	
	public void set_layout(){ //初始化ui布局
		int sys_width = getwindow_height_or_widht(true,this);
		int sys_height = getwindow_height_or_widht(false,this);
		float new_x=sys_width/8;
		float new_y=sys_height*6/10;
		button_up.setX(new_x); button_up.setY(new_y);
		button_down.setX(new_x); button_down.setY(new_y+button_up.getHeight()*2);
		button_left.setX(new_x-button_up.getWidth()); button_left.setY(new_y+button_up.getHeight());
		button_right.setX(new_x+button_up.getWidth()); button_right.setY(new_y+button_up.getHeight());
		button_attackd.setX(new_x*6); button_attackd.setY(new_y+button_up.getHeight());
		Button cs=(Button)findViewById(R.id.button_cs);
		Button re=(Button)findViewById(R.id.rebutton);
		cs.setX(sys_width*87/100);
		re.setX(sys_width*87/100-cs.getWidth()-15); 	}
	

//END END######系统设置方法区#############系统设置方法区#############系统设置方法区#############系统设置方法区#############系统设置方法区#######
	
	
	
}
