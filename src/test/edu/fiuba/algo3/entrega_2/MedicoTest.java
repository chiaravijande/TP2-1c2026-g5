package edu.fiuba.algo3.entrega_2;

import jugadores.Jugador;
import nocturno.AccionNocturna;
import nocturno.ProteccionNocturna;
import nocturno.RegistroNocturno;
import org.junit.jupiter.api.Test;
import roles.ciudadanos.Medico;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MedicoTest {

    @Test
    public void test01MedicoProtegeAUnJugadorVivoExitosamente() {

        Jugador medico = new Jugador("medico", new Medico());

        Jugador objetivo = mock(Jugador.class);

        when(objetivo.estaVivo())
                .thenReturn(true);

        medico.elegirObjetivo(objetivo);

        RegistroNocturno contexto =
                mock(RegistroNocturno.class);

        medico.ejecutarTurnoNocturno(contexto);

        verify(contexto)
                .registrarProteccion(any());
    }

    @Test
    public void test02MedicoNoPuedeProtegerAUnJugadorMuerto() {

        Jugador medico = new Jugador("medico", new Medico());

        Jugador objetivo = mock(Jugador.class);

        when(objetivo.estaVivo())
                .thenReturn(false);

        medico.elegirObjetivo(objetivo);

        RegistroNocturno contexto =
                mock(RegistroNocturno.class);

        medico.ejecutarTurnoNocturno(contexto);

        verify(contexto, never())
                .registrarProteccion(any());
    }

    @Test
    public void test03MedicoNoPuedeProtegerAlMismoJugadorDosNochesSeguidas() {

        Jugador medico = new Jugador("medico", new Medico());

        Jugador objetivo = mock(Jugador.class);

        when(objetivo.estaVivo())
                .thenReturn(true);

        medico.elegirObjetivo(objetivo);

        RegistroNocturno contexto1 =
                mock(RegistroNocturno.class);

        medico.ejecutarTurnoNocturno(contexto1);

        verify(contexto1)
                .registrarProteccion(any());

        medico.elegirObjetivo(objetivo);

        RegistroNocturno contexto2 =
                mock(RegistroNocturno.class);

        medico.ejecutarTurnoNocturno(contexto2);

        verify(contexto2, never())
                .registrarProteccion(any());
    }
}