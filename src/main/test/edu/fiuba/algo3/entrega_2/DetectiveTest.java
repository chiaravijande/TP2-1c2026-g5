package edu.fiuba.algo3.entrega_2;

import roles.ciudadanos.Detective;
import roles.ciudadanos.Ciudadano;
import roles.mafia.Mafioso;
import jugadores.Jugador;
import nocturno.RegistroNocturno;
import nocturno.ResultadoInvestigacion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DetectiveTest {

    //punto 1.1:investigar a un Mafioso común
    @Test
    public void test01DetectiveInvestigaMafiosoYRecibeFalso() {
        Detective detective = new Detective();
        Jugador objetivoMafioso = mock(Jugador.class);
        RegistroNocturno contexto = mock(RegistroNocturno.class);

        when(objetivoMafioso.getRol()).thenReturn(new Mafioso());
        detective.setObjetivo(objetivoMafioso);

        detective.ejecutarAccionNocturna(contexto);

        //guardamos la investigacion que el detectivo quizo realizar.
        ArgumentCaptor<ResultadoInvestigacion> captor = ArgumentCaptor.forClass(ResultadoInvestigacion.class);
        verify(contexto).registrarInvestigacion(captor.capture());

        ResultadoInvestigacion resultado = captor.getValue();
        //como es un mafioso, pareceInocente() debe ser false
        assertFalse(resultado.pareceInocente(), "El Mafioso no debería parecer inocente");
    }

    //punto 1.2:investigar a un Ciudadano común
    @Test
    public void test02DetectiveInvestigaCiudadanoYRecibeVerdadero() {
        Detective detective = new Detective();
        Jugador objetivoCiudadano = mock(Jugador.class);
        RegistroNocturno contexto = mock(RegistroNocturno.class);

        when(objetivoCiudadano.getRol()).thenReturn(new Ciudadano());
        detective.setObjetivo(objetivoCiudadano);

        detective.ejecutarAccionNocturna(contexto);

        ArgumentCaptor<ResultadoInvestigacion> captor = ArgumentCaptor.forClass(ResultadoInvestigacion.class);
        verify(contexto).registrarInvestigacion(captor.capture());

        ResultadoInvestigacion resultado = captor.getValue();
        //como es ciudadano, pareceInocente() debe ser true
        assertTrue(resultado.pareceInocente(), "El Ciudadano debería parecer inocente");
    }

    //punto 2:investigar al Padrino ---> ya esta testeado en PartidaIntegracionTest.java

    //punto 3:historial de investigacion
    @Test
    public void test03DetectiveNoPuedeInvestigarAlMismoJugadorDosNochesSeguidas() {
        Detective detective = new Detective();
        Jugador objetivo = mock(Jugador.class);
        RegistroNocturno contextoNoche1 = mock(RegistroNocturno.class);
        RegistroNocturno contextoNoche2 = mock(RegistroNocturno.class);

        when(objetivo.getRol()).thenReturn(new Ciudadano());

        //primer noche:seteamos el objetivo y ejecutamos
        detective.setObjetivo(objetivo);
        detective.ejecutarAccionNocturna(contextoNoche1);

        //verificamos que la primera noche se registró la investigación
        verify(contextoNoche1, times(1)).registrarInvestigacion(any(ResultadoInvestigacion.class));

        //segunda noche:intentamos investigar al MISMO objetivo
        detective.ejecutarAccionNocturna(contextoNoche2);

        //verificamos que la segunda noche se rechazo y NUNCA se registró la investigación
        verify(contextoNoche2, never()).registrarInvestigacion(any(ResultadoInvestigacion.class));
    }
}