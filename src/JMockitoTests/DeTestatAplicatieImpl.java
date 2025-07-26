package JMockitoTests;

import java.awt.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class DeTestatAplicatieImpl {
    private DeTestatAplicatie Dim;
    private DeTestatAplicatie Adr;
    private DeTestatAplicatie Int;
    public DeTestatAplicatieImpl(DeTestatAplicatie Dim,DeTestatAplicatie Adr,DeTestatAplicatie Int){
        this.Dim=Dim; this.Adr=Adr; this.Int=Int;
    }
    public String VerifDimensiuneEcran(String admin){
        //String rez=new String("java.awt.Dimension[width=1536,height=864]");
        Dimension dimensiune_verif=Dim.intoarceDimensiuneEcran("Admin"); //trebuie sa fie acelasi user peste tot
        Dimension dimensiune_adv= Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(dimensiune_adv.height);
        if(dimensiune_adv.width==dimensiune_verif.width && dimensiune_adv.height==dimensiune_verif.height){
            return "Dimensiune corecta";
        }
        else { return "Dimensiune Gresita"; }
    }
    public String VerifInterfataAdresaIpv4(String admin){
        InetAddress adresaipv6_verif=Adr.intoarceAdresaCurenta("Admin");
        InetAddress adresaipv6_adv=null;
        String interfata_verif=Int.intoarceInterfataCurenta("Admin");
        String interfata_adv=new String("notNull");
        Enumeration<NetworkInterface> interfete=null; //nu merge cu List
        try{
            interfete=NetworkInterface.getNetworkInterfaces();
            while(interfete.hasMoreElements()){
                NetworkInterface interfataCurenta=interfete.nextElement();
                if(interfataCurenta.isUp() && interfataCurenta.getName().contains("32768")){
                    interfata_adv=interfataCurenta.getName(); break;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        try{
            adresaipv6_adv=InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        System.out.println(adresaipv6_adv);
        if(adresaipv6_adv==adresaipv6_verif && interfata_adv.equals(interfata_verif)) return "Adresa IP corecta si interfata buna";
        else return "Adresa IP gresita / Interfata gresita";
    }
}
