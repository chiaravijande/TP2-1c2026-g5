package edu.fiuba.algo3.entrega_1;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import nocturno.ResultadoNocturno;
import votacion.ResultadoVotacion;
import votacion.VotacionMafia;
import org.junit.jupiter.api.Test;
import roles.mafia.Mafioso;
import roles.mafia.Padrino;
import partida.Partida;
import roles.Rol;
import fases.FaseNocturna;
import mazo.ConfiguracionMazo;
import mazo.ConfiguracionMazoDe10a12;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccionesMafiaTest {

    @Test
    public void test05MafiaVotaAObjetivoValido() {

        Jugador mafioso =
                new Jugador("mafioso", new Mafioso());

        Jugador victima = mock(Jugador.class);
        when(victima.estaVivo()).thenReturn(true);

        mafioso.elegirObjetivo(victima);

        RegistroNocturno contexto = mock(RegistroNocturno.class);

        mafioso.ejecutarTurnoNocturno(contexto);

        verify(contexto).registrarVotoMafia(mafioso, victima);
        verify(contexto, never()).registrarAtaque(any());
    }

    @Test
    public void test06LaNocheResuelveElVotoDeLaMafiaEnUnAtaque() {

        Jugador mafioso =
                new Jugador("mafioso", new Mafioso());

        Jugador victima = mock(Jugador.class);
        when(victima.estaVivo()).thenReturn(true);


        mafioso.elegirObjetivo(victima);

        RegistroNocturno registro = new RegistroNocturno();

        mafioso.ejecutarTurnoNocturno(registro);
        registro.resolverAtaqueMafia();

        ResultadoNocturno resultado = registro.generarResultado();

        assertTrue(resultado.huboVictima());
        assertEquals(victima, resultado.obtenerVictima().get());
    }

    @Test
    public void test07MafiaIntentaAtacarObjetivoMuertoYSeRechaza() {

        Jugador mafioso =
                new Jugador("mafioso", new Mafioso());

        Jugador victima = mock(Jugador.class);
        when(victima.estaVivo()).thenReturn(false);

        mafioso.elegirObjetivo(victima);

        RegistroNocturno registro = new RegistroNocturno();

        mafioso.ejecutarTurnoNocturno(registro);
        registro.resolverAtaqueMafia();

        ResultadoNocturno resultado = registro.generarResultado();

        assertFalse(resultado.huboVictima());
    }

    @Test
    public void test08EnEmpateElVotoDelPadrinoDefineLaVictima() {

        Jugador padrino =
                new Jugador("Padrino", new Padrino());

        Jugador mafioso =
                new Jugador("Mafioso", new Mafioso());

        Jugador victimaA = mock(Jugador.class);
        Jugador victimaB = mock(Jugador.class);

        when(victimaA.estaVivo()).thenReturn(true);
        when(victimaB.estaVivo()).thenReturn(true);

        // El padrino vota por A
        padrino.elegirObjetivo(victimaA);

        // El mafioso vota por B
        mafioso.elegirObjetivo(victimaB);

        RegistroNocturno registro = new RegistroNocturno();

        padrino.ejecutarTurnoNocturno(registro);
        mafioso.ejecutarTurnoNocturno(registro);

        registro.resolverAtaqueMafia();

        ResultadoNocturno resultado = registro.generarResultado();

        assertTrue(resultado.huboVictima());

        assertEquals(
                victimaA,
                resultado.obtenerVictima().get()
        );
    }

    @Test
    public void test09SiNoHayEmpateGanaLaMayorCantidadDeVotosAunqueElPadrinoVoteOtraCosa() {

        Jugador padrino =
                new Jugador("Padrino", new Padrino());

        Jugador mafioso1 =
                new Jugador("Mafioso1", new Mafioso());

        Jugador mafioso2 =
                new Jugador("Mafioso2", new Mafioso());

        Jugador victimaA = mock(Jugador.class);
        Jugador victimaB = mock(Jugador.class);

        when(victimaA.estaVivo()).thenReturn(true);
        when(victimaB.estaVivo()).thenReturn(true);

        padrino.elegirObjetivo(victimaA);

        mafioso1.elegirObjetivo(victimaB);
        mafioso2.elegirObjetivo(victimaB);

        RegistroNocturno registro = new RegistroNocturno();

        padrino.ejecutarTurnoNocturno(registro);
        mafioso1.ejecutarTurnoNocturno(registro);
        mafioso2.ejecutarTurnoNocturno(registro);

        registro.resolverAtaqueMafia();

        ResultadoNocturno resultado = registro.generarResultado();

        assertTrue(resultado.huboVictima());

        assertEquals(
                victimaB,
                resultado.obtenerVictima().get()
        );
    }

    @Test
    public void test08EnEmpateLaVotacionMafiaDevuelveElObjetivoDelPadrino() {

        Jugador padrino = new Jugador("Padrino", new Padrino());
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());

        Jugador victima1 = mock(Jugador.class);
        when(victima1.estaVivo()).thenReturn(true);

        Jugador victima2 = mock(Jugador.class);
        when(victima2.estaVivo()).thenReturn(true);

        VotacionMafia votacion = new VotacionMafia();

        votacion.registrarVoto(padrino, victima1);
        votacion.registrarVoto(mafioso, victima2);

        ResultadoVotacion resultado = votacion.calcularResultado();

        assertTrue(resultado.obtenerExpulsado().isPresent());
        assertEquals(victima1, resultado.obtenerExpulsado().get());
    }

    @Test
    public void test09RegistroNocturnoRespetaElDesempateDelPadrino() {

        Jugador padrino = new Jugador("Padrino", new Padrino());
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());

        Jugador victima1 = mock(Jugador.class);
        when(victima1.estaVivo()).thenReturn(true);

        Jugador victima2 = mock(Jugador.class);
        when(victima2.estaVivo()).thenReturn(true);

        RegistroNocturno registro = new RegistroNocturno();

        padrino.elegirObjetivo(victima1);
        mafioso.elegirObjetivo(victima2);

        padrino.ejecutarTurnoNocturno(registro);
        mafioso.ejecutarTurnoNocturno(registro);

        registro.resolverAtaqueMafia();

        ResultadoNocturno resultado = registro.generarResultado();

        assertTrue(resultado.huboVictima());
        assertEquals(victima1, resultado.obtenerVictima().get());
    }

    @Test
    public void test10FaseNocturnaUsaElDesempateDelPadrino() {

        Jugador padrino = new Jugador("Padrino", new Padrino());
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());

        Jugador victima1 = new Jugador("Victima1", mock(Rol.class));
        Jugador victima2 = new Jugador("Victima2", mock(Rol.class));

        padrino.elegirObjetivo(victima1);
        mafioso.elegirObjetivo(victima2);

        Partida partida = new Partida(
                List.of(padrino, mafioso, victima1, victima2),
                new FaseNocturna());

        partida.avanzar();

        assertFalse(victima1.estaVivo());
        assertTrue(victima2.estaVivo());
    }

    @Test
    public void test11FlujoRealDeLaInterfazConEmpateDeMafia() {

        Jugador padrino = new Jugador("Padrino", new Padrino());
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());

        Jugador victima1 = mock(Jugador.class);
        when(victima1.estaVivo()).thenReturn(true);

        Jugador victima2 = mock(Jugador.class);
        when(victima2.estaVivo()).thenReturn(true);

        padrino.elegirObjetivo(victima1);
        mafioso.elegirObjetivo(victima2);

        RegistroNocturno registro = new RegistroNocturno();

        padrino.ejecutarTurnoNocturno(registro);
        mafioso.ejecutarTurnoNocturno(registro);

        registro.resolverAtaqueMafia();

        ResultadoNocturno resultado = registro.generarResultado();

        assertTrue(resultado.huboVictima());
        assertEquals(victima1, resultado.obtenerVictima().get());
    }

    @Test
    public void test12ElPadrinoTienePrioridadDeDesempate() {

        Jugador padrino = new Jugador("Padrino", new Padrino());

        assertTrue(padrino.tienePrioridadDeDesempate());
    }

    @Test
    public void testElMazoDe10JugadoresTieneTresMiembrosDeLaMafia() {

        ConfiguracionMazo config = new ConfiguracionMazoDe10a12();

        List<Rol> roles = config.generarRoles(10);

        long mafiosos =
                roles.stream()
                        .filter(Rol::esMafia)
                        .count();

        assertEquals(3, mafiosos);
    }

}