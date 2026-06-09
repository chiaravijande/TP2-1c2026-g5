package jugadores;

import estado.EstadoJugador;
import estado.EstadoVivo;
import nocturno.ContextoNocturno;
import roles.Rol;
import votacion.Votacion;

public class Jugador {

    private String nombre;
    private Rol rol;
    private EstadoJugador estado;

    public Jugador(
            String nombre,
            Rol rol
    ) {
        this.nombre = nombre;
        this.rol = rol;
        this.estado = new EstadoVivo();
    }

    public void realizarAccionNocturna(
            ContextoNocturno contexto,
            Jugador objetivo
    ) {
        estado.realizarAccionNocturna(
                this,
                contexto,
                objetivo
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

    public void morir() {
        estado.morir(this);
    }

    public void cambiarEstado(
            EstadoJugador nuevoEstado
    ) {
        this.estado = nuevoEstado;
    }

    public Rol getRol() {
        return rol;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean estaVivo() {
        return estado.estaVivo();
    }
}