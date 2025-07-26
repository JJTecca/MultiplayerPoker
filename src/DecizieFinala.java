class DecizieFinala  {
    private String RoyalFlush=null;
    Resources resurseTotale=null;
    private int contor_player1[]=new int[15];
    private int contor_player2[]=new int[15];
    private int contor_dealer[]=new int[15];
    private int suma_carti_player1,suma_carti_player2;
    private int max_player1,max_player2;
    public DecizieFinala(Resources resurseTotale){
        this.resurseTotale=resurseTotale;
        for(int i=0;i<15;i++){ contor_player1[i]=contor_player2[i]=contor_dealer[i]=0; }
        RoyalFlush=new String("10JQKA");
        max_player1=max_player2=0; suma_carti_player1=suma_carti_player2=0;
    }
    public int convert(String carte){
        if(carte.contains("J")) return 11;
        if(carte.contains("Q")) return 12;
        if(carte.contains("K")) return 13;
        if(carte.contains("A")) return 14;
        return carte.charAt(0)-'0';
    }
    public void contorCarti(){
        for(int index_carti=0;index_carti<51;index_carti++){
            if(resurseTotale.folosit[index_carti]==1){ //caz particular pt 10
                int de_convertit=convert(resurseTotale.carti_existente[index_carti]);
                System.out.println(de_convertit + " "+resurseTotale.nume_jucatori_poz[index_carti]);
                if(resurseTotale.nume_jucatori_poz[index_carti].equals("player 1")){
                    contor_player1[de_convertit]++; suma_carti_player1+=de_convertit;
                }
                if(resurseTotale.nume_jucatori_poz[index_carti].equals("player 2")){
                    contor_player2[de_convertit]++; suma_carti_player2+=de_convertit;
                }
                if(resurseTotale.nume_jucatori_poz[index_carti].equals("dealer"))
                    contor_dealer[de_convertit]++;
            }
        }
        for(int i=0;i<15;i++){
            if(contor_player1[i]!=0) {
                contor_player1[i] = contor_player1[i] + contor_dealer[i];
                if(contor_player1[i]>=max_player1)
                    max_player1=contor_player1[i];
                System.out.println("cartea numarul=" + i + "de atatea ori " + contor_player1[i]);
            }
            if(contor_player2[i]!=0){
                contor_player2[i]=contor_player2[i]+contor_dealer[i];
                if(contor_player2[i]>=max_player2)
                    max_player2=contor_player2[i];
                System.out.println("cartea numarul="+i+"de atatea ori "+contor_player2[i]);
            }
        }
        if(max_player1>max_player2 || (max_player1==max_player2 && suma_carti_player1>suma_carti_player2)) { new OptionPane("Jucatorul 1 a castigat"); }
        else {
            if(max_player2>max_player1 || (max_player2==max_player1 && suma_carti_player2>suma_carti_player1)) { new OptionPane("Jucatorul 2 a castigat"); }
            else { new OptionPane("Egalitate"); }
        }
    }
}

//2 3 4 5 6 7 8 9 10 J Q K A