package edu.fiuba.algo3.entrega_1;

import jugadores.Jugador;
import org.junit.jupiter.api.Test;
import roles.Rol;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JugadorTest {

    @Test
    public void unJugadorComienzaVivo() {
        Rol rol = mock(Rol.class);

        Jugador jugador = new Jugador("Juan", rol);

        assertTrue(jugador.estaVivo());
    }

    @Test
    public void unJugadorPuedeSerEliminado() {
        Rol rol = mock(Rol.class);

        Jugador jugador = new Jugador("Juan", rol);

        jugador.eliminar();

        assertFalse(jugador.estaVivo());
    }

    @Test
    public void unJugadorMantieneElRolAsignado() {
        Rol rol = mock(Rol.class);
        Jugador jugador = new Jugador("Juan", rol);


        assertEquals(
                rol,
                jugador.getRol()
        );
    }

    @Test
    public void unJugadorPuedeConocerSiSuRolEsSospechoso() {

        Rol rol = mock(Rol.class);

        when(rol.esSospechoso())
                .thenReturn(true);

        Jugador jugador = new Jugador("Juan", rol);

        assertTrue(jugador.esSospechoso());
    }
}