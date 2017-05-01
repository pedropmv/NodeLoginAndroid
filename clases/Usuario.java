package pedropablomoral.com.nodeloginandroid.clases;

import java.util.Date;

/**
 * Created by Azu on 15/04/2017.
 */

public class Usuario {
    private int id;
    private String username;
    private String email;
    private boolean isAdmin;
    private Date createdAt;
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
