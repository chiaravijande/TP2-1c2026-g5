package edu.fiuba.algo3.entrega_2;

import jugadores.Jugador;
import nocturno.AccionNocturna;
import nocturno.RegistroNocturno;
import nocturno.ResultadoInvestigacion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import roles.ciudadanos.Detective;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DetectiveTest {

    @Test
    public void test01DetectiveInvestigaMafiosoYRecibeFalso() {

        Jugador detective = new Jugador("detective", new Detective());

        Jugador mafioso = mock(Jugador.class);

        when(mafioso.esSospechoso())
                .thenReturn(true);

        detective.elegirObjetivo(mafioso);

        RegistroNocturno contexto =
                mock(RegistroNocturno.class);

        detective.ejecutarTurnoNocturno(contexto);

        ArgumentCaptor<ResultadoInvestigacion> captor =
                ArgumentCaptor.forClass(
                        ResultadoInvestigacion.class
                );

        verify(contexto)
                .registrarInvestigacion(
                        captor.capture()
                );

        assertFalse(
                captor.getValue()
                        .pareceInocente()
        );
    }

    @Test
    public void test02DetectiveInvestigaCiudadanoYRecibeVerdadero() {

        Jugador detective = new Jugador("detective", new Detective());

        Jugador ciudadano = mock(Jugador.class);

        when(ciudadano.esSospechoso())
                .thenReturn(false);

        detective.elegirObjetivo(ciudadano);

        RegistroNocturno contexto =
                mock(RegistroNocturno.class);

        detective.ejecutarTurnoNocturno(contexto);

        ArgumentCaptor<ResultadoInvestigacion> captor =
                ArgumentCaptor.forClass(
                        ResultadoInvestigacion.class
                );

        verify(contexto)
                .registrarInvestigacion(
                        captor.capture()
                );

        assertTrue(
                captor.getValue()
                        .pareceInocente()
        );
    }

    @Test
    public void test03DetectiveNoPuedeInvestigarAlMismoJugadorDosNochesSeguidas() {

        Jugador detective = new Jugador("detective", new Detective());

        Jugador objetivo = mock(Jugador.class);

        when(objetivo.esSospechoso())
                .thenReturn(false);

        detective.elegirObjetivo(objetivo);

        RegistroNocturno contexto1 =
                mock(RegistroNocturno.class);

        detective.ejecutarTurnoNocturno(contexto1);

        verify(contexto1)
                .registrarInvestigacion(any());

        detective.elegirObjetivo(objetivo);

        RegistroNocturno contexto2 =
                mock(RegistroNocturno.class);

        detective.ejecutarTurnoNocturno(contexto2);

        verify(contexto2, never())
                .registrarInvestigacion(any());
    }
}