package edu.fiuba.algo3.entrega_2;

import jugadores.Jugador;
import roles.Rol;
import votacion.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FaseDiurnaTest {

    @Test
    public void test01NominacionesSoloIncluyenVivos() {
        Votacion votacion = new Votacion();
        Jugador votante = mock(Jugador.class);
        Jugador votadoMuerto = mock(Jugador.class);

        when(votadoMuerto.estaVivo()).thenReturn(false);

        votacion.registrarVoto(votante, votadoMuerto);
        ResultadoVotacion resultado = votacion.calcularResultado();

        //al ser rechazado el voto, no hay nadie expulsado
        assertTrue(resultado.obtenerExpulsado().isEmpty());
    }

    @Test
    public void test02VotacionEliminaAlMasVotado() {
        Votacion votacion = new Votacion();
        Jugador votante1 = mock(Jugador.class);
        Jugador votante2 = mock(Jugador.class);
        Jugador votadoGanador = mock(Jugador.class);

        when(votadoGanador.estaVivo()).thenReturn(true);

        votacion.registrarVoto(votante1, votadoGanador);
        votacion.registrarVoto(votante2, votadoGanador);

        ResultadoVotacion resultado = votacion.calcularResultado();

        assertTrue(resultado.obtenerExpulsado().isPresent());
        assertEquals(votadoGanador, resultado.obtenerExpulsado().get());
    }

    @Test
    public void test03JugadorMuertoNoPuedeHacerNada() {
        Jugador jugador = new Jugador("Muerto");
        jugador.eliminar(); //pasa a EstadoMuerto

        Votacion votacionMock = mock(Votacion.class);
        jugador.votarEn(votacionMock);

        //verifica que un jugador muerto no registra voto
        verifyNoInteractions(votacionMock);
    }

    @Test
    public void test04LaCartaDelJugadorSeRevelaAlSerEliminado() {
        Jugador jugador = new Jugador("Pedro");
        Rol rolMock = mock(Rol.class);
        jugador.setRol(rolMock);

        //mientras esta vivo, la carta esta oculta
        assertTrue(jugador.obtenerRolRevelado().isEmpty(), "La carta debe estar oculta mientras viva");

        //eliminamos el jugador (puede ser de día por votación o de noche por ataque)
        jugador.eliminar();

        //verificamos que ahora la carta esta expuesta para todos
        assertTrue(jugador.obtenerRolRevelado().isPresent(), "La carta debe estar revelada al morir");
        assertEquals(rolMock, jugador.obtenerRolRevelado().get(), "El rol revelado debe ser el correcto");
    }
}
