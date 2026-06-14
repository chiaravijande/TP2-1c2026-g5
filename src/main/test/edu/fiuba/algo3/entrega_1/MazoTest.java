package edu.fiuba.algo3.entrega_1;

import jugadores.Jugador;
import mazo.CantidadDeJugadoresInvalidaException;
import mazo.ConfiguracionMazo;
import mazo.Mazo;
import mazo.MezcladorMazo;
import roles.Rol;
import roles.ciudadanos.Ciudadano;
import roles.ciudadanos.Detective;
import roles.ciudadanos.Medico;
import roles.ciudadanos.Sheriff;
import roles.Mafioso;
import roles.mafia.Padrino;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MazoTest {

    // /////////////////////////////////
    // Mock y Helper
    // /////////////////////////////////

    private List<Jugador> jugadoresMock(int cantidad) {
        return IntStream.range(0, cantidad)
                .mapToObj(i -> mock(Jugador.class))
                .collect(Collectors.toList());
    }

    private List<Rol> rolesGenerados(int cantidadJugadores) {
        return ConfiguracionMazo.para(cantidadJugadores).generarRoles(cantidadJugadores);
    }

    private long contarInstancias(List<Rol> roles, Class<?> tipo) {
        return roles.stream().filter(tipo::isInstance).count();
    }

    ///////////////////////////////////////
    //Sección de Test: composición correcta del mazo según cantidad de jugadores
    // ///////////////////////////////////

    @Test
    void conCincoJugadoresGeneraUnMafiosoYUnRolEspecialYCompletaConCiudadanos() {
        // Arrange
        int cantidadJugadores = 5;

        // Act
        List<Rol> roles = rolesGenerados(cantidadJugadores);

        // Assert
        assertEquals(5, roles.size());
        assertEquals(1, roles.stream().filter(r -> r.getClass() == Mafioso.class).count());
        assertEquals(1, contarInstancias(roles, Detective.class) +
                contarInstancias(roles, Medico.class));
    }

    @Test
    void conSieteJugadoresGeneraDosMafiososDetectiveYMedico() {
        // Arrange
        int cantidadJugadores = 7;

        // Act
        List<Rol> roles = rolesGenerados(cantidadJugadores);

        // Assert
        assertEquals(7, roles.size());
        assertEquals(2, roles.stream().filter(r -> r.getClass() == Mafioso.class).count());
        assertEquals(1, contarInstancias(roles, Detective.class));
        assertEquals(1, contarInstancias(roles, Medico.class));
    }

    @Test
    void conDiezJugadoresGeneraDosMafiososPadrinoDetectiveMedicoYSheriff() {
        // Arrange
        int cantidadJugadores = 10;

        // Act
        List<Rol> roles = rolesGenerados(cantidadJugadores);

        // Assert
        assertEquals(10, roles.size());
        assertEquals(2, roles.stream().filter(r -> r.getClass() == Mafioso.class).count());
        assertEquals(1, contarInstancias(roles, Padrino.class));
        assertEquals(1, contarInstancias(roles, Detective.class));
        assertEquals(1, contarInstancias(roles, Medico.class));
        assertEquals(1, contarInstancias(roles, Sheriff.class));
    }

    @Test
    void conCantidadInvalidaDe4JugadoresLanzaExcepcion() {
        // Arrange - Act - Assert
        assertThrows(CantidadDeJugadoresInvalidaException.class,
                () -> ConfiguracionMazo.para(4));
    }

    @Test
    void conCantidadInvalidaDe13JugadoresLanzaExcepcion() {
        // Arrange - Act - Assert
        assertThrows(CantidadDeJugadoresInvalidaException.class,
                () -> ConfiguracionMazo.para(13));
    }

    //////////////////////////////////////////
    // Seccion de Test: reparto exactamente un rol por jugador
    ////////////////////////////////////////

    @Test
    void cadaJugadorRecibeExactamenteUnRol() {
        // Arrange
        List<Jugador> jugadores = jugadoresMock(7);
        Mazo mazo = new Mazo(ConfiguracionMazo.para(7));

        // Act
        mazo.repartir(jugadores);

        // Assert
        jugadores.forEach(jugador ->
                verify(jugador, times(1)).setRol(any(Rol.class))
        );
    }

    /////////////////////////////////////////////////
    // Seccion Test: Mazo utiliza Mezclador para asegurar la aleatoriedad
    ///////////////////////////////////////////////

    @Test
    void elMazoUtilizaElMezcladorAntesDeRepartir() {
        // Arrange
        MezcladorMazo mezclador = mock(MezcladorMazo.class);

        List<Jugador> jugadores = jugadoresMock(10);

        Mazo mazo = new Mazo(
                ConfiguracionMazo.para(10),
                mezclador);
        // Act
        mazo.repartir(jugadores);
        // Assert
        verify(mezclador, times(1)).mezclar(anyList());
    }

    @Test
    void elMezcladorConservaTodosLosRoles() {
        //Arrange
        List<Rol> roles = new ArrayList<>();

        roles.add(new Mafioso());
        roles.add(new Mafioso());
        roles.add(new Detective());
        roles.add(new Medico());
        roles.add(new Ciudadano());

        List<Rol> copia = new ArrayList<>(roles);

        MezcladorMazo mezclador = new MezcladorMazo();

        //Act
        mezclador.mezclar(roles);

        //Assert
        assertEquals(copia.size(), roles.size());
        assertTrue(roles.containsAll(copia));
        assertTrue(copia.containsAll(roles));
    }

    @Test
    void elMezcladorModificaElOrdenDeLaLista() {
        // Arrange
        List<Rol> roles = new ArrayList<>(List.of(
                new Mafioso(), new Detective(), new Medico(),
                new Ciudadano(), new Ciudadano(), new Ciudadano(), new Ciudadano()
        ));

        List<Class<?>> ordenOriginal = roles.stream()
                .map(Object::getClass)
                .collect(Collectors.toList());
        MezcladorMazo mezclador = new MezcladorMazo();

        // Act
        mezclador.mezclar(roles);

        // Assert
        List<Class<?>> ordenMezclado = roles.stream()
                .map(Object::getClass)
                .collect(Collectors.toList());
        assertNotEquals(ordenOriginal, ordenMezclado,
                "El mezclador debería haber modificado el orden de los roles");
    }
}