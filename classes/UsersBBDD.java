package classes;

import java.io.Serializable;
import java.util.ArrayList;

public class UsersBBDD implements Serializable {
    public static final String A = "ADMIN";
    public static final String AU = "ADVANCE USER";
    public static final String U = "USER";

    private ArrayList<User> usersList = new ArrayList<User>();

    /**
     * Constructor of the UsersBBDD' class. This constructor start de class whit the tree kind of users.
     */
    public UsersBBDD(){
        usersList.add(new AdminUser("Administrador", "Admin", "Admin", A));
        usersList.add(new AdvUser("Usuario Avanzado", "AdvUser", "AdvUser", AU));
        usersList.add(new User("Usuario", "User","User", U));
    }

    /**
     * This method add an user to the DataBase.
     * @param fullName User's full name.
     * @param userName User's name.
     * @param password User's password.
     * @param group User's group.
     */
    void addUser(String fullName, String userName, String password, String group){
        if (group.equalsIgnoreCase(A)){
            usersList.add(new AdminUser(fullName, userName, password, group));
        }else if (group.equalsIgnoreCase(AU)){
            usersList.add(new AdvUser(fullName, userName, password, group));
        }else if (group.equalsIgnoreCase(U)){
            usersList.add(new User(fullName, userName, password, group));
        }
    }

    /**
     * This method remove the selected user from the DataBase.
     * @param userName User's name from de user you want to remove.
     */
    void removeUser(String userName){
        for (int i = 0; i < usersList.size(); i++){
            if (userName.equals(usersList.get(i).getUserName())){
                usersList.remove(i);
            }
        }
    }

    /**
     * This method replace the selected user from the DataBase.
     * @param userIndex User's index in the DataBase.
     * @param user New user.
     */
    void replaceUser(int userIndex, User user){
        usersList.set(userIndex, user);
    }

    /**
     * This method clean de DataBase.
     */
    void cleanUserList(){
        usersList.clear();
    }

    /**
     * This method return a users' list.
     * @param kindOfUser Kind of users you want to be listed (Admin, Advance User or User).
     * @return Users' list.
     */
    ArrayList<User> toListUsers(String kindOfUser){
        ArrayList<User> list = new ArrayList<User>();
        list.clear();
        if (kindOfUser.equals("All Users")){
            for (int i = 0; i < usersList.size(); i++){
                list.add(usersList.get(i));
            }
        } else if (kindOfUser.equals("Users")){
            for (int i = 0; i < usersList.size(); i++){
                if (!(usersList.get(i) instanceof AdvUser) && !(usersList.get(i) instanceof AdminUser)){
                    list.add(usersList.get(i));
                }
            }
        } else if (kindOfUser.equals("Advance Users")){
            for (int i = 0; i < usersList.size(); i++){
                if ((usersList.get(i) instanceof AdvUser) && !(usersList.get(i) instanceof AdminUser)){
                    list.add(usersList.get(i));
                }
            }
        } else if (kindOfUser.equals("Administrators")){
            for (int i = 0; i < usersList.size(); i++){
                if (usersList.get(i) instanceof AdminUser){
                    list.add(usersList.get(i));
                }
            }
        }
        return list;
    }

    /**
     * This method return a user from the ArrayList.
     * @param userIndex User's index in the ArrayList
     * @return A user.
     */
    public User getUser(int userIndex){
        return usersList.get(userIndex);
    }

    /**
     * This method return a users' ArrayList size.
     * @return The ArrayList size.
     */
    public int userListSize(){
        return usersList.size();
    }
}
