package airport.data.user;

/**
 * Пользователь.
 */
public class User {

    /**
     * логип, пароль
     */
    private String login, password;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
