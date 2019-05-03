//Teken de mandelbrot figuur

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Mandelbrot extends Applet 
{	
  double MandelXMin, MandelXMax, MandelYMin,MandelYMax;  
  int tekenXmin, tekenYmin, B,H;
  int MaxIt;  
  boolean MandelJa;
  Mandel Mandel1;
  Button WisselKnop;
  TextField txtMaxIteraties,txtVergroting;
  Label lblAantalIteraties,lblVergroting; 
  Tekenvel Mandeltek; 
  Choice Kleurschemalijst;
  int Kleurcode;
  
  public void init() 
	{	
	setLayout(null);
    setBackground(Color.black); 
    tekenXmin = 10;
    tekenYmin = 10;
    B = 500;
    H = 330;    
    MaxIt = 40;
    MandelXMin = -2.1;
    MandelXMax = 0.9;
    MandelYMin = -1.2;
    MandelYMax = 1.2;
    MandelJa = true;
    //Canvas
    Mandeltek = new Tekenvel();
    Mandeltek.setBounds(tekenXmin,tekenYmin,B,H); 
    add(Mandeltek); 
    //Kleurschema kiezen
    Kleurschemalijst = new Choice();
    Kleurschemalijst.addItem("Regenboog");
    Kleurschemalijst.addItem("Blauw");
    Kleurschemalijst.addItem("RoodGroenBlauw");
    Kleurschemalijst.addItem("Zwart-wit");
    Kleurschemalijst.setBounds(100,380,150,20);
    add(Kleurschemalijst);
    Kleurschemalijst.addItemListener( new KeuzeHandler());
        
    Mandel1 = new Mandel(B,H,MaxIt,MandelXMin, MandelXMax,
              MandelYMin,MandelYMax);            
    //Knop Invoegen
    WisselKnop = new Button("Julia");
    WisselKnop.setBounds(10,350,100,20);
    WisselKnop.addActionListener( new WisselKnopHandler());
    add(WisselKnop); 
    txtMaxIteraties = new TextField();
    txtMaxIteraties.setText("" + MaxIt);
    txtMaxIteraties.setBounds(210,350,50,20);
    txtMaxIteraties.setForeground(Color.black);
    txtMaxIteraties.setBackground(Color.white);
    txtMaxIteraties.addActionListener( new TxtItHandler());
    add(txtMaxIteraties);
    //vergrotingstekst
    txtVergroting = new TextField();
    txtVergroting.setBounds(350,350,60,20); 
    txtVergroting.setText("1"); 
    txtVergroting.setEnabled(false);
    txtVergroting.setForeground(Color.black);
    txtVergroting.setBackground(Color.white);
    add(txtVergroting);
  }
	
	class Tekenvel extends Canvas {
  
    public Tekenvel() {    	
  	  addMouseListener( new Muishandler());  	  	
    }	 
  
    public void paint(Graphics g) {   
    	Mandel1.Teken(g,MandelJa);
    }	  
  
    //inwendige klasse
    class Muishandler extends MouseAdapter {
  	  public void mouseClicked(MouseEvent e) {
  		  int X,Y;
  		  long Vergroting;
  		
  		  X = e.getX();
  		  Y = e.getY();  		  
  			//MaxIt kan ook zijn veranderd
  		  Mandel1.setMaxIt(Integer.parseInt(txtMaxIteraties.getText())); 		
  		  Mandel1.Inzoomen(X,Y); //stelt nieuwe waarden in voor Mandel  		
  		  repaint();
  		  Vergroting = Integer.parseInt(txtVergroting.getText());
  		  Vergroting *=2;
  		  txtVergroting.setText("" + Vergroting);  		    		    
   	  }  	
    }      
  }
		
	public void paint( Graphics g)
	{     
    g.setColor(Color.yellow);    
    g.drawString("Aantal iteraties:",120,360);
    g.drawString("Vergroting:",270,360);
    g.drawString("Kleurschema:",10,390);    
	}	  
    
  class KeuzeHandler implements ItemListener {
  	public void itemStateChanged ( ItemEvent e) {
  		Mandeltek.repaint();
  	}	 
  }	 
  
  class WisselKnopHandler implements ActionListener {	
		public void actionPerformed (ActionEvent e) {
			String strLabel;
			
			strLabel = WisselKnop.getLabel();
			
			if (strLabel.equals("Julia")) { 
			  //teken Julia-set
			  WisselKnop.setLabel("Mandelbrot");
			  Mandel1.setJuliaY();
				Mandel1.setJuliaX();
			  Mandel1.setMandelXMin(-1.5);
			  Mandel1.setMandelXMax(1.5);
 			  Mandel1.setMandelYMin(-1.5);
			  Mandel1.setMandelYMax(1.5);			  
			  MandelJa = false;
			  txtVergroting.setText("1");
			}
			else {
				WisselKnop.setLabel("Julia");			  
			  Mandel1.setMandelXMin(-2.1);
			  Mandel1.setMandelXMax(0.9);
 			  Mandel1.setMandelYMin(-1.2);
			  Mandel1.setMandelYMax(1.2);			  
				MandelJa = true;
				txtVergroting.setText("1");
			}					  			
		  Mandeltek.repaint();
		}		
	}   
   
  
  class TxtItHandler implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			 Mandel1.setMaxIt(Integer.parseInt(txtMaxIteraties.getText()));
			 Mandeltek.repaint();
		}
	}		
  
  class Mandel {
     private int B, H,  MaxIt;    
     private double MandelXMin, MandelXMax, MandelYMin, MandelYMax;   
     double JuliaX, JuliaY;      
     
     //de constructor
     public Mandel(int B,int H,int MaxIt,
                   double MandelXMin, double MandelXMax, 
                   double MandelYMin, double MandelYMax) {
       
       this.B = B;
       this.H = H;
       this.MaxIt = MaxIt;
       this.MandelXMin = MandelXMin;
       this.MandelXMax = MandelXMax;
       this.MandelYMin = MandelYMin;
       this.MandelYMax = MandelYMax;       
     }              	                
     
     public void setMaxIt(int MaxIt) {
     	 this.MaxIt =MaxIt;     	
     }     
     
     public void setJuliaX() {
     	 this.JuliaX = (this.MandelXMax + this.MandelXMin) /2;     	
     }
     
     public void setJuliaY() {
     	 this.JuliaY = (this.MandelYMax + this.MandelYMin) /2;     	
     }     
    
     public void setMandelXMin(double Mandelwaarde) {
     	 MandelXMin = Mandelwaarde;     	
     }
     
     
     public void setMandelXMax(double Mandelwaarde) {
     	 MandelXMax = Mandelwaarde;     	
     }
     
     public void setMandelYMin(double Mandelwaarde) {
     	 MandelYMin = Mandelwaarde;     	
     }
     
     public void setMandelYMax(double Mandelwaarde) {
     	 MandelYMax = Mandelwaarde;     	
     }
     
     
     public int getTekenXMax() {
     	 return B;
     }
     
       
     public int getTekenYMax() {
     	 return H;     	
     }     	
     
     public void Teken(Graphics g,boolean MandelJa) {
     	  double MandelXstap, MandelYstap; 
     	  double MandelX, MandelY;   
        int BlokG, BlokYH, BlokXH;
        int Kleurnr, AantalIt;
                
        BlokG = 4;
        
        //eerst grof tekenen daarna steeds verfijnder    
        MandelXstap = (double) (MandelXMax - MandelXMin) / (B/BlokG) ;
        MandelYstap = (double) (MandelYMax - MandelYMin) / (H/BlokG);        
    
        for (int tekenRonde = 0; tekenRonde <3; tekenRonde++) { 
          MandelX = MandelXMin;  
          for (int X= 0; X<B; X+=BlokG){    	
    	      MandelY = MandelYMin;
    	      for (int Y = 0; Y<H; Y+=BlokG) {    		
            //het aantal iteraties is bepalend voor de kleur van het blokje
              if (MandelJa == true) {              
                //Mandelbrot
                AantalIt = AantalMIteraties(MandelX,MandelY,MaxIt);                     
              } 
              else {
              	//Julia-set             	  
                AantalIt = AantalJIteraties(MandelX,MandelY,
                                            JuliaX,JuliaY,MaxIt);
              }  
              g.setColor(BepaalKleur(AantalIt,MaxIt));
              TekenBlokje(X,Y,BlokG, B,H,g);           	    
    	        MandelY += MandelYstap;
    	      }	
    	      MandelX += MandelXstap;
          }
          if (BlokG > 1) {
            BlokG = BlokG / 2;
            MandelXstap /=2;
            MandelYstap /=2;  
          }
        }       
     }	//van de methode     
     
    public void Inzoomen(int MuisPX,int MuisPY) {
      double XAfstand, YAfstand;	  
      double MandelX,MandelY; 
      double MandelXstap, MandelYstap;
       
      //vergroten
      MandelXstap = (double) (MandelXMax - MandelXMin)/ B;
      MandelYstap = (double) (MandelYMax - MandelYMin)/ H;       
      XAfstand = (MandelXMax - MandelXMin) / 2;
      YAfstand = (MandelYMax - MandelYMin) / 2;      
      MandelX = MandelXMin + MandelXstap * MuisPX; 
      MandelY = MandelYMin + MandelYstap * MuisPY;
      //instellen van nieuwe Mandel-waarden
      MandelXMin = MandelX - XAfstand /2;
      MandelXMax = MandelX + XAfstand /2;
      MandelYMin = MandelY - YAfstand /2;
      MandelYMax = MandelY + YAfstand /2;    
  
      this.setMandelXMin(MandelXMin);
      this.setMandelXMax(MandelXMax);
      this.setMandelYMin(MandelYMin);
      this.setMandelYMax(MandelYMax);
    }  
  }	//Van de Class
  
  
  int AantalMIteraties(double BeginX, double BeginY, int MaxIt) {
    int AantalIt = 0;  
    double NewX, NewY, OldY,OldX;    
    double Z = 0.0;
    
    OldX = BeginX;  OldY = BeginY;    
      
    while ((AantalIt < MaxIt) & Z<10.0) {
      NewX = 	Sqr(OldX) - Sqr(OldY) + BeginX;
      NewY =  2 * OldX * OldY + BeginY;            
      Z =  Sqr(NewX) + Sqr(NewY);
      OldX = NewX; OldY = NewY; 
      AantalIt++;            
    }    	
    return AantalIt; 
  }	
  
  int AantalJIteraties(double BeginX, double BeginY,
                       double XMidden, double YMidden, int MaxIt) {
    int AantalIt = 0;  
    double NewX, NewY, OldY,OldX;    
    double Z = 0.0;
    //voor de Julia-set
    OldX = BeginX;  OldY = BeginY;    
      
    while ((AantalIt < MaxIt) & Z<10.0) {
      NewX = 	Sqr(OldX) - Sqr(OldY) + XMidden;
      NewY =  2 * OldX * OldY + YMidden;            
      Z =  Sqr(NewX) + Sqr(NewY);
      OldX = NewX; OldY = NewY; 
      AantalIt++;            
    }    	
    return AantalIt; 
  } //van methode
  
  double Sqr(double Getal) {
  	double Kwadraat;
  	Kwadraat = Getal * Getal;
  	return Kwadraat;
  }	
  
  //***************
  Color BepaalKleur(int AantalIt, int MaxIt){
    int Kleurnr;
    Color Kleur;   
    double Kleurhulp; //voor de type casting
    int intHulpIt; //tientallen 
    
    Kleur = new Color(0);
    Kleurcode = Kleurschemalijst.getSelectedIndex();
    switch (Kleurcode) {      
      case 0: 
        Color[] Kleurrij = {Color.black,Color.blue,Color.cyan,Color.green,
        Color.magenta, Color.pink,Color.red,
        Color.yellow,Color.orange}; //zwart + 8 kleuren 
    
        if (AantalIt < MaxIt) {
        Kleurnr = (AantalIt % 8)+1; //kleuren                            
        }
        else {
          Kleurnr = 0; //zwart          
        }        
        Kleur = Kleurrij[Kleurnr]; 
        break;
      case 1: //Blauw        
        if (AantalIt < MaxIt) {                     
          Kleurhulp = (double) AantalIt / (double) MaxIt;      	          
          Kleurnr = (int) (Kleurhulp  * 255);                              
        }        
        else {
          Kleurnr = 0; //zwart
        }        
        Kleur = new Color(0,0,Kleurnr);    
        break;
     case 2: //RoodGroenBlauw        
        intHulpIt = 0;
        if (AantalIt < MaxIt) {                                     
           
           Kleurhulp = ((double) AantalIt % 20)/20;            
           Kleurnr = (int) (Kleurhulp * 255);
           intHulpIt = AantalIt - (AantalIt % 20);
           intHulpIt = (int) (intHulpIt / 20);
           intHulpIt = (intHulpIt) % 7;           
        }        
        else {
          Kleurnr = 0; //zwart
        }        
        
        switch (intHulpIt) {
          case 0:           
            Kleur = new Color(Kleurnr,0,0);    
          break;
          case 1:
            Kleur = new Color(Kleurnr,Kleurnr,0); 
          break;
          case 2:
            Kleur = new Color(0,Kleurnr,0);                           
          break;
          case 3:
            Kleur = new Color(0,Kleurnr,Kleurnr); 
          break;
          case 4:
            Kleur = new Color(0,0,Kleurnr); 
          break;
          case 5:
            Kleur = new Color(Kleurnr,0,Kleurnr); 
          break;
          case 6:
            Kleur = new Color(Kleurnr,Kleurnr,Kleurnr); 
          break;
        }        
        break;  
      case 3: //Zwart-wit                
        if (AantalIt < MaxIt) {
        Kleur = Color.black; //zwart
        }
        else {
          Kleur = Color.white; //wit          
        }        
        break;      
    }    
    return Kleur;
  }	
     
  void TekenBlokje(int X,int Y,int BlokG, int MaxXlok,int MaxYlok,Graphics g) { 
  int BlokXG, BlokYG;
  
    BlokXG = BlokG; 
    if (X + BlokG > MaxXlok) {
      BlokXG = MaxXlok - X;
    }
    
    BlokYG = BlokG;
    if (Y + BlokG > MaxYlok) {
      BlokYG = MaxYlok - Y;
    }				
    g.fillRect(X,Y,BlokXG,BlokYG);  	
  }	         
}
