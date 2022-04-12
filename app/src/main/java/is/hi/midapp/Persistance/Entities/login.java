package is.hi.midapp.Persistance.Entities;

import com.google.gson.annotations.SerializedName;

public class login {
    @SerializedName("EmailField")
    private String EmailField;
    @SerializedName("Password_id")
    private String Password_id;

    public login(String Password_id, String EmailField) {
        this.Password_id = Password_id;
        this.EmailField = EmailField;
    }
    public String getEmailField() {
        return EmailField;
    }

    public void setEmailField(String EmailField) {
        this.EmailField = EmailField;
    }

    public String getPassword() {
        return Password_id;
    }

    public void setPassworde(String Password_id) {
        this.Password_id = Password_id;
    }

}
