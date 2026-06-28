package edu.fiuba.algo3.entrega_3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import jugadores.Jugador;
import partida.*;
import fases.FaseNocturna;
import roles.mafia.Mafioso;
import roles.mafia.Padrino;
import roles.ciudadanos.Ciudadano;
import roles.ciudadanos.Detective;
import roles.ciudadanos.Medico;

import java.util.List;

public class VictoriaIntegracionTest {

    @Test
    public void test01CiudadanosGananAlEliminarTodosLosMafiosos() {
        // Arrange
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act - se elimina al único mafioso
        partida.eliminarJugador(mafioso);
        ResultadoPartida resultado = partida.verificarVictoria();

        // Assert
        assertTrue(resultado.esTerminal());
    }

    @Test
    public void test02CiudadanosGananAlEliminarVariosMafiosos() {
        // Arrange
        Jugador mafioso1 = new Jugador("Mafioso1", new Mafioso());
        Jugador mafioso2 = new Jugador("Mafioso2", new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());
        Jugador ciudadano3 = new Jugador("Ciudadano3", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso1, mafioso2, ciudadano1, ciudadano2, ciudadano3),
                new FaseNocturna()
        );

        // Act - se eliminan ambos mafiosos
        partida.eliminarJugador(mafioso1);
        partida.eliminarJugador(mafioso2);
        ResultadoPartida resultado = partida.verificarVictoria();

        // Assert
        assertTrue(resultado.esTerminal());
    }

    @Test
    public void test03PadrinoEliminadoCuentaComoMafiosoParaVictoria() {
        // Arrange - el Padrino cuenta como mafioso en el ContadorDeBandos,
        // aunque parezca inocente ante el Detective
        Jugador padrino = new Jugador("Padrino", new Padrino());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        Partida partida = new Partida(
                List.of(padrino, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act
        partida.eliminarJugador(padrino);
        ResultadoPartida resultado = partida.verificarVictoria();

        // Assert
        assertTrue(resultado.esTerminal());
    }

    @Test
    public void test04MafiaGanaCuandoQuedaUnMafiosoYUnCiudadano() {
        // Arrange - igualdad 1 a 1
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano),
                new FaseNocturna()
        );

        // Act
        ResultadoPartida resultado = partida.verificarVictoria();

        // Assert
        assertTrue(resultado.esTerminal());
    }

    @Test
    public void test05MafiaGanaCuandoIgualanACiudadanos() {
        // Arrange - 2 mafiosos, 2 ciudadanos (igualdad = gana la mafia)
        Jugador mafioso1 = new Jugador("Mafioso1", new Mafioso());
        Jugador mafioso2 = new Jugador("Mafioso2", new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso1, mafioso2, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act — se eliminan dos ciudadanos hasta igualar
        ResultadoPartida resultado = partida.verificarVictoria();

        // Assert
        assertTrue(resultado.esTerminal());
    }

    @Test
    public void test06MafiaGanaCuandoSuperanACiudadanos() {
        // Arrange - 2 mafiosos, 1 ciudadano
        Jugador mafioso1 = new Jugador("Mafioso1", new Mafioso());
        Jugador mafioso2 = new Jugador("Mafioso2", new Mafioso());
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso1, mafioso2, ciudadano),
                new FaseNocturna()
        );

        // Act - no se elimina nadie, la mafia ya supera desde el inicio
        ResultadoPartida resultado = partida.verificarVictoria();

        // Assert
        assertTrue(resultado.esTerminal());
    }

    @Test
    public void test07PartidaEnCursoSiMafiosaEsMinoria() {
        // Arrange - 1 mafioso, 3 ciudadanos
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());
        Jugador ciudadano3 = new Jugador("Ciudadano3", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano1, ciudadano2, ciudadano3),
                new FaseNocturna()
        );

        // Act
        ResultadoPartida resultado = partida.verificarVictoria();

        // Assert
        assertFalse(resultado.esTerminal());
    }

    @Test
    public void test08JugadoresEliminadosNoInfluyenEnLaCondicionDeVictoria() {
        // Arrange - empezamos con 2 mafiosos y 4 ciudadanos (partida en curso)
        // luego se elimina un ciudadano, quedando 2 mafiosos y 3 ciudadanos =  partida en curso
        Jugador mafioso1 = new Jugador("Mafioso1", new Mafioso());
        Jugador mafioso2 = new Jugador("Mafioso2", new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());
        Jugador ciudadano3 = new Jugador("Ciudadano3", new Ciudadano());
        Jugador ciudadano4 = new Jugador("Ciudadano4", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso1, mafioso2, ciudadano1, ciudadano2, ciudadano3, ciudadano4),
                new FaseNocturna()
        );

        // Act
        partida.eliminarJugador(ciudadano1);
        ResultadoPartida resultado = partida.verificarVictoria();

        // Assert - el eliminado no cuenta, quedan 2 vs 3 = partida en curso
        assertFalse(resultado.esTerminal());
    }
}
