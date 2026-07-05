package edu.fiuba.algo3.entrega_1;

import nocturno.*;

import nocturno.*;
import jugadores.Jugador;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistroNocturnoTest {

    @Test
    public void test01NoHayAtaquesNadieMuere() {

        RegistroNocturno contexto = new RegistroNocturno();
        // (no registramos ningun ataque)

        ResultadoNocturno resultado = contexto.generarResultado();
        Optional<Jugador> posibleVictima = resultado.obtenerVictima();

        assertFalse(posibleVictima.isPresent(), "El Optional deberia estar vacio porque nadie ataco");
    }

    @Test
    public void test02HayUnAtaqueExitosoDevuelveALaVictimaCorrecta() {
        RegistroNocturno contexto = new RegistroNocturno();
        AtaqueNocturno ataqueFalso = mock(AtaqueNocturno.class);
        Jugador victimaFalsa = mock(Jugador.class);

        //lLe decimos que el ataque fue exitoso devolviendo 'true'
        when(ataqueFalso.resolverCon(any())).thenReturn(true);

        //el contexto necesita preguntarle al ataque quien era la victima
        when(ataqueFalso.getVictima()).thenReturn(victimaFalsa);

        contexto.registrarAtaque(ataqueFalso);

        ResultadoNocturno resultado = contexto.generarResultado();
        Optional<Jugador> posibleVictima = resultado.obtenerVictima();

        assertTrue(posibleVictima.isPresent(), "El Optional deberia tener un jugador adentro");
        assertEquals(victimaFalsa, posibleVictima.get(), "La victima deberia ser correcta");
    }
}
