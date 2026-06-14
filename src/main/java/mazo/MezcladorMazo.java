package mazo;

import roles.Rol;
import java.util.Collections;
import java.util.List;

public class MezcladorMazo {

    public void mezclar(List<Rol> roles) {
        Collections.shuffle(roles);
    }
}
