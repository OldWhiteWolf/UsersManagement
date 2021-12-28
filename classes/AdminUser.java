package classes;

public class AdminUser extends AdvUser{

    /**
     * Constructor of the Admin's Class.
     * @param fullName Admin's full name.
     * @param userName Admin's name.
     * @param password Admin's password.
     * @param group Admin's group.
     */
    public AdminUser(String fullName, String userName, String password, String group){
        super(fullName, userName, password, group);
    }

    /**
     * This method change the full name of an specific user.
     * @param userName User's name of the selected user.
     * @param newFullName New full name of the selected user.
     */
    void changeUserFullName(String userName, String newFullName){
        for (int i = 0; i < MySystem.getDataBase().userListSize(); i++){
            if (userName.equals(MySystem.getDataBase().getUser(i).getUserName())){
                MySystem.getDataBase().getUser(i).setAccess(true);
                MySystem.getDataBase().getUser(i).setFullName(newFullName);
                MySystem.getDataBase().getUser(i).setAccess(false);
            }
        }
    }

    /**
     * This method change the user name of an specific user.
     * @param userName User's name of the selected user.
     * @param newUserName New user name of the selected user.
     */
    void changeUserName(String userName, String newUserName){
        for (int i = 0; i < MySystem.getDataBase().userListSize(); i++){
            if (userName.equals(MySystem.getDataBase().getUser(i).getUserName())){
                MySystem.getDataBase().getUser(i).setAccess(true);
                MySystem.getDataBase().getUser(i).setUserName(newUserName);
                MySystem.getDataBase().getUser(i).setAccess(false);
            }
        }
    }

    /**
     * This method change the password of an specific user.
     * @param userName User's name of the selected user.
     * @param newPassword New password of the selected user.
     */
    void changeUserPassword(String userName, String newPassword){
        for (int i = 0; i < MySystem.getDataBase().userListSize(); i++){
            if (userName.equals(MySystem.getDataBase().getUser(i).getUserName())){
                MySystem.getDataBase().getUser(i).setAccess(true);
                MySystem.getDataBase().getUser(i).setPassword(newPassword);
                MySystem.getDataBase().getUser(i).setAccess(false);
            }
        }
    }

    /**
     * This method change the group of an specific user.
     * @param userName User's name of the selected user.
     * @param newGroup New group of the selected user.
     */
    void changeUserGroup(String userName, String newGroup){
        for (int i = 0; i < MySystem.getDataBase().userListSize(); i++){
            if (userName.equals(MySystem.getDataBase().getUser(i).getUserName())){
                newGroup=newGroup.toUpperCase();
                if (newGroup.equalsIgnoreCase(UsersBBDD.A)){
                    replaceUser(i, new AdminUser(MySystem.getDataBase().getUser(i).getFullName(), userName,
                            MySystem.getDataBase().getUser(i).getPassword(), newGroup));
                } else if (newGroup.equalsIgnoreCase(UsersBBDD.AU)){
                    replaceUser(i, new AdvUser(MySystem.getDataBase().getUser(i).getFullName(), userName,
                            MySystem.getDataBase().getUser(i).getPassword(), newGroup));
                } else if (newGroup.equalsIgnoreCase(UsersBBDD.U)){
                    replaceUser(i, new User(MySystem.getDataBase().getUser(i).getFullName(), userName,
                            MySystem.getDataBase().getUser(i).getPassword(), newGroup));
                }
            }
        }
    }

    /**
     * This method put a new user in another user's place in the ArrayList from the DataBase.
     * @param userIndex User position in the ArrayList.
     * @param user New user.
     */
    private void replaceUser(int userIndex, User user){
        MySystem.getDataBase().replaceUser(userIndex, user);
    }
}
