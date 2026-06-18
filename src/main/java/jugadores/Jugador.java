package jugadores;

import estado.*;
import roles.Rol;
import nocturno.RegistroNocturno;
import partida.Partida;
import partida.ContadorDeBandos;
import votacion.Votacion;

import java.util.List;
import java.util.Optional;

public class Jugador {

    private String nombre;
    private Rol rol;
    private EstadoJugador estado;


    public Jugador(String nombre) {
        this.nombre = nombre;
        this.estado = new EstadoVivo();
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void ejecutarTurnoNocturno(RegistroNocturno contexto) {
        this.estado.ejecutarTurnoNocturno(this, contexto);
    }

    public void ejecutarTurnoDiurno(Partida partida) {
        this.estado.ejecutarTurnoDiurno(this, partida);
    }

    public void votarEn(Votacion votacion) {
        this.estado.votarEn(this, votacion);
    }

    public void agruparseEn(ContadorDeBandos contador) {
        this.estado.agruparseEn(this, contador);
    }

    public void eliminar() {
        this.estado.eliminar(this);
    }

    public boolean estaVivo() {
        return this.estado instanceof EstadoVivo;
    }

    public Rol getRol() {
        return this.rol;
    }

    public void cambiarEstado(EstadoJugador nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void votarEnBallotage(Votacion votacion, List<Jugador> candidatos) {
        this.estado.votarEnBallotage(this, votacion, candidatos);
    }

    public Optional<Rol> obtenerRolRevelado() {
        if (!this.estaVivo()) {
            return Optional.of(this.rol);
        }
        return Optional.empty();
    }
}