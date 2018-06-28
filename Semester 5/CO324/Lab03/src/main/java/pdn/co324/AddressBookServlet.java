package pdn.co324;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns={"/searchCon","/addCon"})

public class AddressBookServlet extends HttpServlet 
{      
	private static final long serialVersionUID = 1L;
	AddressBook oldAddressBook = new AddressBook();

	String filename;
    
    //Servlet initialization
    public void init() throws ServletException 
    {     
        ServletConfig config = getServletConfig();
        
        //get the value of the init-parameter
        filename = config.getInitParameter("addressBook.txt");
        ServletContext sc = config.getServletContext();
        String path = sc.getRealPath("addressBook.txt");
        oldAddressBook.initAddressBook(path);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");

        String contactName = request.getParameter("name");
        HttpSession session = request.getSession(false);

        AddressBook newAddressBook;

        if(session!=null)
        {
            newAddressBook = (AddressBook)session.getAttribute("newAddressObject");
            String detail[] = newAddressBook.searchContact(contactName);
            if(detail!=null)
            {
                PrintWriter out = response.getWriter();
                String htmlRespone = "<html>";
                htmlRespone += "<h2>Name: "+contactName+"<br/>";
                htmlRespone += "<h2>Phone: "+detail[0]+"<br/>";
                htmlRespone += "<h2>Email: "+detail[1]+"<br/>";
                htmlRespone += "</html>";
                out.println(htmlRespone);
            }
            else
            {
                System.out.println("sess");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "The Contact Not Found");
            }
        }
        else
        {
            String detail[] = oldAddressBook.searchContact(contactName);
            if(detail!=null)
            {
                PrintWriter out = response.getWriter();
                String htmlRespone = "<html>";
                htmlRespone += "<h2>Name: "+contactName+"<br/>";
                htmlRespone += "<h2>Phone: "+detail[0]+"<br/>";
                htmlRespone += "<h2>Email: "+detail[1]+"<br/>";
                htmlRespone += "</html>";
                out.println(htmlRespone);
            }
            else
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "The Contact Not Found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html;charset=UTF-8");
    	String contactName = request.getParameter("name");
    	String[] contactDetails = new String[2];
        contactDetails[0] = request.getParameter("phone");
        contactDetails[1] = request.getParameter("mail");
        AddressBook newAddressBook = new AddressBook();
        newAddressBook.addContact(contactName,contactDetails);

        HttpSession session = request.getSession(true);

        if(session!=null)
        {
            session.setAttribute("newAddressObject",newAddressBook);
        }

        PrintWriter out = response.getWriter();
        out.println("Contact Details Added");
    }
}
