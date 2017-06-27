package datos;

public class GetterAndSetter {

    private String host;
    private String nomBD; 
    private String user; 
    private String password;
    
    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public String getNomBD() {
        return nomBD;
    }

    public void setNomBD(String nomBD) {
        this.nomBD = nomBD;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
