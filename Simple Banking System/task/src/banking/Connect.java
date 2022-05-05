package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class Connect {
    String url = "jdbc:sqlite:";
    SQLiteDataSource dataSource = new SQLiteDataSource();

    //constructor
    public Connect(String input) {
        url = url + input;
        dataSource.setUrl(url);
    }

    //create database
    public void createDataBase() {
        try (Connection conn = dataSource.getConnection()) {
            //Statement creation
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAccount(Account anAccount) {
        String insertSql = "INSERT INTO card(number, pin) VALUES(?,?)";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pstmnt = conn.prepareStatement(insertSql);
            pstmnt.setString(1, anAccount.getCardNumber());
            pstmnt.setString(2, anAccount.getPIN());
            pstmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginPossible(String cardNumber, String pin) {
        boolean loginPossible = false;
        String selectSql = "SELECT * FROM card WHERE number = ? AND pin = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pstmnt = conn.prepareStatement(selectSql);
            pstmnt.setString(1, cardNumber);
            pstmnt.setString(2, pin);
            ResultSet result = pstmnt.executeQuery();
            if (result.next()) {
                loginPossible = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginPossible;
    }

    public boolean cardExists(String cardNumber) {
        boolean cardExists = false;
        String selectSql = "SELECT * FROM card WHERE number = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pstmnt = conn.prepareStatement(selectSql);
            pstmnt.setString(1, cardNumber);
            ResultSet result = pstmnt.executeQuery();
            if (result.next()) {
                cardExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardExists;
    }

    public int getBalance(String cardNumber) {
        int balance = 0;
        String selectSql = "SELECT * FROM card WHERE number = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pstmnt = conn.prepareStatement(selectSql);
            pstmnt.setString(1, cardNumber);
            ResultSet result = pstmnt.executeQuery();
            while (result.next()){
                balance = result.getInt("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void addIncome(String cardNumber, int income) {
        String updateSql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pstmnt = conn.prepareStatement(updateSql);
            pstmnt.setInt(1, income);
            pstmnt.setString(2, cardNumber);
            pstmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transfer(String cardNumber, String targetCardNumber, int amount) {
        // TODO

        String increaseFundsSql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        String decreaseFundsSql = "UPDATE card SET balance = balance - ? WHERE number = ?";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement increaseFunds = conn.prepareStatement(increaseFundsSql);
                 PreparedStatement decreaseFunds = conn.prepareStatement(decreaseFundsSql)) {

                // Increase funds
                increaseFunds.setInt(1, amount);
                increaseFunds.setString(2, targetCardNumber);
                increaseFunds.executeUpdate();

                // Decrease funds
                decreaseFunds.setInt(1, amount);
                decreaseFunds.setString(2, cardNumber);
                decreaseFunds.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAccount(String cardNumber) {
        String deleteSql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pstmnt = conn.prepareStatement(deleteSql);
            pstmnt.setString(1, cardNumber);
            pstmnt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
