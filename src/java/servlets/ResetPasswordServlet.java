/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.AccountService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 677571
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getParameter("uuid"));
        request.setAttribute("uuid", request.getParameter("uuid"));
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getAttribute("send") != null && (boolean) request.getAttribute("send") == true) {
            try {
                AccountService as = new AccountService();
                String path = getServletContext().getRealPath("/WEB-INF");
                String url = request.getRequestURL().toString();
                String email = request.getParameter("email");
                as.resetPassword(email, path, url);
                request.setAttribute("errormessage", "A password reset email has been sent");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            } catch (Exception ex) {
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }

        String password = request.getParameter("newPassword");
        String uuid = request.getParameter("uuid");

        AccountService accountService = new AccountService();
        try {
            accountService.changePassword(uuid, password);
            request.setAttribute("errormessage", "password succesfully reset!");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        } catch (Exception ex) {
            Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errormessage", "error resetting password");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

    }
}
