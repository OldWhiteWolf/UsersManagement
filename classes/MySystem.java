package classes;

import java.io.*;
import java.util.ArrayList;

public class MySystem {

    private static UsersBBDD dataBase = new UsersBBDD();
    public static UsersBBDD getDataBase(){
        return dataBase;
    }

    private int id = 0;
    private int accessLevel = 0;
    private boolean permission = false;

    /**
     * This method return permission's value.
     * @return permission's value.
     */
    public boolean isPermission(){
        return permission;
    }

    /**
     * This method return id's value.
     * @return id value.
     */
    public int getId(){
        return id;
    }

    /**
     * This method return accessLevel's value.
     * @return accessLevel value.
     */
    public int getAccessLevel(){
        return accessLevel;
    }

    /**
     * This method start the session of the program if the user is registered in the DataBase.
     * @param userName User's name.
     * @param password User's password.
     */
    public void starSession(String userName, String password){
        for (int i = 0; i < dataBase.userListSize(); i++){
            if (userName.equals(dataBase.getUser(i).getUserName())
                    && password.equals(dataBase.getUser(i).getPassword())){
                id = i;
                permission = true;
            }
        }
        if (permission){
            if (dataBase.getUser(id) instanceof AdminUser){
                accessLevel = 3;
            } else if (dataBase.getUser(id) instanceof AdvUser){
                accessLevel = 2;
            } else if (dataBase.getUser(id) instanceof User){
                accessLevel = 1;
            }
        }
    }

    /**
     * This method close the session.
     */
    public void closeSession(){
        permission = false;
        accessLevel = 0;
    }

    /**
     * This method change the user's own password for a new one.
     * @param actualPassword Actual user's password.
     * @param newPassword New user's password.
     * @param confirmNewPassword Confirmation of the new user's password.
     */
    public void editMyPassword(String actualPassword, String newPassword, String confirmNewPassword){
        ((User)dataBase.getUser(id)).changeMyPassword(actualPassword, newPassword, confirmNewPassword);
    }

    /**
     * This method add an user to the UsersBBDD.
     * @param fullName User's full name.
     * @param userName User's name.
     * @param password User's password.
     * @param group User's group.
     */
    public void addUser(String fullName, String userName, String password, String group){
        if(group.equalsIgnoreCase(UsersBBDD.A) || group.equalsIgnoreCase(UsersBBDD.AU) || group.equalsIgnoreCase(UsersBBDD.U)){
            ((AdvUser)dataBase.getUser(id)).addUser(fullName, userName, password, group);
        }
    }

    /**
     * This method remove the selected user from the UsersBBDD.
     * @param userName User's name from de user you want to remove.
     */
    public void removeUser(String userName){
        ((AdvUser)dataBase.getUser(id)).removeUser(userName);
    }

    /**
     * This method change the full name of an specific user.
     * @param userName User's name of the selected user.
     * @param newFullName New full name of the selected user.
     */
    public void editUserFullName(String userName, String newFullName){
        ((AdminUser)dataBase.getUser(id)).changeUserFullName(userName, newFullName);
    }

    /**
     * This method change the user name of an specific user.
     * @param userName User's name of the selected user.
     * @param newUserName New user name of the selected user.
     */
    public void editUserName(String userName, String newUserName){
        ((AdminUser)dataBase.getUser(id)).changeUserName(userName, newUserName);
    }

    /**
     * This method change the password of an specific user.
     * @param userName User's name of the selected user.
     * @param newPassword New password of the selected user.
     */
    public void editUserPassword(String userName, String newPassword){
        ((AdminUser)dataBase.getUser(id)).changeUserPassword(userName, newPassword);
    }

    /**
     * This method change the group of an specific user.
     * @param userName User's name of the selected user.
     * @param newGroup New group of the selected user.
     */
    public void editUserGroup(String userName, String newGroup){
        ((AdminUser)dataBase.getUser(id)).changeUserGroup(userName, newGroup);
    }

    /**
     * This method return a users' list.
     * @param kinOfUser Kind of users you want to be listed (Admin, Advance User or User).
     */
    public ArrayList<User> toListUsers(String kinOfUser){
        return ((AdvUser)dataBase.getUser(id)).toListUsers(kinOfUser);
    }

    /**
     * This method create the directory for the save and export users files.
     */
    private void createDirectory(){
        String path = "C:" + File.separator;
        String directoryName = path.concat("FinalProject6Saves");
        File directory = new File(directoryName);
        if (!directory.exists()){
            directory.mkdirs();
        }
    }

    /**
     * This method export the selected user's data in a .txt file.
     * @param userName Selected user's name.
     */
    public void exportUserData(String userName) throws IOException {
        this.createDirectory();
        File file=new File("C:" + File.separator + "FinalProject6Saves" + File.separator +
                "Exported_User_" + ((AdvUser)dataBase.getUser(id)).exportUser(userName).getUserName() +".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Nombre Completo: " + ((AdvUser)dataBase.getUser(id)).exportUser(userName).getFullName());
        bw.newLine();
        bw.write("Nombre de Usuario: " + ((AdvUser)dataBase.getUser(id)).exportUser(userName).getUserName());
        bw.newLine();
        bw.write("Contraseña: " + ((AdvUser)dataBase.getUser(id)).exportUser(userName).getPassword());
        bw.newLine();
        bw.write("Grupo: " + ((AdvUser)dataBase.getUser(id)).exportUser(userName).getGroup());
        bw.close();
    }

    /**
     * This method export the user own data in a .txt file.
     */
    public void exportMyData() throws IOException {
        this.createDirectory();
        File file=new File("C:" + File.separator + "FinalProject6Saves" + File.separator +
                "Exported_User_" + dataBase.getUser(id).getUserName() + ".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Nombre Completo: " + dataBase.getUser(id).getFullName());
        bw.newLine();
        bw.write("Nombre de Usuario: " + dataBase.getUser(id).getUserName());
        bw.newLine();
        bw.write("Contraseña: " + dataBase.getUser(id).getPassword());
        bw.newLine();
        bw.write("Grupo: " + dataBase.getUser(id).getGroup());
        bw.close();
    }

    /**
     * This method save the user's list data in the UserDataBase in a .txt file.
     */
    public void save() throws IOException {
        this.createDirectory();
        FileOutputStream save = new FileOutputStream("C:" + File.separator + "FinalProject6Saves" +
                File.separator + "save.dat");
        ObjectOutputStream writingFile = new ObjectOutputStream(save);
        writingFile.writeObject(dataBase);
        writingFile.close();
    }

    /**
     * This method load a user's list data in the UserDataBase from a .txt file.
     */
    public void load() throws IOException, ClassNotFoundException {
        this.createDirectory();
        FileInputStream load = new FileInputStream("C:" + File.separator + "FinalProject6Saves" +
                File.separator + "save.dat");
        ObjectInputStream readingFile = new ObjectInputStream(load);
        dataBase = (UsersBBDD) readingFile.readObject();
        readingFile.close();
    }
}
