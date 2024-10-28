package si.skavtko.v1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class SimpleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    resp.setContentType("text/html; charset=UTF-8");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter writer = resp.getWriter();

    // izpis uporabnikov
    writer.append("<br/><br/>Uporabniki:<br/>");
    writer.append("Bo treba pa še kaj narediti preden se kaj izpiše tu :))" + "<br/><br/>");

    writer.close();
    // TODO: missing implementation
    }
}
