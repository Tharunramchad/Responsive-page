import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (validateInput(firstName, lastName, email, password, confirmPassword)) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "username", "password");
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)");
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setString(4, password);
                pstmt.executeUpdate();
                response.sendRedirect("success.jsp");
            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Error registering user: " + e.getMessage());
                request.getRequestDispatcher("SignUpForm.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Invalid input data");
            request.getRequestDispatcher("SignUpForm.jsp").forward(request, response);
        }
    }

    private boolean validateInput(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return false;
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return false;
        }
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        if (!password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }
}