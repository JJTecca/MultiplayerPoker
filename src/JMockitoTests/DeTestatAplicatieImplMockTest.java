package JMockitoTests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

class DeTestatAplicatieImplMockTest {

    DeTestatAplicatieImpl testDimensiune;
    DeTestatAplicatieImpl testAdresa;
    DeTestatAplicatieImpl testInterfata;
    @Mock
    DeTestatAplicatie testDimensiuneMock;
    @Mock                             //cream mock pentru Dimensiune
    DeTestatAplicatie testAdresaMock; //cream mock pentru Adresa
    @Mock
    DeTestatAplicatie testInterfataMock;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testDimensiune=new DeTestatAplicatieImpl(testDimensiuneMock,testAdresaMock,testInterfataMock);
        testAdresa=new DeTestatAplicatieImpl(testDimensiuneMock,testAdresaMock,testInterfataMock);
        testInterfata=new DeTestatAplicatieImpl(testDimensiuneMock,testAdresaMock,testInterfataMock);
    }
    @Test
    void verifDimensiuneEcran() {
        Dimension Dim=new Dimension(); Dim.height=864; Dim.width=1536;
        when(testDimensiuneMock.intoarceDimensiuneEcran("Admin")).thenReturn(Dim);
        String verif=testDimensiune.VerifDimensiuneEcran("Admin");
        assertEquals("Dimensiune corecta",verif);
    }
    @Test
    void verifAdresaCurenta(){
        InetAddress Adresa=null;
        String interfata_cautata="wireless_32768";
        try{
            Adresa=InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        when(testAdresaMock.intoarceAdresaCurenta("Admin")).thenReturn(Adresa);
        when(testInterfataMock.intoarceInterfataCurenta("Admin")).thenReturn(interfata_cautata);
        String verif=testAdresa.VerifInterfataAdresaIpv4("Admin");
        assertEquals("Adresa IP corecta si interfata buna",verif);
    }
}