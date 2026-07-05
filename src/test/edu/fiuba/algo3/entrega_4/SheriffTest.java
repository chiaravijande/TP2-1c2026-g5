package edu.fiuba.algo3.entrega_4;

import jugadores.Jugador;
import nocturno.AccionNocturna;
import nocturno.RegistroNocturno;
import nocturno.ResultadoInvestigacion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import roles.ciudadanos.Detective;
import roles.ciudadanos.Sheriff;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SheriffTest {


    @Test
    public void sheriffSoloPuedeRevelarUnaVez() {

        Sheriff sheriff = new Sheriff();

        Jugador actor = mock(Jugador.class);
        Jugador objetivo = mock(Jugador.class);

        when(objetivo.esSospechoso()).thenReturn(true);

        sheriff.prepararAccion(actor, Optional.of(objetivo));

        sheriff.revelarInvestigacion();

        Optional<ResultadoInvestigacion> segunda =
                sheriff.revelarInvestigacion();

        assertTrue(segunda.isEmpty());
    }

    @Test
    public void sheriffPuedeRevelarSoloSiNoLoHizoAntes() {

        Sheriff sheriff = new Sheriff();

        Jugador actor = mock(Jugador.class);
        Jugador objetivo = mock(Jugador.class);

        when(objetivo.esSospechoso()).thenReturn(false);

        sheriff.prepararAccion(actor, Optional.of(objetivo));

        assertTrue(sheriff.puedeRevelarInvestigacion());

        sheriff.revelarInvestigacion();

        assertFalse(sheriff.puedeRevelarInvestigacion());
    }

    @Test
    public void sheriffNoPuedeRevelarSiNuncaInvestigo() {

        Sheriff sheriff = new Sheriff();

        assertFalse(sheriff.puedeRevelarInvestigacion());

        assertTrue(
                sheriff.revelarInvestigacion().isEmpty()
        );
    }

    @Test
    public void sheriffNoMuestraSuRolMientrasNoReveleLaInvestigacion() {

        Sheriff sheriff = new Sheriff();

        Jugador sheriffJugador =
                new Jugador("Sheriff", sheriff);

        Jugador mafioso = mock(Jugador.class);

        when(mafioso.esSospechoso()).thenReturn(true);

        sheriff.prepararAccion(
                sheriffJugador,
                Optional.of(mafioso)
        );

        assertTrue(
                sheriffJugador.obtenerRolRevelado().isEmpty()
        );
    }


}