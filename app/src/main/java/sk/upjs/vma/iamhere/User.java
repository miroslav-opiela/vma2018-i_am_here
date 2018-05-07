package sk.upjs.vma.iamhere;

import java.io.Serializable;

/**
 * Entita reprezentujuca jedneho pouzivatela so svojim username.
 * Kvôli retrofitu by mala byt Java Bean - teda serializable, prazdny verejny konstruktor, gettre a settre.
 */
class User implements Serializable {

    private String login;

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return login;
    }
}
