package is.hi.midapp.Persistance.Entities;

import android.widget.Button;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String editName;
    @SerializedName("Password_id")
    private String Password_id;
    @SerializedName("Repeatpassword_id")
    private String Repeatpassword_id;
    @SerializedName("EmailField")
    private String EmailField;
    @SerializedName("Subscribe_button")
    private Button Subscribe_button;


    public User(String editName, String Password_id, String Repeatpassword_id, String EmailField, Button Subscribe_button) {
        this.editName = editName;
        this.Password_id = Password_id;
        this.Repeatpassword_id = Repeatpassword_id;
        this.EmailField = EmailField;
        this.Subscribe_button = Subscribe_button;

    }

    public String getName() {
        return editName;
    }

    public void setName(String editName) {
        this.editName = editName;
    }

    public String getPassword() {
        return Password_id;
    }

    public void setPassworde(String Password_id) {
        this.Password_id = Password_id;
    }

    public String getRepeatpassword_id() {
        return Repeatpassword_id;
    }

    public void setRepeatpassword(String Repeatpassword_id) {
        this.Repeatpassword_id = Repeatpassword_id;
    }

    public String getEmailField() {
        return EmailField;
    }

    public void setEmailField(String EmailField) {
        this.EmailField = EmailField;
    }




}
