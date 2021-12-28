package sample;

import classes.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //---------------------------------------WORK SPACE PANE ELEMENTS---------------------------------------------------
    @FXML private Pane pane_workSpace;
    @FXML private MenuBar menuBar;
    @FXML private Menu menu_file;
    @FXML private Menu menu_toList;
    @FXML private Menu menu_window;
    @FXML private Menu menu_help;
    @FXML private MenuItem menuItem_save;
    @FXML private MenuItem menuItem_load;
    @FXML private MenuItem menuItem_closeSession;
    @FXML private MenuItem menuItem_exit;
    @FXML private MenuItem menuItem_users;
    @FXML private MenuItem menuItem_advanceUsers;
    @FXML private MenuItem menuItem_administrators;
    @FXML private MenuItem menuItem_allUsers;
    @FXML private MenuItem menuItem_usersProfile;
    @FXML private MenuItem menuItem_myProfile;
    @FXML private MenuItem menuItem_help;
    //------------------------------------END OF WORK SPACE PANE ELEMENTS-----------------------------------------------
    //-----------------------------------------USERS PANE ELEMENTS------------------------------------------------------
    @FXML private Pane pane_users;
    @FXML private TableView<User> tableView_users;
    @FXML private TableColumn tableColumn_fullName;
    @FXML private TableColumn tableColumn_userName;
    @FXML private TableColumn tableColumn_group;
    @FXML private VBox vBox_userForm;
    @FXML private Label label_fullName;
    @FXML private Label label_fixFullName;
    @FXML private Label label_username;
    @FXML private Label label_fixUsername;
    @FXML private Label label_password;
    @FXML private Label label_fixPassword;
    @FXML private Label label_group;
    @FXML private Label label_fixGroup;
    @FXML private Label label_notification;
    @FXML private TextField textField_fullName;
    @FXML private TextField textField_userName;
    @FXML private TextField textField_password;
    @FXML private ComboBox<String> comboBox_groups;
    @FXML private ButtonBar buttonBar_formOptions;
    @FXML private Button button_addUser;
    @FXML private Button button_deleteUser;
    @FXML private Button button_editUser;
    @FXML private Button button_exportUser;
    //---------------------------------------END OF USERS PANE ELEMENTS-------------------------------------------------
    //------------------------------------------MYSELF PANE ELEMENTS----------------------------------------------------
    @FXML private Pane pane_mySelf;
    @FXML private VBox vBox_myOptions;
    @FXML private HBox hBox_newPassword;
    @FXML private Label label_fixMyPassword;
    @FXML private ToggleButton toggleButton_changePassword;
    @FXML private Button button_accept;
    @FXML private Button button_exportMyData;
    @FXML private PasswordField passwordField_password;
    @FXML private PasswordField passwordField_newPassword;
    @FXML private PasswordField passwordField_newPassword2;
    //---------------------------------------END OF MYSELF PANE ELEMENTS------------------------------------------------
    //------------------------------------------LOGIN PANE ELEMENTS-----------------------------------------------------
    @FXML private Pane pane_login;
    @FXML private VBox vBox_startSession;
    @FXML private Label label_userStartSession;
    @FXML private Label label_passwordStartSession;
    @FXML private Label label_welcome;
    @FXML private TextField textField_startSession;
    @FXML private PasswordField passwordField_startSession;
    @FXML private Button button_startSession;

    //---------------------------------------END OF LOGIN PANE ELEMENTS-------------------------------------------------

    private MySystem system = new MySystem();
    private ObservableList<User> observableList_users;
    private int usersPositionOnTable;
    private final ObservableList<String> observableList_groups = FXCollections.observableArrayList(UsersBBDD.A, UsersBBDD.AU, UsersBBDD.U);
    private Alert alert = new Alert(Alert.AlertType.NONE);
    private String userKind = "All Users";
    private short count = 3;

    //----------------------------------------USERS PANE FUNCTIONALITY--------------------------------------------------
    @FXML public void addUser(){
        int userNameCount = 0;
        boolean userGroupCheck = true;
        for(int i = 0; i < MySystem.getDataBase().userListSize(); i++){
            if(MySystem.getDataBase().getUser(i).getUserName().equals(textField_userName.getText())){
                userNameCount++;
            }
        }
        if(comboBox_groups.getValue() == null){
            userGroupCheck = false;
            label_fixGroup.setText("* Obligatory field!");
        }
        if(userNameCount != 0){
            label_fixUsername.setText("* This user name already exist!");
        }else if(textField_userName.getText().equalsIgnoreCase("")){
            label_fixUsername.setText("* Obligatory field!");
        }else{
            label_fixUsername.setText("");
        }
        if(textField_fullName.getText().equalsIgnoreCase("")){
            label_fixFullName.setText("* Obligatory field!");
        }else{
            label_fixFullName.setText("");
        }
        if(textField_password.getText().equalsIgnoreCase("")){
            label_fixPassword.setText("* Obligatory field!");
        }else{
            label_fixPassword.setText("");
        }
        if(!textField_fullName.getText().equalsIgnoreCase("") && userNameCount == 0
                && !textField_userName.getText().equalsIgnoreCase("")
                && !textField_password.getText().equalsIgnoreCase("") && userGroupCheck){
            system.addUser(textField_fullName.getText(), textField_userName.getText(), textField_password.getText(), comboBox_groups.getValue());
            textField_fullName.setText("");
            textField_userName.setText("");
            textField_password.setText("");
            comboBox_groups.setValue("");
            label_fixUsername.setText("");
            label_fixGroup.setText("");
            toList(userKind);
        }
    }

    @FXML public void removeUser(){
        alert.setResult(null);
        boolean allow = true;
        for(int i = 0; i < MySystem.getDataBase().userListSize(); i++){
            if(MySystem.getDataBase().getUser(i).getUserName().equals(textField_userName.getText())
                    && MySystem.getDataBase().getUser(i) instanceof AdminUser
                    && system.getAccessLevel() == 2){
                allow = false;
                label_fixGroup.setText("* You have no right over ADMIN!");
            }
        }
        if (MySystem.getDataBase().getUser(system.getId()).getUserName().equals(textField_userName.getText())) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to delete yourself?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CANCEL){
                allow = false;
            }else if (alert.getResult() == ButtonType.OK){
                allow = true;
            }
        }
        if(textField_userName.getText().equalsIgnoreCase("")){
            label_fixUsername.setText("* Select a user!");
        }else{
            label_fixUsername.setText("");
        }
        if(allow && !textField_userName.getText().equalsIgnoreCase("")){
            system.removeUser(textField_userName.getText());
            textField_fullName.setText("");
            textField_userName.setText("");
            textField_password.setText("");
            comboBox_groups.setValue("");
            label_fixGroup.setText("");
            if (alert.getResult() == ButtonType.OK){
                closeSession();
            }else {
                toList(userKind);
            }
        }
    }

    @FXML public void editUser(){
        boolean userGroupCheck = true;
        String selectedUser = "";
        if(textField_userName.getText().equalsIgnoreCase("")){
            label_fixUsername.setText("* Select a user!");
        }else{
            label_fixUsername.setText("");
        }
        if(textField_fullName.getText().equalsIgnoreCase("")){
            label_fixFullName.setText("* Obligatory field!");
        }else{
            label_fixFullName.setText("");
        }
        if(!comboBox_groups.getValue().equalsIgnoreCase(UsersBBDD.A)
                && !comboBox_groups.getValue().equalsIgnoreCase(UsersBBDD.AU)
                && !comboBox_groups.getValue().equalsIgnoreCase(UsersBBDD.U)){
            userGroupCheck = false;
            label_fixGroup.setText("* Mismatch Group!");
        }else{
            label_fixGroup.setText("");
        }
        if(textField_password.getText().equalsIgnoreCase("")){
            label_fixPassword.setText("* Obligatory field!");
        }else{
            label_fixPassword.setText("");
        }
        if(!textField_fullName.getText().equalsIgnoreCase("") && !textField_userName.getText().equalsIgnoreCase("")
                && !textField_password.getText().equalsIgnoreCase("") && userGroupCheck){
            selectedUser = MySystem.getDataBase().getUser(usersPositionOnTable).getUserName();
            boolean allow =true;
            if (selectedUser.equals(MySystem.getDataBase().getUser(system.getId()).getUserName())){
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure?");
                alert.setContentText("Do you want to edit yourself?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CANCEL) allow = false;
            }
            if (allow) {
                system.editUserFullName(selectedUser, textField_fullName.getText());
                system.editUserPassword(selectedUser, textField_password.getText());
                system.editUserGroup(selectedUser, comboBox_groups.getValue());
                system.editUserName(selectedUser, textField_userName.getText());
                textField_fullName.setText("");
                textField_userName.setText("");
                textField_password.setText("");
                comboBox_groups.setValue("");
                toList(userKind);
            }
        }
    }

    @FXML public void exportUser(){
        boolean allow = true;
        for(int i = 0; i < MySystem.getDataBase().userListSize(); i++) {
            if (MySystem.getDataBase().getUser(i).getUserName().equals(textField_userName.getText())
                    && MySystem.getDataBase().getUser(i) instanceof AdminUser && system.getAccessLevel() == 2) {
                allow = false;
            }
        }
        if(textField_userName.getText().equalsIgnoreCase("")){
            label_fixUsername.setText("* Select a user!");
        }else{
            label_fixUsername.setText("");
        }
        if(!allow){
            label_fixGroup.setText("* You have no right over ADMIN!");
        }else{
            label_fixGroup.setText("");
        }
        if(allow && !textField_userName.getText().equalsIgnoreCase("")){
            try {
                system.exportUserData(textField_userName.getText());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("USER'S DATA HAVE BEEN EXPORTED!");
                alert.setContentText("Check in C:/FinalProject6Saves");
                alert.showAndWait();
            } catch (IOException e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User's data can't be exported!");
                alert.setContentText("The directory pointed doesn't exist.");
                alert.showAndWait();
            }
        }
    }
    //-------------------------------------END OF USERS PANE FUNCTIONALITY----------------------------------------------

    //--------------------------------------WORK SPACE PANE FUNCTIONALITY-----------------------------------------------
    private void toList(String userKind){
        int usersNumber = 0;
        while (observableList_users.size() > 0){
            observableList_users.remove(0);
        }
        for (int i = 0; i < system.toListUsers(userKind).size(); i++){
            observableList_users.add(system.toListUsers(userKind).get(i));
            usersNumber++;
        }
        label_notification.setTextFill(Color.color(0.22578, 0.754, 0.99));
        if (userKind.equals(menuItem_allUsers.getText())) {
            label_notification.setText("ENTERPRISE USERS: " + usersNumber);
        } else if (userKind.equals(menuItem_users.getText())) {
            label_notification.setText("USERS: " + usersNumber);
        } else if (userKind.equals(menuItem_advanceUsers.getText())) {
            label_notification.setText("ADVANCE USERS: " + usersNumber);
        } else if (userKind.equals(menuItem_administrators.getText())) {
            label_notification.setText("ADMINS: " + usersNumber);
        }
    }

    @FXML public void toListAllUsers(){
        userKind = menuItem_allUsers.getText();
        toList(userKind);
    }

    @FXML public void toListUsers(){
        userKind = menuItem_users.getText();
        toList(userKind);
    }

    @FXML public void toListAdvUsers(){
        userKind = menuItem_advanceUsers.getText();
        toList(userKind);
    }

    @FXML public void toListAdminUsers(){
        userKind = menuItem_administrators.getText();
        toList(userKind);
    }

    @FXML public void save(){
        try {
            system.save();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("SAVED!");
            alert.setContentText("Check in C:/FinalProject6Saves");
            alert.showAndWait();
        } catch (IOException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The file doesn't exist");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    @FXML public void load(){
        try {
            system.load();
            if (pane_workSpace.isVisible()){
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("LOADED!");
                alert.setContentText("");
                alert.showAndWait();
            }
            toList(userKind);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (pane_workSpace.isVisible()){
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("There is not save file.");
                alert.setContentText("Be careful!!! You haven't saved yet, \nor the save file has been deleted.");
            }else {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("There is not preview save file.");
                alert.setContentText("The application has the default users:\n" +
                        "   User Name: Admin     Password: Admin\n" +
                        "   User Name: AdvUser  Password: AdvUser\n" +
                        "   User Name: User        Password: User");
            }
            alert.showAndWait();
        }
    }

    @FXML public void closeSession(){
        system.closeSession();
        pane_workSpace.setVisible(false);
        pane_mySelf.setVisible(false);
        pane_users.setVisible(false);
        pane_login.setVisible(true);
        hBox_newPassword.setDisable(true);
        toggleButton_changePassword.setSelected(false);

        while (observableList_users.size() > 0){
            observableList_users.remove(0);
        }
        textField_fullName.setText("");
        textField_userName.setText("");
        textField_password.setText("");
        comboBox_groups.setValue("");
        label_fixFullName.setText("");
        label_fixUsername.setText("");
        label_fixMyPassword.setText("");
        label_fixGroup.setText("");

        passwordField_password.setText("");
        passwordField_newPassword.setText("");
        passwordField_newPassword2.setText("");
        label_fixMyPassword.setText("");

        count = 3;
        textField_startSession.setText("");
        textField_startSession.setStyle("-fx-border-color: none;");
        textField_startSession.setPromptText("");
        passwordField_startSession.setText("");
        passwordField_startSession.setStyle("-fx-border-color: none;");
        passwordField_startSession.setPromptText("");
    }

    @FXML public void exit(){
        Platform.exit();
    }

    @FXML public void changeToUserWindow(){
        pane_mySelf.setVisible(false);
        pane_users.setVisible(true);
        menu_toList.setDisable(false);
    }

    @FXML public void changeToMyWindow(){
        pane_mySelf.setVisible(true);
        pane_users.setVisible(false);
        menu_toList.setDisable(true);
    }

    @FXML public void help(){
        // TODO
    }
    //------------------------------------END OF WORK SPACE PANE FUNCTIONALITY------------------------------------------

    //-----------------------------------------MYSELF PANE FUNCTIONALITY------------------------------------------------
    @FXML public void changePassOption(){
        if (toggleButton_changePassword.isSelected()) {
            hBox_newPassword.setDisable(false);
            toggleButton_changePassword.setText("Don't Change Password");
        } else if (!toggleButton_changePassword.isSelected()){
            passwordField_password.setText("");
            passwordField_newPassword.setText("");
            passwordField_newPassword2.setText("");
            hBox_newPassword.setDisable(true);
            toggleButton_changePassword.setText("Change Password");
        }
    }

    @FXML public void exportMyData(){
        try {
            system.exportMyData();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("YOUR DATA HAVE BEEN EXPORTED!");
            alert.setContentText("Check in C:/FinalProject6Saves");
            alert.showAndWait();
        } catch (IOException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your data can't be exported!");
            alert.setContentText("The directory pointed doesn't exist.");
            alert.showAndWait();
        }
    }

    @FXML public void acceptChangePass(){
        if(!passwordField_password.getText().equals(MySystem.getDataBase().getUser(system.getId()).getPassword())){
            label_fixMyPassword.setText("* Wrong password!");
        }else if(!passwordField_newPassword.getText().equals(passwordField_newPassword2.getText())){
            label_fixMyPassword.setText("* Wrong verification!");
        }else{
            label_fixMyPassword.setText("");
        }
        if(passwordField_password.getText().equals(MySystem.getDataBase().getUser(system.getId()).getPassword())
                && passwordField_newPassword.getText().equals(passwordField_newPassword2.getText())){
            system.editMyPassword(passwordField_password.getText(), passwordField_newPassword.getText(), passwordField_newPassword2.getText());
            passwordField_password.setText("");
            passwordField_newPassword.setText("");
            passwordField_newPassword2.setText("");
            hBox_newPassword.setDisable(true);
            tableView_users.refresh();
            toggleButton_changePassword.selectedProperty().setValue(false);
        }
    }
    //--------------------------------------END OF MYSELF PANE FUNCTIONALITY--------------------------------------------

    //-----------------------------------------LOGIN PANE FUNCTIONALITY-------------------------------------------------
    @FXML public void startSession(){
        system.starSession(textField_startSession.getText(), passwordField_startSession.getText());
        count--;
        if(system.getAccessLevel() == 1){
            pane_login.setVisible(false);
            pane_mySelf.setVisible(true);
            pane_workSpace.setVisible(true);
            menu_toList.setDisable(true);
            menuItem_usersProfile.setDisable(true);
        }else if(system.getAccessLevel() == 2){
            pane_login.setVisible(false);
            pane_mySelf.setVisible(true);
            pane_workSpace.setVisible(true);
            menu_toList.setDisable(true);
            this.toListAllUsers();
            menuItem_usersProfile.setDisable(false);
            button_editUser.setDisable(true);
        }else if(system.getAccessLevel() == 3){
            pane_login.setVisible(false);
            pane_mySelf.setVisible(true);
            pane_workSpace.setVisible(true);
            menu_toList.setDisable(true);
            this.toListAllUsers();
            menuItem_usersProfile.setDisable(false);
            button_editUser.setDisable(false);
        } else{
            if (count > 0) {
                textField_startSession.setText("");
                textField_startSession.setStyle("-fx-border-color: red;");
                textField_startSession.setPromptText("Check user name or password");
                passwordField_startSession.setText("");
                passwordField_startSession.setStyle("-fx-border-color: red;");
                passwordField_startSession.setPromptText("You have " + count + " more attempt(s)");
            }else {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Users Management is closing!");
                alert.setContentText("You are out of attempts.");
                alert.showAndWait();
                Platform.exit();
            }
        }
    }
    //--------------------------------------END OF LOGIN PANE FUNCTIONALITY---------------------------------------------

    //------------------------------------------SELECT USER FROM TABLE--------------------------------------------------
    public User getFromTableSelectedUser(){
        if(tableView_users != null){
            List<User> table = tableView_users.getSelectionModel().getSelectedItems();
            if(table.size() == 1){
                final User selectedUser = table.get(0);
                return selectedUser;
            }
        }
        return null;
    }

    private void putSelectedUser(){
        final User enterpriseUser = getFromTableSelectedUser();
        usersPositionOnTable = observableList_users.indexOf(enterpriseUser);
        if(enterpriseUser != null){
            textField_fullName.setText(enterpriseUser.getFullName());
            textField_userName.setText(enterpriseUser.getUserName());
            if(enterpriseUser.getGroup().equalsIgnoreCase(UsersBBDD.A)
                    && system.getAccessLevel() == 2){
                textField_password.setText("");
            }else{
                textField_password.setText(enterpriseUser.getPassword());
            }
            comboBox_groups.setValue(enterpriseUser.getGroup());
        }
    }

    private final ListChangeListener<User> selectUserOnTable=new ListChangeListener<User>() {
        @Override
        public void onChanged(Change<? extends User> c) {
            putSelectedUser();
        }
    };
    //----------------------------------------END OF SELECT USER FROM TABLE---------------------------------------------

    //---------------------------------------INITIALIZATION OF THE TABLEVIEW--------------------------------------------
    private void initializeTableView_users(){
        tableColumn_fullName.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
        tableColumn_userName.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        tableColumn_group.setCellValueFactory(new PropertyValueFactory<User, String>("group"));
        observableList_users = FXCollections.observableArrayList();
        tableView_users.setItems(observableList_users);
    }
    //----------------------------------END OF THE INITIALIZATION OF THE TABLEVIEW--------------------------------------

    //--------------------------------------IMPLEMENTED INITIALIZABLE'S METHOD------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane_workSpace.setVisible(false);
        pane_mySelf.setVisible(false);
        pane_users.setVisible(false);
        pane_login.setVisible(true);
        hBox_newPassword.setDisable(true);
        menu_toList.setDisable(true);
        this.initializeTableView_users();
        comboBox_groups.setItems(observableList_groups);
        final ObservableList<User> table_userSelect = tableView_users.getSelectionModel().getSelectedItems();
        table_userSelect.addListener(selectUserOnTable);
        this.toListAllUsers();
        this.load();
    }
}
