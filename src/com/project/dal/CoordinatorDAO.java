package com.project.dal;
import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.CheckInput;
import com.project.dal.connectorDAO.DBConnector;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CoordinatorDAO {
    DBConnector dbConnector;
    CheckInput checkInput ;
    public CoordinatorDAO() throws IOException {
        dbConnector = new DBConnector();
        checkInput = new CheckInput();
    }
    public List<Coordinator> getAllCoordinators() throws SQLException {
        List<Coordinator>allCoordinators= new ArrayList<>();
        try (Connection connection = dbConnector.getConnection()){
            String sql0 = "SELECT * FROM categories_users WHERE category=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setString(1,"coordinator");
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet1.next()) {
                int id = resultSet1.getInt("id");
                String sql = "SELECT * FROM users WHERE category = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
                preparedStatement1.setInt(1, id);
                ResultSet resultSet = preparedStatement1.executeQuery();
                while (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String sql1 = "SELECT * FROM credentials WHERE user_id =? ";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql1);
                    preparedStatement2.setInt(1, userId);
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet2.next()) {
                        allCoordinators.add(new Coordinator(userId,
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet2.getString("user_name"),
                                resultSet2.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getInt("phone_number")));
                    }
                }
            }
        }
        return allCoordinators;
    }

    public Coordinator createCoordinator(String firstName, String lastName, String userName, String passWord, String email, int phoneNumber) throws UserException {
        exceptionCreation(firstName,lastName,userName,passWord,email);
        Coordinator coordinator = null;
        int idCategory=0;
        try (Connection connection= dbConnector.getConnection()){
            String sql0="SELECT * FROM categories_users WHERE category=?";
            PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
            preparedStatement0.setString(1,"customer");
            ResultSet resultSet = preparedStatement0.executeQuery();
            while (resultSet.next()){
                idCategory = resultSet.getInt("id");
            }
            String sql= "INSERT INTO users VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,email);
            preparedStatement.setInt(4,phoneNumber);
            preparedStatement.setInt(5,idCategory);
            preparedStatement.executeUpdate();


            ResultSet resultSet1= preparedStatement.getGeneratedKeys();
            while (resultSet1.next()){
                int id = resultSet1.getInt(1);
                String sql1 = "INSERT INTO credentials VALUES(?,?,?)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1,id);
                preparedStatement1.setString(2,userName);
                preparedStatement1.setString(3,passWord);

                ResultSet resultSet2 = preparedStatement1.executeQuery();
                while (resultSet2.next()){
                    coordinator = new Coordinator(id,firstName,lastName,userName,passWord,email,phoneNumber);
                }
            }
        }catch (SQLException sqlException){
            throw new UserException("something went wrong in the database",new Exception());
        }
        return coordinator;
    }
    public void deleteCoordinator(Coordinator coordinator)throws SQLException{

            try (Connection connection= dbConnector.getConnection()){
                String sql0 = "DELETE FROM event_coordinator WHERE coordinator_id = ?";
                PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
                preparedStatement0.setInt(1,coordinator.getId());
                preparedStatement0.executeUpdate();
                String sql = "DELETE FROM users WHERE id= ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,coordinator.getId());
                preparedStatement.executeUpdate();
                String sql1 = "DELETE FROM credentials WHERE user_id= ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1,coordinator.getId());
                preparedStatement1.executeUpdate();
        }
    }
    public Coordinator editCoordinators(Coordinator coordinator) throws SQLException{
        try (Connection connection = dbConnector.getConnection()){
            String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ?, WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, coordinator.getFirstName());
            preparedStatement.setString(2, coordinator.getLastName());
            preparedStatement.setString(3, coordinator.getEmail());
            preparedStatement.setInt(4,  coordinator.getPhoneNumber());
            preparedStatement.setInt(5,coordinator.getId());

            preparedStatement.executeUpdate();

            String sql1 = "UPDATE credentials SET user_name = ?, password = ?, WHERE user_id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1,coordinator.getUserName());
            preparedStatement1.setString(2,coordinator.getPassWord());
            preparedStatement1.setInt(3,coordinator.getId());
        }
        return coordinator;
    }

    private void exceptionCreation(String firstName, String lastName, String userName, String passWord, String email) throws UserException {
        if (firstName.isEmpty())
            throw new UserException("Please enter your first name.",new Exception());
        if (lastName.isEmpty())
            throw new UserException("Please enter your last name.",new Exception());
        if (!checkInput.isValidName(firstName)){
            UserException userException = new UserException("Please find a valid first name",new Exception());
            userException.setInstructions("A valid name is only composed of Alphabet characters");
            throw userException;
        }
        if (!checkInput.isValidName(lastName)){
            UserException userException = new UserException("Please find a valid last name",new Exception());
            userException.setInstructions("A correct name is only composed of Alphabet characters");
            throw userException;
        }
        if (userName.isEmpty())
            throw new UserException("Please find a username.",new Exception());

        if (userNameTaken(userName)){
            UserException userException = new UserException("user name already exists.",new Exception());
            userException.setInstructions("Please find another one and try again.");
            throw userException;
        }

        if (CheckInput.isPasswordValid(passWord)) {
                UserException userException = new UserException("Please find a correct password.",new Exception());
                userException.setInstructions("A password is composed of an 9-length string containing only characters and digits, at least two of the digits");
                throw userException;
            }
        if(email.isEmpty())
            throw new UserException("Please enter your email.",new Exception());

        if (!CheckInput.isValidEmailAddress(email))
            throw new UserException("Please enter a valid email.",new Exception());
        }

    private Boolean userNameTaken(String userName) throws UserException {
        try (Connection connection = dbConnector.getConnection()){
            String sql = "SELECT * FROM Users WHERE user_name=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        }catch (SQLException sqlException){
            throw  new UserException("Something went wrong in the database",new Exception());
        }
        return false;
    }
    public Coordinator logInCredentials(String userName)throws SQLException,UserException{
        for (Coordinator coordinator: getAllCoordinators()){
            if (userName.equals(coordinator.getUserName()))
                return coordinator;
        }
        return null;
    }
}
