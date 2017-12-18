/**
 * Created by HP on 06.12.2017.
 */
public class Man {
    boolean alive =true;
    public void die(){ alive = false; }
    public class Heart{
        public void stop(){die();}
    }

    public static void main(String[] args) {
        Man belarus = new Man();
        Man.Heart heart = belarus.new Heart();
        System.out.println(belarus.alive);
        heart.stop();
        System.out.println(belarus.alive);
    }
}
