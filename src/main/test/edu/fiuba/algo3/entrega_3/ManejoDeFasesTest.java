package edu.fiuba.algo3.entrega_3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import jugadores.Jugador;
import partida.Partida;
import partida.ResultadoPartida;
import fases.*;
import roles.mafia.Mafioso;
import roles.mafia.Padrino;
import roles.ciudadanos.Ciudadano;
import roles.ciudadanos.Detective;
import roles.ciudadanos.Medico;

import java.util.List;

public class ManejoDeFasesTest {

    @Test
    public void test01PartidaIniciaEnFaseNocturna() {
        // Arrange
        Jugador mafioso   = new Jugador("Mafioso",   new Mafioso());
        Jugador ciudadano = new Jugador("Ciudadano",  new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano, ciudadano2),
                new FaseNocturna()
        );

        // Assert - la condición de victoria inicial es PartidaEnCurso
        assertFalse(partida.verificarVictoria().esTerminal());
    }

    @Test
    public void test02DespuesDeFaseNocturnaVieneFaseDiurna() {
        // Arrange
        Jugador mafioso    = new Jugador("Mafioso",    new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act - avanzamos la fase diurna
        partida.avanzar();

        // Assert
        // La partida sigue en curso porque nadie murió
        assertFalse(partida.verificarVictoria().esTerminal());
        assertEquals(2, partida.getRondaActual());
    }

    @Test
    public void test03RondaIncrementaAlIniciarFaseDiurna() {
        // Arrange
        Jugador mafioso    = new Jugador("Mafioso",    new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act
        partida.avanzar();
        partida.avanzar();

        // Assert
        assertEquals(2, partida.getRondaActual());
    }

    @Test
    public void test03DosRondasCompletasIncrementanRondaCorrectamente() {
        // Arrange
        Jugador mafioso    = new Jugador("Mafioso",    new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());
        Jugador ciudadano3 = new Jugador("Ciudadano3", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano1, ciudadano2, ciudadano3),
                new FaseNocturna()
        );

        // Act - dos ciclos completos
        partida.avanzar();
        partida.avanzar();
        partida.avanzar();

        // Assert
        assertEquals(3, partida.getRondaActual());
    }

    @Test
    public void test04FaseFinalNoIncrementaRonda() {
        // Arrange - La mafia gana al comenzar el día
        Jugador mafioso   = new Jugador("Mafioso",   new Mafioso());
        Jugador ciudadano = new Jugador("Ciudadano",  new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano),
                new FaseNocturna()
        );

        // Act - noche (sin víctima) -> día (mafia iguala, condición terminal = FaseFinal)
        partida.avanzar();
        int rondaAntesDeFinal = partida.getRondaActual();

        partida.avanzar(); // FaseDiurna detecta victoria y pasa a FaseFinal
        partida.avanzar(); // FaseFinal
        partida.avanzar(); // sigue en FaseFinal

        // Assert — la ronda no sigue subiendo una vez que la partida terminó
        assertEquals(rondaAntesDeFinal, partida.getRondaActual());
        assertTrue(partida.verificarVictoria().esTerminal());
    }

    @Test
    public void test05MafiosoAtacaYCiudadanoMuereSinProteccion() {
        // Arrange
        Jugador mafioso    = new Jugador("Mafioso",    new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        mafioso.elegirObjetivo(ciudadano1);

        Partida partida = new Partida(
                List.of(mafioso, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act
        partida.avanzar();

        // Assert
        assertFalse(ciudadano1.estaVivo(), "El ciudadano atacado sin protección debe morir");
        assertTrue(ciudadano2.estaVivo());
        assertTrue(mafioso.estaVivo());
    }


    //TestMedicoProtegeCiudadanoAtacado() -> Testeado en PartidaInteracionTest

    @Test
    public void test06MedicoNoProtegeSiEligeDiferenteObjetivo() {
        // Arrange
        Jugador mafioso    = new Jugador("Mafioso",    new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());
        Jugador medico     = new Jugador("Medico",     new Medico());

        mafioso.elegirObjetivo(ciudadano1);
        medico.elegirObjetivo(ciudadano2);  // protege a alguien distinto

        Partida partida = new Partida(
                List.of(mafioso, ciudadano1, ciudadano2, medico),
                new FaseNocturna()
        );

        // Act
        partida.avanzar();

        // Assert
        assertFalse(ciudadano1.estaVivo(), "ciudadano1 fue atacado sin protección, debe morir");
        assertTrue(ciudadano2.estaVivo());
    }

    @Test
    public void test07MafiosoNoAtacaAliado() {
        // Arrange - el mafioso elige como objetivo a otro mafioso (aliado)
        Jugador mafioso1 = new Jugador("Mafioso1", new Mafioso());
        Jugador mafioso2 = new Jugador("Mafioso2", new Mafioso());
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        mafioso1.elegirObjetivo(mafioso2); // ataque inválido: aliado

        Partida partida = new Partida(
                List.of(mafioso1, mafioso2, ciudadano, ciudadano2),
                new FaseNocturna()
        );

        // Act
        partida.avanzar();

        // Assert - el ataque al aliado no se registra, nadie muere
        assertTrue(mafioso2.estaVivo(), "Un mafioso no puede matar a otro mafioso");
        assertTrue(ciudadano.estaVivo());
    }

    @Test
    public void test08PadrinoNoEsSospechosoParaDetective() {
        // Arrange
        Jugador padrino    = new Jugador("Padrino",    new Padrino());
        Jugador detective  = new Jugador("Detective",  new Detective());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        detective.elegirObjetivo(padrino);

        Partida partida = new Partida(
                List.of(padrino, detective, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act - la noche ejecuta la investigación del detective
        partida.avanzar();

        // Assert - el Padrino aparece como inocente
        assertFalse(padrino.esSospechoso(),
                "El Padrino debe parecer inocente ante el Detective");
    }

    @Test
    public void test09MafiosoEsSospechosoParaDetective() {
        // Arrange
        Jugador mafioso    = new Jugador("Mafioso",    new Mafioso());
        Jugador detective  = new Jugador("Detective",  new Detective());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        detective.elegirObjetivo(mafioso);

        Partida partida = new Partida(
                List.of(mafioso, detective, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act
        partida.avanzar();

        // Assert
        assertTrue(mafioso.esSospechoso(),
                "Un Mafioso debe aparecer como sospechoso ante el Detective");
    }

    @Test
    public void test10SinObjetivoNocturnoNadieEsEliminado() {
        // Arrange - ningún jugador elige objetivo
        Jugador mafioso    = new Jugador("Mafioso",    new Mafioso());
        Jugador ciudadano1 = new Jugador("Ciudadano1", new Ciudadano());
        Jugador ciudadano2 = new Jugador("Ciudadano2", new Ciudadano());

        Partida partida = new Partida(
                List.of(mafioso, ciudadano1, ciudadano2),
                new FaseNocturna()
        );

        // Act
        partida.avanzar();

        // Assert
        assertTrue(mafioso.estaVivo());
        assertTrue(ciudadano1.estaVivo());
        assertTrue(ciudadano2.estaVivo());
    }
}
