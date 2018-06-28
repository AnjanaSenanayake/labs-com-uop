import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/search")

public class AddressBookServlet extends HttpServlet 
{      
	private static final long serialVersionUID = 1L;
	String filename;
    
    //Servlet initialization
    public void init() throws ServletException 
    {     
        ServletConfig config = getServletConfig();
        
        //get the value of the init-parameter
        filename = config.getInitParameter("addressBook.txt");
        ServletContext sc = config.getServletContext();
        String path = sc.getRealPath("addressBook.txt");
        AddressBook.initAddressBook(path);        
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
            
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        
        String contactName = request.getParameter("name");        
        String detail = AddressBook.search(contactName);
        doPost(request, response);
        
        PrintWriter out = response.getWriter();        
        String htmlRespone = "<html>";
        htmlRespone += "<h2>"+contactName+": "+detail+"<br/>";            
        htmlRespone += "</html>";
        out.println(htmlRespone);                 
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html;charset=UTF-8");
    	String contactName = request.getParameter("name");        
        String detail = AddressBook.search(contactName);
        if(detail.equals("404"))
        {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND, "The Contact Not Found");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }// </editor-fold>

}
