package by.epam.eshop.entity;


public class User implements Entity {
    private static final long serialVersionUID = 1L;

    private int id;
    private String login;
    private String password;
    private String fistName;
    private String lastName;
    private String email;
    private Role role;

    public User () {}


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

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role.toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) {
            return false;
        }
        if (login==null || !login.equals(user.getLogin())) {
            return false;
        }
        if (password==null || !password.equals(user.getPassword())) {
            return false;
        }
        if (fistName == null || !fistName.equals(user.getFistName())){
            return false;
        }
        if (lastName == null || !lastName.equals(user.getLastName())) {
            return false;
        }
        if (email == null || !email.equals(user.getEmail())) {
            return false;
        }
        if (role != getRole()) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fistName != null ? fistName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    private enum Role {
        ADMIN,CUSTOMER
    }

}
