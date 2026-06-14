package edu.fiuba.algo3.entrega_1;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import org.junit.jupiter.api.Test;
import roles.Rol;
import roles.ciudadanos.Medico;
import roles.RolNocturno;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JugadorTest {

    @Test
    public void unJugadorComienzaVivo() {
        Jugador jugador = new Jugador("Juan");

        assertTrue(jugador.estaVivo(), "El jugador debería nacer en EstadoVivo");
    }

    @Test
    public void unJugadorPuedeSerEliminado() {
        Jugador jugador = new Jugador("Juan");

        //al eliminarlo internamente cambia a EstadoMuerto
        jugador.eliminar();

        assertFalse(jugador.estaVivo(), "El jugador ya no debería estar vivo tras ser eliminado");
    }

    @Test
    public void unJugadorMantieneElRolAsignado() {
        //verificamos que el jugador guarde correctamente el rol que se le reparte
        Rol rolMock = mock(Rol.class);
        Jugador jugador = new Jugador("Juan");

        jugador.setRol(rolMock);

        assertEquals(rolMock, jugador.getRol(), "El jugador debería poder ver/acceder al rol que se le asignó");
    }

    @Test
    public void unJugadorConRolNocturnoPuedeEjecutarSuAccion() {

        // Mockeamos directamente un rol concreto que actúa de noche (ej: Medico).
        // Al ser Medico, Java ya sabe que es un "Rol" y también un "RolNocturno".
        Medico medicoMock = mock(Medico.class);

        Jugador jugador = new Jugador("Juan");
        jugador.setRol(medicoMock);

        RegistroNocturno contextoMock = mock(RegistroNocturno.class);

        // El jugador (que está vivo por defecto) ejecuta su turno
        jugador.ejecutarTurnoNocturno(contextoMock);

        // Verificamos que el jugador le haya pasado la pelota a su rol correctamente
        verify(medicoMock, times(1)).ejecutarAccionNocturna(contextoMock);
    }
}