import java.util.Random;

public class Resources {
    protected int numar_playeri_live;
    protected boolean extrase=false;
    protected String Hashcode=null;
    protected String[] nume_jucatori_poz =new String[51];
    //protected String carteLuataDealer=null;
    public String[] carti_existente=new String[] {"2-C","2-D","2-H","2-S","3-C","3-D","3-H","3-S",
                "4-C","4-D","4-H","4-S","5-C","5-D","5-H","5-S", "6-C","6-D","6-H","6-S", "7-C","7-D","7-H","7-S",
                "8-C","8-D","8-H","8-S","9-C","9-D","9-H","9-S","10-C","10-D","10-H","10-S",
                "A-C","A-D","A-H","A-S","J-C","J-D","J-H","J-S","K-C","K-D","K-H","K-S","Q-C","Q-D","Q-H","Q-S"
    };
    protected int folosit[]=new int[51];//nu merge cu Integer
    Resources(int folosit[],String Hashcode,boolean extrase){
        System.out.println("\nAi luat pachetul copie de la server");
        for(int index_folosit=0;index_folosit<51;index_folosit++){ folosit[index_folosit]=0; nume_jucatori_poz[index_folosit]=null;}
        numar_playeri_live=1; //deja avem dealer
        this.Hashcode=Hashcode;
        this.extrase=extrase;
    }
    public void marcatLuat(String card,int esteDealer[],String Hashcode){
        for(int carte_curenta=0;carte_curenta<carti_existente.length;carte_curenta++){
            if(card.contains(carti_existente[carte_curenta])) {
                folosit[carte_curenta] = 1; nume_jucatori_poz[carte_curenta]=Hashcode;
                break;
                //nume_jucatori_poz[carte_curenta]=Hashcode; Unreachable statement
            }
        }
    }
    public void AfisareNumarPlayeriLive(){
        System.out.println("In joc se afla "+numar_playeri_live+ " jucatori cu tot cu dealer!");
    }
}
class Dealer extends Resources{
    int numar_carti_extrase,pozitie;
    private String carteLuataDealer=null;
    Dealer(int Folosit[],int numar_carti_extrase,String Hashcode,boolean extrase){
        super(Folosit,Hashcode,extrase); this.numar_carti_extrase=numar_carti_extrase; pozitie=0;
        System.out.println("Codul dealerului "+Hashcode);
    }
    String IaCarte(){
        Random getRand=new Random(); int X=getRand.nextInt(0,50);
        while(folosit[X]!=0){ X=getRand.nextInt(0,50); } //cauta pana cand gasesti cv cu 0
        folosit[X]=1;
        numar_carti_extrase++; carteLuataDealer=carti_existente[X];
        return carteLuataDealer+".png";
    }
    public void marcatLuat(String card,int esteDealer[],String Hashcode){
        for(int carte_curenta=0;carte_curenta<carti_existente.length;carte_curenta++){
            if(card.contains(carti_existente[carte_curenta])) {
                esteDealer[carte_curenta]=folosit[carte_curenta]=1;
                break;
            }
        }
    }
}
class Jucator extends Resources{
    protected int numar_carti_extrase,pozitie;
    protected String carteLuataJucator =null;
    Jucator(int folosit[],String Hashcode_jucator,int numar_carti_extrase,boolean extrase){
        super(folosit,Hashcode_jucator,extrase); this.numar_carti_extrase=numar_carti_extrase; pozitie=0;
        System.out.println("Codul jucatorului: "+Hashcode_jucator);
    }
    String IaCarte(){
        Random getRand=new Random(); int X=getRand.nextInt(0,50);
        while(folosit[X]!=0){ X=getRand.nextInt(0,50); } //cauta pana cand gasesti cv cu 0
        folosit[X]=1;
        numar_carti_extrase++; carteLuataJucator=carti_existente[X];
        return carteLuataJucator+".png"; //carti_existente[X]+".png";
    }
    public boolean verificareGeneralaArrays(String card,int esteDealer[]){
        for(int i=0;i<carti_existente.length;i++){
            if(card.contains(carti_existente[i]) && esteDealer[i]==1)
                return false;
            //adica daca am card=8-D.png si if(8-D.png contine 8-D si 1==1) returneaza fals ca a fost marcat
        }
        return true;
    }
    public void marcatLuat(String card,int esteDealer[],String Hashcode){ //aici marchez ca nu este vorba de dealer
        while(verificareGeneralaArrays(card,esteDealer)==false){ card=IaCarte();}
        //return false inseamna ca cartea a fost marcata si trb extrasa alta
        carteLuataJucator=card; //la iesire din functia card nu se va modifica , trb modif. membrul clasei
        System.out.println("din verif "+card);
        for(int carte_curenta=0;carte_curenta<carti_existente.length;carte_curenta++){
            if(card.contains(carti_existente[carte_curenta])) {
                esteDealer[carte_curenta]=folosit[carte_curenta]=1;
                break;
            }
        }
    }
    public void f(){
        for(int i=0;i<51;i++){
            if(folosit[i]==1)
                System.out.println(carti_existente[i]);
        }
    }
}
//polimorfism: pt resurse marcam doar folosit[] cu 1
//             pt dealer marcam resurseTotale si folosit[] cu 1
//             pt jucator while+marcam reshrseTotale si folosit[] cu 1
//clasa decizie cu maini top poker care extinde resources si decide castigatorul