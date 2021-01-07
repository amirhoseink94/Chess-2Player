import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;

public class G{
	public static chessbord l;
	public static king black=new king(-1);
	public static king white=new king(1);
	public static bord[][] B=new bord[8][8];
	
	}
class MouseHandler implements MouseListener
{
	static StringBuffer whatDo=new StringBuffer();
	static StringBuffer last=new StringBuffer();
	static int turn=1;
	public void mouseClicked( MouseEvent event )
	{
		squer t=(squer)event.getSource();
	//	System.out.println(G.black.whatIsHappen());
		//System.out.println(G.white.whatIsHappen());
		if(!G.white.condition.equals("chekmat")&&!G.black.condition.equals("chekmat")&&(t.getComponentCount()!=0)&& (((man)t.getComponent(0)).getcol()==turn) && (last.length()==0))
		{
			t.setBackground(Color.yellow);
			whatDo.append(((man)t.getComponent(0)).whereCanGo());
			last.append(t.getName());
			tool.coloringOn(whatDo.toString());
		}
		else if((t.getComponentCount()!=0)&& (((man)t.getComponent(0)).getcol()==turn) && (last.length()!=0))
		{
			if((last.charAt(0)+last.charAt(1))%2==0)
				G.l.q[last.charAt(0)-'0'][last.charAt(1)-'0'].setBackground(new Color(249,220,205));
			else
				G.l.q[last.charAt(0)-'0'][last.charAt(1)-'0'].setBackground(new Color(109,44,12));
			tool.coloringOff(whatDo.toString());
			last.delete(0,last.length());
			whatDo.delete(0,whatDo.length());
			t.setBackground(Color.yellow);
			whatDo.append(((man)t.getComponent(0)).whereCanGo());
			last.append(t.getName());
			tool.coloringOn(whatDo.toString());
		}
		if((whatDo.length()!=0)&&(tool.subst(t.getName(),whatDo.toString())))
		{
			man.move(tool.cheng(last.toString()),tool.cheng(t.getName()));
			tool.coloringOff(whatDo.toString());
			if((last.charAt(0)+last.charAt(1))%2==0)
				G.l.q[last.charAt(0)-'0'][last.charAt(1)-'0'].setBackground(new Color(249,220,205));
			else
				G.l.q[last.charAt(0)-'0'][last.charAt(1)-'0'].setBackground(new Color(109,44,12));
			last.delete(0,last.length());
			whatDo.delete(0,whatDo.length());
			if((((man)t.getComponent(0)).getn()==6)&&((t.getName().charAt(0)-'0'==0)||(t.getName().charAt(0)-'0'==7)))
			{
				mass a=new mass();
				a.setact(t.getName().charAt(0)-'0',t.getName().charAt(1)-'0',((man)t.getComponent(0)).getcol());
				a.setVisible(true);
				
			}
			tool.backup();
		//	for(int i=0;i<8;i++){
		//		for(int j=0;j<8;j++)
			//		G.B[i][j].print();
			//System.out.println();
			//}
			G.black.whatIsHappen();
			G.white.whatIsHappen();
			if(G.black.whatIsHappen().equals("mate"))
				JOptionPane.showMessageDialog( null,"Black is Checked", "checked",JOptionPane.INFORMATION_MESSAGE );
			if(G.black.whatIsHappen().equals("checkmate"))
				JOptionPane.showMessageDialog( null,"Black is Checkmate", "checkmate",JOptionPane.INFORMATION_MESSAGE );
			if(G.white.whatIsHappen().equals("mate"))
				JOptionPane.showMessageDialog( null,"White is Checked", "checked",JOptionPane.INFORMATION_MESSAGE );
			if(G.white.whatIsHappen().equals("checkmate"))
				JOptionPane.showMessageDialog( null,"White is Checkmate", "checkmate",JOptionPane.INFORMATION_MESSAGE );
			//System.out.println("WHITE "+G.white.condition);
			//System.out.println("BLACK "+G.black.condition);
			turn*=-1;
		}
	} 
	public void mousePressed( MouseEvent event ){}
	public void mouseReleased( MouseEvent event ){} 
	public void mouseEntered( MouseEvent event ){((squer)event.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());}
	public void mouseExited( MouseEvent event ){((squer)event.getSource()).setBorder(BorderFactory.createRaisedBevelBorder());} 
}
class bord
{
	int col=0;
	int piece=0;
	int numm=0;
	bord(){}
	bord(bord a)
	{
		col=a.col;
		piece=a.piece;
		numm=a.numm;
	}
	void remove(){	
		col=0;
		piece=0;
		numm=0;	}
	void add(bord a,int x,int y)
	{
		if(G.B[x][y].piece==0)
		{
			G.B[x][y].col=a.col;
			G.B[x][y].piece=a.piece;
			G.B[x][y].numm=a.numm;
		}
		else
		{
			G.B[x][y].remove();
			G.B[x][y].col=a.col;
			G.B[x][y].piece=a.piece;
			G.B[x][y].numm=a.numm;
		}
	}
	void print(){ System.out.print("{"+piece+" "+col+" "+" "+numm+"}"); }
	static void backup()
	{
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				G.B[i][j].col=0;
				G.B[i][j].piece=0;
				G.B[i][j].numm=0;
				if(G.l.q[i][j].getComponentCount()!=0){
					G.B[i][j].col=((man)G.l.q[i][j].getComponent(0)).getcol();
					G.B[i][j].piece=((man)G.l.q[i][j].getComponent(0)).getn();
					G.B[i][j].numm=((man)G.l.q[i][j].getComponent(0)).numm;
				}
			}
	}
	static boolean tempMove(int[] s,int[] e)
	{
		backup();
		bord tmp=new bord(G.B[s[0]][s[1]]);
		G.B[s[0]][s[1]].remove();
		tmp.add(tmp,e[0],e[1]);
		if((tmp.col==1)&&(G.white.isSafe()))
			return true;
		if((tmp.col==-1)&&(G.black.isSafe()))
			return true;
		return false;
	}
}
class tool
{
	static int abs(int x){return((x>=0)?x:-x);}
	static boolean cr(int x){	return (((0<=x)&&(x<=7))?true:false);	}
	static int[] cheng(String a)
	{
		int[] w=new int[2];
		w[0]=a.charAt(0)-'0';
		w[1]=a.charAt(1)-'0';
		return w;
	}
	static void backup()
	{
		
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				G.B[i][j].col=0;
				G.B[i][j].piece=0;
				G.B[i][j].numm=0;
				if(G.l.q[i][j].getComponentCount()!=0){
					G.B[i][j].col=((man)G.l.q[i][j].getComponent(0)).getcol();
					G.B[i][j].piece=((man)G.l.q[i][j].getComponent(0)).getn();
					G.B[i][j].numm=((man)G.l.q[i][j].getComponent(0)).numm;
				}
			}
	}
	static String kaj(int x,int y,int col)
	{
		StringBuffer we=new StringBuffer();
		int i=x+1,j=y+1;
		while(tool.cr(i)&&tool.cr(j)&&((G.B[i][j].piece==0)||((G.B[i][j].piece!=0)&&(G.B[i][j].col!=col))))
		{
				we.append(i+""+j+"");
				if(G.B[i][j].piece!=0) break;
				i++;
				j++;
		}
		i=x+1;
		j=y-1;
		while(tool.cr(i)&&tool.cr(j)&&((G.B[i][j].piece==0)||((G.B[i][j].piece!=0)&&(G.B[i][j].col!=col))))
		{
			we.append(i+""+j+"");
			if(G.B[i][j].piece!=0) break;
			i++;
			j--;
		}
		i=x-1;
		j=y+1;
		while(tool.cr(i)&&tool.cr(j)&&((G.B[i][j].piece==0)||((G.B[i][j].piece!=0)&&(G.B[i][j].col!=col))))
		{
			we.append(i+""+j+"");
			if(G.B[i][j].piece!=0) break;
			i--;
			j++;
		}
		i=x-1;
		j=y-1;
		while(tool.cr(i)&&tool.cr(j)&&((G.B[i][j].piece==0)||((G.B[i][j].piece!=0)&&(G.B[i][j].col!=col))))
		{
			we.append(i+""+j+"");
			if(G.B[i][j].piece!=0) break;
			i--;
			j--;
		}
		return we.toString();
	}
	static String rast(int x,int y,int col)
	{
		StringBuffer we=new StringBuffer();
		int i=x+1,j=y;
		while(tool.cr(i)&&((G.B[i][y].piece==0)||((G.B[i][y].piece!=0)&&(G.B[i][y].col!=col))))
		{
			we.append(i+""+y+"");
			if(G.B[i][y].piece!=0) break;
			i++;
		}
		i=x-1;
		while(tool.cr(i)&&((G.B[i][y].piece==0)||((G.B[i][y].piece!=0)&&(G.B[i][y].col!=col))))
		{
			we.append(i+""+y+"");
			if(G.B[i][y].piece!=0) break;
			i--;
		}
		j=y+1;
		while(tool.cr(j)&&((G.B[x][j].piece==0)||((G.B[x][j].piece!=0)&&(G.B[x][j].col!=col))))
		{
			we.append(x+""+j+"");
			if(G.B[x][j].piece!=0) break;
			j++;
		}
		j=y-1;
		while(tool.cr(j)&&((G.B[x][j].piece==0)||((G.B[x][j].piece!=0)&&(G.B[x][j].col!=col))))
		{
			we.append(x+""+j+"");
			if(G.B[x][j].piece!=0) break;
			j--;
		}
		return we.toString();
	}
	static String asb(int x,int y,int col)
	{
		int[][] w=new int[2][];
		int[] wt={2,2,1,1,-2,-2,-1,-1};
		int[] wte={1,-1,2,-2,1,-1,2,-2};
		w[0]=wt;
		w[1]=wte;
		StringBuffer we=new StringBuffer();
		for(int i=0;i<8;i++)
			if(tool.cr(x+w[0][i])&&tool.cr(y+w[1][i])&&((G.B[x+w[0][i]][y+w[1][i]].piece==0)||((G.B[x+w[0][i]][y+w[1][i]].piece!=0)&&(G.B[x+w[0][i]][y+w[1][i]].col!=col))))
				we.append((x+w[0][i])+""+(y+w[1][i])+"");
		return we.toString();
	}
	static boolean subst(String a,String b)
	{
		for(int i=0;i<b.length();i+=2)
			if(b.substring(i,i+2).equals(a)){return true;}
		return false;
	}
	static void coloringOn(String a)
	{
		if(a.length()!=0)
			for(int i=0;i<a.length();i+=2){
				G.l.q[cheng(a.substring(i,i+2))[0]][cheng(a.substring(i,i+2))[1]].setBackground(new Color(17,174,9));
			}
	}
	static void coloringOff(String a)
	{
		if(a.length()!=0)
			for(int i=0;i<a.length();i+=2)
				if((cheng(a.substring(i,i+2))[0]+cheng(a.substring(i,i+2))[1])%2==0)
					G.l.q[cheng(a.substring(i,i+2))[0]][cheng(a.substring(i,i+2))[1]].setBackground(new Color(249,220,205));
				else
					G.l.q[cheng(a.substring(i,i+2))[0]][cheng(a.substring(i,i+2))[1]].setBackground(new Color(109,44,12));
	}
	static String seriOfValidatesMove(int[] s,String seri)
	{
		StringBuffer se=new StringBuffer();
		for(int i=0;i<seri.length();i+=2)
		{
			int[] e=tool.cheng(seri.substring(i,i+2));
			
			if(bord.tempMove(s,e))
				se.append(seri.substring(i,i+2));
		}
		return se.toString();
	}
}
abstract class man extends JPanel
{
	abstract String whereCanGo();
	abstract int getcol();
	abstract int getn();
	int numm=0;
	static void move(int[] s,int[] e)
	{
		man tmp=(man)G.l.q[s[0]][s[1]].getComponent(0);
		if(G.l.q[e[0]][e[1]].getComponentCount()==0)
			{
				G.l.q[s[0]][s[1]].remove(0);
				G.l.q[s[0]][s[1]].revalidate();
				G.l.q[s[0]][s[1]].repaint();
				G.l.q[e[0]][e[1]].add(tmp);
				G.l.q[e[0]][e[1]].revalidate();
				G.l.q[e[0]][e[1]].repaint();
				((man)G.l.q[e[0]][e[1]].getComponent(0)).numm++;
			}
		else
			{
				G.l.q[s[0]][s[1]].remove(0);
				G.l.q[s[0]][s[1]].revalidate();
				G.l.q[s[0]][s[1]].repaint();
				G.l.q[e[0]][e[1]].remove(0);
				G.l.q[e[0]][e[1]].revalidate();
				G.l.q[e[0]][e[1]].repaint();
				G.l.q[e[0]][e[1]].add(tmp);
				G.l.q[e[0]][e[1]].revalidate();
				G.l.q[e[0]][e[1]].repaint();
				((man)G.l.q[e[0]][e[1]].getComponent(0)).numm++;
			}
		if((tmp.getn()==5)&&(s[1]==4)&&(e[1]==6))
		{
			man rk=(man)G.l.q[s[0]][7].getComponent(0);
			G.l.q[s[0]][7].remove(0);
			G.l.q[s[0]][7].revalidate();
			G.l.q[s[0]][7].repaint();
			G.l.q[e[0]][5].add(rk);
			G.l.q[e[0]][5].revalidate();
			G.l.q[e[0]][5].repaint();
			((man)G.l.q[e[0]][5].getComponent(0)).numm++;
		}
		if((tmp.getn()==5)&&(s[1]==4)&&(e[1]==2))
		{
			man rk=(man)G.l.q[s[0]][0].getComponent(0);
			G.l.q[s[0]][0].remove(0);
			G.l.q[s[0]][0].revalidate();
			G.l.q[s[0]][0].repaint();
			G.l.q[e[0]][3].add(rk);
			G.l.q[e[0]][3].revalidate();
			G.l.q[e[0]][3].repaint();
			((man)G.l.q[e[0]][3].getComponent(0)).numm++;
		}
	}
}
class horse extends man
{
	int col;
	int getn(){return 2;}
	horse(int a)
	{
		if(a==1)
			add(new JLabel(new ImageIcon("horsew.png")),BorderLayout.CENTER);
		else
			add(new JLabel(new ImageIcon("horseb.png")),BorderLayout.CENTER);
		col=a;
		setOpaque(false);
	}
	int getcol(){return col;}
	String whereCanGo()
	{
		JPanel where=(squer)getParent();
		int x=where.getName().charAt(0)-'0';
		int y=where.getName().charAt(1)-'0';
		tool.backup();
		return tool.seriOfValidatesMove(tool.cheng(where.getName()),tool.asb(x,y,col));
	}
}
class elephent extends man
{
	int col;
	int getn(){return 3;}
	elephent(int a)
	{
		if(a==1)
			add(new JLabel(new ImageIcon("elephantw.png")),BorderLayout.CENTER);
		else
			add(new JLabel(new ImageIcon("elephantb.png")),BorderLayout.CENTER);
		col=a;
		setOpaque(false);
	}
	int getcol(){return col;}
	String whereCanGo()
	{
		JPanel where=(squer)getParent();
		int x=where.getName().charAt(0)-'0',y=where.getName().charAt(1)-'0';
		tool.backup();
		return tool.seriOfValidatesMove(tool.cheng(where.getName()),tool.kaj(x,y,col));
	}
}
class rook extends man
{
	int col;
	int getn(){return 1;}
	rook(int a)
	{
		if(a==1)
			add(new JLabel(new ImageIcon("rookw.png")),BorderLayout.CENTER);
		else
			add(new JLabel(new ImageIcon("rookb.png")),BorderLayout.CENTER);
		col=a;
		setOpaque(false);
	}
	int getcol(){return col;}
	String whereCanGo()
	{
		JPanel where=(squer)getParent();
		int x=where.getName().charAt(0)-'0',y=where.getName().charAt(1)-'0';
		tool.backup();
		return tool.seriOfValidatesMove(tool.cheng(where.getName()),tool.rast(x,y,col));
	}
}
class queen extends man
{
	int col;
	int getn(){return 4;}
	queen(int a)
	{
		if(a==1)
			add(new JLabel(new ImageIcon("queenw.png")),BorderLayout.CENTER);
		else
			add(new JLabel(new ImageIcon("queenb.png")),BorderLayout.CENTER);
		col=a;
		setOpaque(false);
	}
	int getcol(){return col;}
	String whereCanGo()
	{
		JPanel where=(squer)getParent();
		int x=where.getName().charAt(0)-'0',y=where.getName().charAt(1)-'0';
		tool.backup();
		return tool.seriOfValidatesMove(tool.cheng(where.getName()),tool.kaj(x,y,col)+tool.rast(x,y,col));
	}
}
class pawn extends man
{
	int col;
	int getn(){return 6;}
	pawn(int a)
	{
		if(a==1)
			add(new JLabel(new ImageIcon("pawnw.png")),BorderLayout.CENTER);
		else
			add(new JLabel(new ImageIcon("pawnb.png")),BorderLayout.CENTER);
		col=a;
		setOpaque(false);
	}
	int getcol(){return col;}
	String whereCanGo()
	{
		tool.backup();
		JPanel where=(squer)getParent();
		int x=where.getName().charAt(0)-'0',y=where.getName().charAt(1)-'0';
		StringBuffer we=new StringBuffer();
		int[] wa={1,-1};
		if(col==-1)
		{
			for(int i=0;i<2;i++)
				if(tool.cr(x+1)&&tool.cr(y+wa[i])&&(G.B[x+1][y+wa[i]].piece!=0)&&(G.B[x+1][y+wa[i]].col!=col))
					we.append((x+1)+""+(y+wa[i])+"");
			if(tool.cr(x+1)&&(G.B[x+1][y].piece==0))
				we.append((x+1)+""+y+"");
			if(numm==0)
				if(tool.cr(x+2)&&(G.B[x+2][y].piece==0)&&(G.B[x+1][y].piece==0))
					we.append((x+2)+""+y+"");	
		}
		if(col==1)
		{
			for(int i=0;i<2;i++)
				if(tool.cr(x-1)&&tool.cr(y+wa[i])&&(G.B[x-1][y+wa[i]].piece!=0)&&(G.B[x-1][y+wa[i]].col!=col))
					we.append((x-1)+""+(y+wa[i])+"");
			if(tool.cr(x-1)&&(G.B[x-1][y].piece==0))	
				we.append((x-1)+""+y+"");
			if(numm==0)
				if(tool.cr(x-2)&&(G.B[x-2][y].piece==0)&&(G.B[x-1][y].piece==0))
					we.append((x-2)+""+y+"");
		}
		return tool.seriOfValidatesMove(tool.cheng(where.getName()),we.toString());
	}
	static void replacement(int n,int x,int y,int c)
	{
		G.l.q[x][y].remove(0);
		G.l.q[x][y].revalidate();
		switch (n)
		{
			case 1:
				G.l.q[x][y].add(new rook(c));
				break;
			case 2:
				G.l.q[x][y].add(new horse(c));
				break;
			case 3:
				G.l.q[x][y].add(new elephent(c));
				break;
			case 4:
				G.l.q[x][y].add(new queen(c));
				break;
		}
		G.l.q[x][y].revalidate();
		G.l.q[x][y].repaint();
		tool.backup();
		if(G.black.whatIsHappen().equals("mate"))
				JOptionPane.showMessageDialog( null,"Black is Checked", "checked",JOptionPane.INFORMATION_MESSAGE );
			if(G.black.whatIsHappen().equals("checkmate"))
				JOptionPane.showMessageDialog( null,"Black is Checkmate", "checkmate",JOptionPane.INFORMATION_MESSAGE );
			if(G.white.whatIsHappen().equals("mate"))
				JOptionPane.showMessageDialog( null,"White is Checked", "checked",JOptionPane.INFORMATION_MESSAGE );
			if(G.white.whatIsHappen().equals("checkmate"))
				JOptionPane.showMessageDialog( null,"White is Checkmate", "checkmate",JOptionPane.INFORMATION_MESSAGE );
	}

}
class mass extends JFrame
{
	JRadioButton rk=new JRadioButton("rook",new ImageIcon(""),false);
	JRadioButton kh=new JRadioButton("knight",new ImageIcon(""),false);
	JRadioButton bp=new JRadioButton("bishop",new ImageIcon(""),false);
	JRadioButton qn=new JRadioButton("queen",new ImageIcon(""),false);
	int x,y,z;
	mass(){
		super("replacement");
		setLocationRelativeTo(null);
		setLayout(new GridLayout(5,1));
		setSize(295,140);
		setBackground(Color.white);
		setResizable(false);
		JLabel titel=new JLabel("what do yo want to replace whith pawn?");
		add(titel);
		ButtonGroup collect=new ButtonGroup();
		rk.addItemListener(new RBH());
		collect.add(rk);
		kh.addItemListener(new RBH());
		collect.add(kh);
		bp.addItemListener(new RBH());
		collect.add(bp);	
		qn.addItemListener(new RBH());
		collect.add(qn);
		add(rk);
		add(kh);
		add(bp);
		add(qn);
	}
	void setact(int a,int b,int c){x=a;y=b;z=c;}
	class RBH implements ItemListener
	{			
			public void itemStateChanged( ItemEvent event )
			{
				if(rk.isSelected()){
					pawn.replacement(1,x,y,z);
					dispose();
				}
				if(kh.isSelected()){
					pawn.replacement(2,x,y,z);
					dispose();
				}
				if(bp.isSelected()){
					pawn.replacement(3,x,y,z);
					dispose();
				}
				if(qn.isSelected()){
					pawn.replacement(4,x,y,z);
					dispose();
				}
				tool.backup();
			}
	}
}		
class king extends man
{
	int col;
	int getn(){return 5;}
	StringBuffer condition=new StringBuffer();
	king(int a)
	{
		if(a==1)
			add(new JLabel(new ImageIcon("kingw.png")),BorderLayout.CENTER);
		else
			add(new JLabel(new ImageIcon("kingb.png")),BorderLayout.CENTER);
		col=a;
		setOpaque(false);
	}
	int getcol(){return col;}
	String whereCanGo()
	{
		tool.backup();
		JPanel where=(squer)getParent();
		int x=where.getName().charAt(0)-'0',y=where.getName().charAt(1)-'0';
		int[][] wa=new int[2][];
		int[] re={1,-1,0,0,1,1,-1,-1};
		int[] re2={0,0,1,-1,1,-1,1,-1};
		wa[0]=re;
		wa[1]=re2;
		StringBuffer we=new StringBuffer();
		for(int i=0;i<8;i++)
			if(tool.cr(x+wa[0][i])&&tool.cr(y+wa[1][i])&&((G.B[x+wa[0][i]][y+wa[1][i]].piece==0) || ((G.B[x+wa[0][i]][y+wa[1][i]].piece!=0)&&(G.B[x+wa[0][i]][y+wa[1][i]].col!=col))))
				we.append((x+wa[0][i])+""+(y+wa[1][i])+"");
		int[] f={0,4};
		int[] roS={0,5};
		int[] roB1={0,3};
		int[] roB2={0,2};
		if((col==-1)&&(numm==0)&&condition.toString().equals("safe")&&(G.B[0][5].piece==0)&&(G.B[0][6].piece==0)&&(bord.tempMove(f,roS))&&(G.B[0][7].piece==1)&&(G.B[0][7].numm==0)){
		//System.out.println("hoy");
			we.append("06");
			}
		if((col==-1)&&(numm==0)&&condition.toString().equals("safe")&&(G.B[0][3].piece==0)&&(G.B[0][2].piece==0)&&(bord.tempMove(f,roB1))&&(G.B[0][0].piece==1)&&(G.B[0][0].numm==0))
			we.append("02");
		f[0]=roS[0]=roB1[0]=roB2[0]=7;
		if((col==1)&&(numm==0)&&condition.toString().equals("safe")&&(G.B[7][5].piece==0)&&(G.B[7][6].piece==0)&&(bord.tempMove(f,roS))&&(G.B[7][7].piece==1)&&(G.B[7][7].numm==0)){
	//	System.out.println("hoy");
			we.append("76");
			}
		if((col==1)&&(numm==0)&&condition.toString().equals("safe")&&(G.B[7][3].piece==0)&&(G.B[7][2].piece==0)&&(bord.tempMove(f,roB1))&&(G.B[7][0].piece==1)&&(G.B[7][0].numm==0))
			we.append("72");
		///System.out.println("log "+we);
 		return tool.seriOfValidatesMove(tool.cheng(where.getName()),we.toString());
	}
	int[] getloc(){
		int[] w=new int[2]; 
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if((G.B[i][j].piece==5)&&(G.B[i][j].col==col))
				{
					w[0]=i;
					w[1]=j;
					i=8;
					break;
				}
		return w;
	}
	boolean isSafe()
	{
		int x=getloc()[0];
		int y=getloc()[1];
		String a=tool.kaj(getloc()[0],getloc()[1],col);
		String b=tool.rast(getloc()[0],getloc()[1],col);
		String c=tool.asb(getloc()[0],getloc()[1],col);
		int[] wa={1,-1};
		for(int i=0;i<2;i++)
			if((tool.cr(x+wa[i])&&(G.B[x+wa[i]][y].piece==5)&&(G.B[x+wa[i]][y].col==-col)))
				return false;
		for(int i=0;i<2;i++)
			if((tool.cr(y+wa[i])&&(G.B[x][y+wa[i]].piece==5)&&(G.B[x][y+wa[i]].col==-col)))
				return false;
		for(int i=0;i<b.length();i+=2)
			if((G.B[b.charAt(i)-'0'][b.charAt(i+1)-'0'].col==-col)&&((G.B[b.charAt(i)-'0'][b.charAt(i+1)-'0'].piece==4)||(G.B[b.charAt(i)-'0'][b.charAt(i+1)-'0'].piece==1)))
				return false;
		for(int i=0;i<c.length();i+=2)
			if((G.B[c.charAt(i)-'0'][c.charAt(i+1)-'0'].col==-col)&&(G.B[c.charAt(i)-'0'][c.charAt(i+1)-'0'].piece==2))
				return false;
		if(col==-1){
			for(int i=0;i<2;i++)
				if((tool.cr(x+1)&&tool.cr(y+wa[i])&&((G.B[x+1][y+wa[i]].piece==6)||(G.B[x+1][y+wa[i]].piece==5))&&(G.B[x+1][y+wa[i]].col==-col)))
					return false;
			for(int i=0;i<2;i++)
				if((tool.cr(x-1)&&tool.cr(y+wa[i])&&(G.B[x-1][y+wa[i]].piece==5)&&(G.B[x-1][y+wa[i]].col==-col)))
					return false;
		}		
		if(col==1){
			for(int i=0;i<2;i++)
				if((tool.cr(x-1)&&tool.cr(y+wa[i])&&((G.B[x-1][y+wa[i]].piece==6)||(G.B[x-1][y+wa[i]].piece==5))&&(G.B[x-1][y+wa[i]].col==-col)))
					return false;
			for(int i=0;i<2;i++)
				if((tool.cr(x+1)&&tool.cr(y+wa[i])&&(G.B[x+1][y+wa[i]].piece==5)&&(G.B[x+1][y+wa[i]].col==-col)))
					return false;
		}
		for(int i=0;i<a.length();i+=2)
			if((G.B[a.charAt(i)-'0'][a.charAt(i+1)-'0'].col==-col)&&((G.B[a.charAt(i)-'0'][a.charAt(i+1)-'0'].piece==4)||(G.B[a.charAt(i)-'0'][a.charAt(i+1)-'0'].piece==3)))
				return false;	
		return true;
		
	}
	String whatIsHappen()
	{
		tool.backup();
		condition.delete(0,condition.length());
		if(isSafe()){
			condition.append("safe");
			return "safe";
		}
		else
		{
			int l=0;
			for(int i=0;i<8;i++)
				for(int j=0;j<8;j++)
					if(G.B[i][j].col==col){
						l+=((man)G.l.q[i][j].getComponent(0)).whereCanGo().length();
						tool.backup();
						}
			if(l==0){
				condition.append("checkmate");
				return "checkmate";
				}
			else{
				condition.append("mate");
				return "mate";
				} 
		}
	}
}
class squer extends JPanel
{
	MouseHandler handler = new MouseHandler();
	squer(){
	addMouseListener( handler );
	}
}

