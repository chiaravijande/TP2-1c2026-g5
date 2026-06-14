package edu.fiuba.algo3.entrega_1;

import static org.junit.jupiter.api.Assertions.*;

import jugadores.Jugador;
import partida.*;
import fases.*;
import roles.mafia.Mafioso;
import roles.mafia.Padrino;
import roles.ciudadanos.*;
import votacion.*;
import mazo.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class PartidaIntegracionTest {
    @Test
    public void testSimulacionPartidaCortaConNocheYDia() {
        // Creamos los 6 jugadores requeridos
        Jugador juan = new Jugador("Juan - El Padrino");
        Jugador maria = new Jugador("María - La Mafiosa 1");
        Jugador matias = new Jugador("Matías - El Mafioso 2");
        Jugador ana = new Jugador("Ana - La Detective");
        Jugador pedro = new Jugador("Pedro - El Medico");
        Jugador lucas = new Jugador("Lucas - Ciudadano");

        List<Jugador> listaJugadores = new ArrayList<>(List.of(juan, maria, matias, ana, pedro, lucas));

        // Instanciamos los roles correspondientes
        Padrino rolPadrino = new Padrino();
        Mafioso rolMafioso1 = new Mafioso();
        Mafioso rolMafioso2 = new Mafioso();
        Detective rolDetective = new Detective();
        Medico rolMedico = new Medico();
        Ciudadano rolCiudadano = new Ciudadano();

        // Asignamos manualmente
        juan.setRol(rolPadrino);
        maria.setRol(rolMafioso1);
        matias.setRol(rolMafioso2);
        ana.setRol(rolDetective);
        pedro.setRol(rolMedico);
        lucas.setRol(rolCiudadano);

        // Creamos la partida iniciando en la Fase Nocturna
        Mazo mazo = new Mazo(ConfiguracionMazo.para(6));
        Partida partida = new Partida(listaJugadores, mazo, new FaseNocturna());

        // Verificar condiciones iniciales: todos arrancan vivos
        assertTrue(juan.estaVivo());
        assertTrue(lucas.estaVivo());
        assertTrue(maria.estaVivo());
        assertTrue(ana.estaVivo());
        assertTrue(pedro.estaVivo());
        assertTrue(matias.estaVivo());

        // Fase nocturna: Dos mafiosos intentan atacar a un Mafioso

        // María y Matías intentan seleccionar a Juan (Padrino) como objetivo.
        rolMafioso1.setObjetivo(juan);
        rolMafioso2.setObjetivo(juan);

        // El Padrino actua de forma legal apuntando a un ciudadano (Lucas)
        rolPadrino.setObjetivo(lucas);

        // El Medico (Pedro) protege a Lucas
        rolMedico.setObjetivo(lucas);

        // La detective investiga a Juan
        rolDetective.setObjetivo(juan);

        // Avanzamos la partida
        partida.avanzar();

        // verificacion de la noche:
        // el ataque de Maria y Matías nop funciono
        assertTrue(juan.estaVivo(), "El ataque de los mafiosos a Juan debio rechazarse por ser del mismo bando");

        // Lucas tambien sobrevive porque el Medico lo protegio
        assertTrue(lucas.estaVivo(), "Lucas debio salvarse del ataque porque el Médico lo protegio");

        // Validamos que el Detective recibio que Juan es 'Ciudadano'
        assertFalse(juan.getRol().esSospechoso(), "El Padrino debe reportar falso para sospechoso ante el Detective");

        // Fase diurna: Votacion del debate del pueblo
        Votacion votacionDia1 = new Votacion();

        // Los dos mafiosos (María y Matías) votan a Juan de día [Juan: 2 votos]
        votacionDia1.registrarVoto(maria, juan);
        votacionDia1.registrarVoto(matias, juan);

        // El Padrino (Juan) vota a Lucas [Lucas: 1 voto]
        votacionDia1.registrarVoto(juan, lucas);

        // Los tres ciudadanos concentran sus votos en María [María: 3 votos]
        votacionDia1.registrarVoto(ana, maria);
        votacionDia1.registrarVoto(pedro, maria);
        votacionDia1.registrarVoto(lucas, maria);

        ResultadoVotacion resultadoDia1 = votacionDia1.calcularResultado();
        Optional<Jugador> expulsadoDia1 = resultadoDia1.obtenerExpulsado();

        // Verificacion del día 1:
        assertTrue(expulsadoDia1.isPresent());
        assertEquals(maria, expulsadoDia1.get(), "La expulsada debe ser Maria por tener la mayoria de votos");

        // Se ejecuta la eliminacion de María
        partida.eliminarJugador(expulsadoDia1.get());
        assertFalse(maria.estaVivo(), "Maria debería ser eliminada tras la votacion");
        // Confirmamos nuevamente que Juan sigue vivo
        assertTrue(juan.estaVivo(), "Juan no debio morir en el debate porque no fue el mas votado");
    }
}
