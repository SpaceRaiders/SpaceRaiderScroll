import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList ;
/**
 * Oberklasse von SCrollbaren Welten. Implementiert einige Methoden um die Scrollbarkeit zu realisieren. 
 * Da wären einige Methoden um die Liste der Scrollbaren Objekte zu verwalten ( inijObj(), removeObject() )
 * und das eigentliche Scrollen. Hierfür wir din der act() bestimmt, ob und wenn ja wie weit die Rakete über die grenze ( vom Rand aus gesehen ( grenzeX,grenzeY)) geflogen ist und verschiebt dann alle Scrollbaren Objecte auf der WElt um dx,dy.
 * 
 * @author Vitalij Kochno - Yorick Netzer - Christophe Stilmant
 * @version 09-11-2012
 */
public class ScrollableWorld extends World
{
//private ArrayList<greenfoot.Actor> objects = new ArrayList<greenfoot.Actor>();
    private ArrayList<Scrollable> objects = new ArrayList<Scrollable>();
    //private ArrayList<Point> realPos = new ArrayList<Point>();
    private int grenzeX,grenzeY,width=2400,height=1600,shiftX=0,shiftY=0,lvl;
    
    /**
     * Constructor for objects of class Space.
     * 
     */
    public ScrollableWorld(int screenWidth, int screenHeight, int cellsize, int width, int height, int lvl)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(screenWidth, screenHeight, cellsize,false); 
        this.width=width;
        this.height=height;
        grenzeX = screenWidth/2;
        grenzeY= screenHeight/2;
        this.lvl=lvl;
           
    }
    /**
     * @param rocket Objekt von Rocket, das hinzugefügt wird.
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void initRocket(Scrollable rocket, int x,int y)
    {
        addObject(rocket, x, y);
        objects.add(rocket);
        rocket.init(x-shiftX,y-shiftY,this);
        act();
        //realPos.add(new Point(x,y));
    }
    /**
     * @param scrble Objekt einer Subklasse von Scrollable, das hinzugefügt wird.
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void initObj(Scrollable scrble, int x,int y)
    {
        addObject(scrble, x, y);
        objects.add(scrble);
        scrble.init(x-shiftX,y-shiftY,this);
        //realPos.add(new Point(x,y));
    }
    /**
     * Act-Methode der World, achtet auf die Position der Rakete bzw des Ersten Object in Objects ( objects.get(0)) und verschieb alle Objekte dementsprechend.
     * 
     */
    public void act()
    {
        int dx=0,dy=0;
        if(objects.get(0).getX()<grenzeX && objects.get(0).getX()<= objects.get(0).getRealX())
        {
            dx= grenzeX - objects.get(0).getX() ;
            if(shiftX>0)// das hier sollte niemals kommen :D
            {
                System.out.println(shiftX+"  bei If 1");
            }
        }
        if( objects.get(0).getX()>getWidth()-grenzeX && getWidth()-objects.get(0).getX()<= width-objects.get(0).getRealX())
        {
            dx= getWidth()-(grenzeX+objects.get(0).getX());
        }
        if(objects.get(0).getY()<grenzeY && objects.get(0).getY()<= objects.get(0).getRealY())
        {
            dy= grenzeY - objects.get(0).getY()  ;
        }
        if(objects.get(0).getY()>getHeight()-grenzeY && getHeight()-objects.get(0).getY()<= height-objects.get(0).getRealY())
        {
            dy= getHeight()-(grenzeY+objects.get(0).getY());
        }
        //System.out.println("dx: "+dx+"    dy: "+dy);
        
        scroll(dx,dy);
        
        if(Greenfoot.isKeyDown("h"))
        {
            Greenfoot.setWorld(new MainMenu(lvl));
        }
    }
    public void scroll(int dx, int dy)
    {
        //System.out.println("Scroll: "+dx+":dx    "+dy+":dy");
        shiftX+=dx;
        shiftY+=dy;
        for(int i =0; i< objects.size();i++)
        {
            //realPos.get(i).setX();
            
            Actor tmp=objects.get(i);
            //System.out.println("Actor:" + tmp);
            tmp.setLocation(tmp.getX()+dx,tmp.getY()+dy);
            //tmp.setRealLocation(tmp.getRealX()+dx,tmp.getRealY()+dy);
        }
    }
    /**
     * Beim entfernen zu nutzen, amsonsten gibs Exception, weil beim Scrollen auf den Actor zugegriffen wird obwohl er nicht mehr in der Welt ist.
     * 
     */
    public void removeObject(Scrollable scrble)
    {
        super.removeObject(scrble);
        objects.remove(scrble);
    }
    /**
     * 
     */
    public int getRealWidth()
    {
        return width;
    }
    public int getRealHeight()
    {
        return height;
    }
    public void setShift(int shift_x,int shift_y)
    {
        shiftX=shift_x;
        shiftY=shift_y;
    }
    public int getShiftX()
    {
        return shiftX;
    }
    public int getShiftY()
    {
        return shiftY;
    }
}

