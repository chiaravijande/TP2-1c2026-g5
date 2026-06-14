package edu.fiuba.algo3.entrega_1;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import partida.*;
import fases.*;
import nocturno.*;
import jugadores.*;
import roles.mafia.Mafioso;

public class AccionesMafiaTest {

    // TEST 5: Selección de víctima válida
    @Test
    public void test05MafiaSeleccionaVictimaValidaYRegistraAtaque() {
        //preparación
        Mafioso rolMafioso = new Mafioso();
        Jugador victimaValida = mock(Jugador.class);
        RegistroNocturno contextoMock = mock(RegistroNocturno.class);

        //simulamos que la víctima cumple las condiciones (está viva)
        when(victimaValida.estaVivo()).thenReturn(true);

        rolMafioso.setObjetivo(victimaValida);

        //ejecución
        rolMafioso.ejecutarAccionNocturna(contextoMock);

        //verifica que al ser un objetivo valido, el mafioso genero y registro el ataque
        verify(contextoMock, times(1)).registrarAtaque(any(AtaqueNocturno.class));
    }

    // TEST 6: Selección de víctima inválida (y demostración del Test 4)
    @Test
    public void test06MafiaIntentaAtacarObjetivoMuertoOCompañeroYSeRechaza() {
        //preparación
        Mafioso rolMafioso = new Mafioso();
        Jugador victimaInvalida = mock(Jugador.class);
        RegistroNocturno contextoMock = mock(RegistroNocturno.class);

        //simula que el jugador objetivo ya esta muerto (condición inválida)
        when(victimaInvalida.estaVivo()).thenReturn(false);

        rolMafioso.setObjetivo(victimaInvalida);

        //ejecución
        rolMafioso.ejecutarAccionNocturna(contextoMock);

        //verifica que como el sistema rechaza la acción, NUNCA se registra un ataque
        verify(contextoMock, never()).registrarAtaque(any(AtaqueNocturno.class));
    }
}