package JMockitoTests;

import java.awt.*;
import java.net.InetAddress;

public interface DeTestatAplicatie {
    public Dimension intoarceDimensiuneEcran(String user); //dim ecranului == dim de pe disp curent
    public InetAddress intoarceAdresaCurenta(String user); //adresa
    public String intoarceInterfataCurenta(String user);
}
