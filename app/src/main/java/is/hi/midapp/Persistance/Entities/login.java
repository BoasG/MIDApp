package is.hi.midapp.Persistance.Entities;

import com.google.gson.annotations.SerializedName;

public class login {
    @SerializedName("Email")
    private String Email;
    @SerializedName("Password_id")
    private String Password_id;

    public login(String Password_id, String EmailField) {
        this.Password_id = Password_id;
        this.Email = Email;
    }
    public String getEmailField() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password_id;
    }

    public void setPassword(String Password_id) {
        this.Password_id = Password_id;
    }

}
