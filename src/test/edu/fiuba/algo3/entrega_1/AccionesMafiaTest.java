package edu.fiuba.algo3.entrega_1;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import org.junit.jupiter.api.Test;
import roles.mafia.Mafioso;

import static org.mockito.Mockito.*;

public class AccionesMafiaTest {

    @Test
    public void test05MafiaSeleccionaVictimaValidaYRegistraAtaque() {

        Jugador mafioso =
                new Jugador(
                        "mafioso",
                        new Mafioso()
                );

        Jugador victima = mock(Jugador.class);

        when(victima.estaVivo())
                .thenReturn(true);

        when(victima.esAliadoDe(mafioso))
                .thenReturn(false);

        mafioso.elegirObjetivo(victima);

        RegistroNocturno contexto =
                mock(RegistroNocturno.class);

        mafioso.ejecutarTurnoNocturno(
                contexto
        );

        verify(contexto)
                .registrarAtaque(any());
    }

    @Test
    public void test06MafiaIntentaAtacarObjetivoMuertoYSeRechaza() {

        Jugador mafioso =
                new Jugador(
                        "mafioso",
                        new Mafioso()
                );

        Jugador victima = mock(Jugador.class);

        when(victima.estaVivo())
                .thenReturn(false);

        mafioso.elegirObjetivo(victima);

        RegistroNocturno contexto =
                mock(RegistroNocturno.class);

        mafioso.ejecutarTurnoNocturno(
                contexto
        );

        verify(contexto, never())
                .registrarAtaque(any());
    }
}