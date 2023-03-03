/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Logan
 */
public class ServletEssai extends HttpServlet {

    private static ArrayList<Employe> listeEmploye = new ArrayList<Employe>();
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static String pilote = "org.gjt.mm.mysql.Driver";
    private static String url = new String("jdbc:mysql://localhost/gesemp");
    private Employe unEmploye;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            listeEmploye = new ArrayList<Employe>();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from emp");
            
            while(rs.next())
            {
                unEmploye = new Employe(rs.getDouble("empo"), rs.getString("ename"), rs.getString("job"), rs.getDouble("mgr"), rs.getDate("hiredate"), rs.getDouble("sal"), rs.getDouble("comm"), rs.getDouble("deptno"));
                listeEmploye.add(unEmploye);
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<table>");
            out.println("<tr><td>Empo</td><td>Ename</td><td>Job</td><td>mgr</td><td>hiredate</td><td>Salaire</td><td>Comm</td><td>Deptno</td></tr>");
            for(Employe e : listeEmploye)
            {
                out.println("<tr><td>" + e.getEmpo()+ "</td>");
                out.println("<td><a href='ServletAugment?numero=" + e.getEname() + "'>" + e.getEname() + "</a></td>");
                out.println("<td>" + e.getJob()+ "</td>");
                out.println("<td>" + e.getMgr()+ "</td>");
                out.println("<td>" + e.getHiredate()+ "</td>");
                out.println("<td>" + e.getSal()+ "</td>");
                out.println("<td>" + e.getComm()+ "</td>");
                out.println("<td>" + e.getDeptno()+ "</td></tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try
        {
            Class.forName(pilote);
            conn = DriverManager.getConnection(url,"root","");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(ServletEssai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}