class chessbord extends JFrame
{
	squer[][] q=new squer[8][8];
	chessbord()
	{
		super("Chess");
		setSize(800,800);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(8,8));
		MouseHandler handler = new MouseHandler();
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				StringBuffer and=new StringBuffer();
				q[i][j]=new squer();
				if((i+j)%2==0){
					q[i][j].setBackground(new Color(249,220,205));
					and.append(i);
					and.append(j);
					q[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
					q[i][j].setToolTipText(and+"");
					q[i][j].setName(and+"");
					}
				else{
					q[i][j].setBackground(new Color(109,44,12));
					and.append(i);
					and.append(j);
					q[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
					q[i][j].setToolTipText(and+"");
					q[i][j].setName(and+"");
					}
				add(q[i][j]);
			}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
}

class chess
{
	public static void main(String[] args)
	{
		chessbord bord=new chessbord();
		G.l=bord;
		bord.q[0][0].add(new rook(-1));
		bord.q[0][0].repaint();
		bord.q[0][7].add(new rook(-1));
		bord.q[0][7].repaint();
		bord.q[0][1].add(new horse(-1));
		bord.q[0][1].repaint();
		bord.q[0][6].add(new horse(-1));
		bord.q[0][6].repaint();
		bord.q[0][2].add(new elephent(-1));
		bord.q[0][2].repaint();
		bord.q[0][5].add(new elephent(-1));
		bord.q[0][5].repaint();
		bord.q[0][3].add(new queen(-1));
		bord.q[0][3].repaint();
		bord.q[0][4].add(G.black);
		bord.q[0][4].repaint();
		bord.q[7][0].add(new rook(1));
		bord.q[7][0].repaint();
		bord.q[7][7].add(new rook(1));
		bord.q[7][7].repaint();
		bord.q[7][1].add(new horse(1));
		bord.q[7][1].repaint();
		bord.q[7][6].add(new horse(1));
		bord.q[7][6].repaint();
		bord.q[7][2].add(new elephent(1));
		bord.q[7][2].repaint();
		bord.q[7][5].add(new elephent(1));
		bord.q[7][5].repaint();
		bord.q[7][3].add(new queen(1));
		bord.q[7][3].repaint();
		bord.q[7][4].add(G.white);
		bord.q[7][4].repaint();		
		for(int i=0;i<8;i++)
		{
			bord.q[1][i].add(new pawn(-1));
			bord.q[1][i].repaint();
			bord.q[6][i].add(new pawn(1));
			bord.q[6][i].repaint();
		}
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				G.B[i][j]=new bord(); 
		tool.backup();
		G.white.whatIsHappen();
		G.black.whatIsHappen();
	//	System.out.println("WHITE "+G.white.condition);
	//	System.out.println("BLACK "+G.black.condition);
		bord.repaint();
		bord.setVisible(true);
	}
}
