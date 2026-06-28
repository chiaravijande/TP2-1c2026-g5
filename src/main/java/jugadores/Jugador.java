package jugadores;

import estado.*;
import nocturno.AccionNocturna;
import nocturno.RegistroNocturno;
import partida.ContadorDeBandos;
import partida.Partida;
import roles.*;
import votacion.Votacion;

import java.util.List;
import java.util.Optional;

public class Jugador {

    private String nombre;
    private Rol rol;
    private EstadoJugador estado;
    private Optional<Jugador> objetivoNocturno = Optional.empty();

    public Jugador(String nombre, Rol rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.estado = new EstadoVivo();
    }

    public Rol getRol() {
        return rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void ejecutarTurnoNocturno(
            RegistroNocturno contexto
    ) {
        estado.ejecutarTurnoNocturno(
                this,
                contexto
        );
    }

    public void elegirObjetivo(Jugador objetivo) {

        this.objetivoNocturno = Optional.of(objetivo);
    }

    public void ejecutarTurnoDiurno(
            Partida partida
    ) {
        estado.ejecutarTurnoDiurno(
                this,
                partida
        );
    }

    public void votarEn(
            Votacion votacion
    ) {
        estado.votarEn(
                this,
                votacion
        );
    }

    public void votarEnBallotage(
            Votacion votacion,
            List<Jugador> candidatos
    ) {
        estado.votarEnBallotage(
                this,
                votacion,
                candidatos
        );
    }

    public void abstenerse() {
        this.objetivoNocturno = Optional.empty();
    }

    public void agruparseEn(
            ContadorDeBandos contador
    ) {
        estado.agruparseEn(
                this,
                contador
        );
    }

    public void eliminar() {
        estado.eliminar(this);
    }

    public void cambiarEstado(
            EstadoJugador nuevoEstado
    ) {
        this.estado = nuevoEstado;
    }

    public boolean estaVivo() {
        return estado.estaVivo();
    }

    public boolean esSospechoso() {
        return rol.esSospechoso();
    }

    public boolean esAliadoDe(
            Jugador otro) {

        return rol.esAliadoDe(
                otro.rol
        );
    }

    public void agruparseSegunRol(
            ContadorDeBandos contador
    ) {
        rol.agruparseEn(contador);
    }

    public Optional<AccionNocturna> prepararAccionNocturna() {

        return rol.prepararAccion(this, objetivoNocturno);
    }

    public Optional<Rol> obtenerRolRevelado() {
        if (!estaVivo()) {
            return Optional.of(rol);
        }

        return Optional.empty();
    }
}