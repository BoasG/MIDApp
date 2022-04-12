package is.hi.midapp.Persistance.Entities;

import com.google.gson.annotations.SerializedName;

public class login {
    @SerializedName("Email")
    private String Email;
    @SerializedName("Password")
    private String Password;

    public login(String Password, String EmailField) {
        this.Password = Password;
        this.Email = Email;
    }
    public String getEmailField() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password_id) {
        this.Password = Password_id;
    }

}
