package tcpobject;

import jfx.Speed;
import util.NetworkUtil;

public class WriteThreadServer implements Runnable {
    Thread t;
    NetworkUtil nc1,nc2;
    boolean pl1Playing,pl2Playing;

    public WriteThreadServer(NetworkUtil nc1, NetworkUtil nc2) {
        this.nc1 = nc1;
        this.nc2 = nc2;
        this.t = new Thread(this);
        pl1Playing = true;
        pl2Playing = false;
    }

    @Override
    public void run() {
        Speed s1,s2;
        try{
            Object o;
            while (true) {
                o = nc1.read();
                if (o != null) {
                    if (o instanceof Speed) {
                        s1 = (Speed) o;
                        if (s1.getVelocity() == 0) {
                            pl1Playing = false;
                            pl2Playing = true;
                        }
                        nc2.refresh();
                        nc2.write(s1);
                    }
                }


                o = nc2.read();
                if (o != null) {
                    if (o instanceof Speed) {
                        s2 = (Speed) o;
                        if (s2.getVelocity() == 0) {
                            pl1Playing = true;
                            pl2Playing = false;
                        }
                        nc1.refresh();
                        nc1.write(s2);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
