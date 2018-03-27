package com.aaa.dasi.dasi_td2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.aaa.dasi.dasi_td2.Service.*;
import com.aaa.dasi.dasi_td2.Service;
import com.google.gson.Gson;
import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aelomarial
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ActionServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ActionServlet at " + request.getContextPath() + "!!!</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }

        String action = request.getParameter("action");
        switch(action){
            case "listPeople" :
                
                Service s = new Service();
                List<Personne> lp = null;
                try{
                    lp = s.consulterListePersonnes();
                }catch(Exception ex){
                    throw new ServletException("Data access problem",ex);
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                printListPeople(out, lp);
                out.close();
                break;
                
            default :
                    break;
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

    public static void printListPeople(PrintWriter out, List<Personne> lp){
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        JsonArray jsonList = new JsonArray();
        for(Personne p : lp){
            JsonObject jsonPerson = new JsonObject();
            jsonPerson.addProperty("id", p.getId());
            jsonPerson.addProperty("civilite", p.getCivilite());
            jsonPerson.addProperty("nom", p.getNom());
            jsonPerson.addProperty("prenom", p.getPrenom());
            jsonPerson.addProperty("mail", p.getMail());
            jsonPerson.addProperty("adresse", p.getAdresse());
            jsonList.add(jsonPerson);
        }
        
        JsonObject container = new JsonObject();
        container.add("personnes", jsonList);
        out.println(gson.toJson(container));
    }
}
