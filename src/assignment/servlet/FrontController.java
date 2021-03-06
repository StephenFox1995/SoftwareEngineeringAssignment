package assignment.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import assignment.command.Command;
import assignment.command.CommandFactory;
import assignment.command.CommandType;
import assignment.exceptions.CommandCreationException;


@WebServlet(urlPatterns={"/FrontController"})
public class FrontController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String CREATE_USER_PAGE = "CreateUserPage";
	private static final String ADD_ARTICLE_PAGE = "AddArticlePage";
	private static final String LOGIN_PAGE = "LoginPage";
	
	private static final String LOGIN_ACTION = "LoginUser";
	private static final String ADD_ARTICLE = "InsertArticle";
	private static final String NEWS_FEED = "NewsFeed";
	private static final String CREATE_USER = "CreateUser";
	private static final String VIEW_USER_PROFILE = "ViewUserProfile";
	private static final String BAN_USER = "BanUser";
	private static final String SHOW_ALL_USERS = "ShowAllUsers";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest (request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}
	


	/**
	 * Common method to process all client requests (GET and POST)
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		String forwardToJsp = "";		
		String commandToCreate = request.getParameter("action");
		CommandType commandType = null;
		
		if (commandToCreate.equalsIgnoreCase(ADD_ARTICLE_PAGE)) {
			forwardToPage(request, response, "/addArticlePage.jsp");
			return; 
		} else if (commandToCreate.equals(CREATE_USER_PAGE)) {
			forwardToPage(request, response, "/createUserPage.jsp");
			return;
		} else if (commandToCreate.equals(LOGIN_PAGE)) {
			forwardToPage(request, response, "/loginPage.jsp");
			return;
		}
		
		
		System.out.println("Request is: " + request.getParameter("action"));
		switch (commandToCreate) {
		case LOGIN_ACTION:
			commandType = CommandType.LOGIN_COMMAND;
			break;
		case ADD_ARTICLE:
			commandType = CommandType.ADD_ARTICLE;
			break;
		case NEWS_FEED:
			commandType = CommandType.GET_ALL_ARTICLE_COMMAND;
			break;
		case CREATE_USER:
			commandType = CommandType.CREATE_USER_COMMAND;
			break;
		case VIEW_USER_PROFILE:
			commandType = CommandType.USER_PROFILE_COMMAND;
			break;
		case BAN_USER:
			commandType = CommandType.BAN_USER_COMMAND;
			break;
		case SHOW_ALL_USERS:
			commandType = CommandType.SHOW_ALL_USERS_COMMAND;
			break;
		default:
			break;
		}

		
		CommandFactory commandFactory = (CommandFactory)CommandFactory.getSharedInstance();
		try {
			Command command = commandFactory.createCommand(commandType);
			forwardToJsp = command.execute(request, response);
			forwardToPage(request, response, forwardToJsp);
		} catch(CommandCreationException e) {
			// TODO: Appropriate error page here.
			e.printStackTrace();
		}
		
	}



	/**
	 * Forward to server to the supplied page
	 */
	private void forwardToPage(HttpServletRequest request, HttpServletResponse response, 
			String page){
		System.out.println("Page" + page);
		//Get the request dispatcher object and forward the request to the appropriate JSP page...
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
