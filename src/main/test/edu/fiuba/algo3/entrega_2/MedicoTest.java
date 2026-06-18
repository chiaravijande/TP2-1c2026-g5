package edu.fiuba.algo3.entrega_2;

import roles.ciudadanos.Medico;
import jugadores.Jugador;
import nocturno.RegistroNocturno;
import nocturno.ProteccionNocturna;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class MedicoTest {

    @Test
    public void test01MedicoProtegeAUnJugadorVivoExitosamente() {
        Medico medico = new Medico();
        Jugador objetivo = mock(Jugador.class);
        RegistroNocturno contexto = mock(RegistroNocturno.class);

        when(objetivo.estaVivo()).thenReturn(true);
        medico.setObjetivo(objetivo);

        medico.ejecutarAccionNocturna(contexto);

        //verificamos que se registro la protección
        verify(contexto, times(1)).registrarProteccion(any(ProteccionNocturna.class));
    }

    @Test
    public void test02MedicoNoPuedeProtegerAUnJugadorMuerto() {
        Medico medico = new Medico();
        Jugador objetivo = mock(Jugador.class);
        RegistroNocturno contexto = mock(RegistroNocturno.class);

        //simulamos que el objetivo está muerto
        when(objetivo.estaVivo()).thenReturn(false);
        medico.setObjetivo(objetivo);

        medico.ejecutarAccionNocturna(contexto);

        //verificamos que NO se registró la protección
        verify(contexto, never()).registrarProteccion(any(ProteccionNocturna.class));
    }

    //historial de protección
    @Test
    public void test03MedicoNoPuedeProtegerAlMismoJugadorDosNochesSeguidas() {
        Medico medico = new Medico();
        Jugador objetivo = mock(Jugador.class);
        RegistroNocturno contextoNoche1 = mock(RegistroNocturno.class);
        RegistroNocturno contextoNoche2 = mock(RegistroNocturno.class);

        when(objetivo.estaVivo()).thenReturn(true);

        //primer noche:seteamos el objetivo y ejecutamos
        medico.setObjetivo(objetivo);
        medico.ejecutarAccionNocturna(contextoNoche1);

        //verificamos que la primera noche si se registro proteccion
        verify(contextoNoche1, times(1)).registrarProteccion(any(ProteccionNocturna.class));

        //segunda noche:intentamos proteger al MISMO objetivo
        medico.ejecutarAccionNocturna(contextoNoche2);

        //verifica que la segunda noche se rechazo y NUNCA se registró la protección
        verify(contextoNoche2, never()).registrarProteccion(any(ProteccionNocturna.class));
    }
}