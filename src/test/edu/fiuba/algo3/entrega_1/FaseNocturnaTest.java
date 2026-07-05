package edu.fiuba.algo3.entrega_1;

import partida.*;
import fases.*;
import nocturno.*;
import jugadores.Jugador;
import roles.Rol;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.mockito.Mockito.*;

public class FaseNocturnaTest {

    @Test
    public void test07MedicoProtegeAlObjetivoYJugadorSigueVivo() {
        FaseNocturna fase = new FaseNocturna();
        Partida partidaFalsa = mock(Partida.class);
        Jugador victimaFalsa = mock(Jugador.class);

        Jugador atacanteImpostor = new Jugador("MafiosoFalso", mock(Rol.class)) {

            @Override
            public void ejecutarTurnoNocturno(RegistroNocturno contexto) {
                AtaqueNocturno ataqueFrenado = mock(AtaqueNocturno.class);

                //le decimos que devuelva 'false' (el medico lo salvo)
                when(ataqueFrenado.resolverCon(any())).thenReturn(false);

                contexto.registrarAtaque(ataqueFrenado);
            }
        };

        when(partidaFalsa.getJugadores()).thenReturn(List.of(atacanteImpostor, victimaFalsa));

        fase.ejecutar(partidaFalsa);

        //comprobamos que NUNCA se llamo al metodo eliminarJugador() ya que no hubo victimas.
        verify(partidaFalsa, never()).eliminarJugador(any());
    }

    @Test
    public void test08MafiaEligeNoProtegidoYJugadorPasaAEstadoMuerto() {
        FaseNocturna fase = new FaseNocturna();
        Partida partidaFalsa = mock(Partida.class);
        Jugador victimaFalsa = mock(Jugador.class);

        Jugador atacanteImpostor = new Jugador("MafiosoFalso", mock(Rol.class)) {

            @Override
            public void ejecutarTurnoNocturno(RegistroNocturno contexto) {
                AtaqueNocturno ataqueExitoso = mock(AtaqueNocturno.class);

                //le decimos que simule que el ataque funciono (true)
                when(ataqueExitoso.resolverCon(any())).thenReturn(true);

                //le pasamos la víctima falsa para que la fase sepa a quién eliminar
                when(ataqueExitoso.getVictima()).thenReturn(victimaFalsa);

                contexto.registrarAtaque(ataqueExitoso);
            }
        };

        when(partidaFalsa.getJugadores()).thenReturn(List.of(atacanteImpostor, victimaFalsa));

        fase.ejecutar(partidaFalsa);

        //comprobamos que la Fase le ordeno a la Partida eliminar EXACTAMENTE a esa víctima
        verify(partidaFalsa, times(1)).eliminarJugador(victimaFalsa);
    }
}