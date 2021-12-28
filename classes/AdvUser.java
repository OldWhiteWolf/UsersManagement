package classes;

import java.util.ArrayList;

public class AdvUser extends User{

    /**
     * Constructor of the Advance User's Class
     * @param fullName User's full name.
     * @param userName User's name.
     * @param password User's password.
     * @param group User's group.
     */
    public AdvUser(String fullName, String userName, String password, String group){
        super(fullName, userName, password, group);
    }

    /**
     * This method add an user to the UsersBBDD.
     * @param fullName User's full name.
     * @param userName User's name.
     * @param password User's password.
     * @param group User's group.
     */
    void addUser(String fullName, String userName, String password, String group){
        MySystem.getDataBase().addUser(fullName, userName, password, group.toUpperCase());
    }

    /**
     * This method remove the selected user from the UsersBBDD.
     * @param userName User's name from de user you want to remove.
     */
    void removeUser(String userName){
        MySystem.getDataBase().removeUser(userName);
    }

    /**
     * This method return a users' list.
     * @param kindOfUser Kind of users you want to be listed (Admin, Advance User or User).
     */
    ArrayList<User> toListUsers(String kindOfUser){
        return MySystem.getDataBase().toListUsers(kindOfUser);
    }

    /**
     * This method returns the chosen user.
     * @param userName
     * @return A User
     */
    User exportUser(String userName){
        for(int i = 0; i< MySystem.getDataBase().userListSize(); i++){
            if(userName.equals(MySystem.getDataBase().getUser(i).getUserName())){
                return MySystem.getDataBase().getUser(i);
            }
        }
        return null;
    }
}
