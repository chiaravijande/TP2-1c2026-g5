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

        Padrino rolPadrino = new Padrino();
        Mafioso rolMafioso1 = new Mafioso();
        Mafioso rolMafioso2 = new Mafioso();
        Detective rolDetective = new Detective();
        Medico rolMedico = new Medico();
        Ciudadano rolCiudadano = new Ciudadano();

        Jugador juan = new Jugador("Juan - El Padrino", rolPadrino);
        Jugador maria = new Jugador("María - La Mafiosa 1", rolMafioso1);
        Jugador matias = new Jugador("Matías - El Mafioso 2", rolMafioso2);
        Jugador ana = new Jugador("Ana - La Detective", rolDetective);
        Jugador pedro = new Jugador("Pedro - El Médico", rolMedico);
        Jugador lucas = new Jugador("Lucas - Ciudadano", rolCiudadano);

        List<Jugador> jugadores = List.of(
                juan,
                maria,
                matias,
                ana,
                pedro,
                lucas
        );


        Partida partida =
                new Partida(
                        jugadores,
                        new FaseNocturna()
                );

        assertTrue(juan.estaVivo());
        assertTrue(maria.estaVivo());
        assertTrue(matias.estaVivo());
        assertTrue(ana.estaVivo());
        assertTrue(pedro.estaVivo());
        assertTrue(lucas.estaVivo());

        maria.elegirObjetivo(juan);
        matias.elegirObjetivo(juan);

        juan.elegirObjetivo(lucas);

        pedro.elegirObjetivo(lucas);

        ana.elegirObjetivo(juan);

        partida.avanzar();

        assertTrue(
                juan.estaVivo(),
                "El ataque mafioso a Juan debe ser rechazado"
        );

        assertTrue(
                lucas.estaVivo(),
                "Lucas debe sobrevivir por la protección del médico"
        );

        assertFalse(
                juan.esSospechoso(),
                "El Padrino debe parecer inocente"
        );

        Votacion votacion = new Votacion();

        votacion.registrarVoto(maria, juan);
        votacion.registrarVoto(matias, juan);

        votacion.registrarVoto(juan, lucas);

        votacion.registrarVoto(ana, maria);
        votacion.registrarVoto(pedro, maria);
        votacion.registrarVoto(lucas, maria);

        ResultadoVotacion resultado =
                votacion.calcularResultado();

        assertTrue(resultado.obtenerExpulsado().isPresent());

        Jugador expulsado =
                resultado.obtenerExpulsado()
                        .orElseThrow();

        assertEquals(
                maria,
                expulsado,
                "La expulsada debe ser María"
        );

        partida.eliminarJugador(expulsado);

        assertFalse(
                maria.estaVivo(),
                "María debe quedar eliminada"
        );

        assertTrue(
                juan.estaVivo(),
                "Juan no debe morir durante la votación"
        );
    }
}
