package classes;

import java.io.Serializable;

public class User implements Serializable {

    private String fullName, userName, password, group;
    private boolean access = false;

    /**
     * Constructor of the User's Class
     * @param fullName User's full name.
     * @param userName User's name.
     * @param password User's password.
     * @param group User's group.
     */
    public User(String fullName, String userName, String password, String group){
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.group = group;
        if (this.group != null) this.group.toUpperCase();
    }

    /**
     *
     * @return User's full name.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * This method change the user's full name for a new one.
     * @param fullName New user's full name.
     */
    void setFullName(String fullName) {
        if (access) this.fullName = fullName;
    }

    /**
     *
     * @return User's name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method change the user's name for a new one.
     * @param userName New user's full name.
     */
    void setUserName(String userName) {
        if (access) this.userName = userName;
    }

    /**
     *
     * @return User´s password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method change the user's password for a new one.
     * @param password New user's password.
     */
    void setPassword(String password) {
        if (access) this.password = password;
    }

    /**
     *
     * @return User´s group.
     */
    public String getGroup() {
        return group;
    }

    /**
     * This method change the user's group for a new one.
     * @param group New user's group.
     */
    void setGroup(String group) {
        if (access) this.group = group;
    }

    /**
     *
     * @return User's access.
     */
    public boolean getAccess(){
        return access;
    }

    /**
     * This method change the user's access state.
     * @param access Modify the access state.
     */
    void setAccess(boolean access){
        this.access = access;
    }

    /**
     * This method change the user's own password for a new one.
     * @param actualPassword Actual user's password.
     * @param newPassword New user's password.
     * @param confirmNewPassword Confirmation of the new user's password.
     */
    void changeMyPassword(String actualPassword, String newPassword, String confirmNewPassword){
        if (actualPassword.equals(getPassword()) && newPassword.equals(confirmNewPassword)){
            setAccess(true);
            setPassword(newPassword);
            setAccess(false);
        }
    }
}
