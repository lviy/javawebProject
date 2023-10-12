package lviy_javaweb_work1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//@WebServlet("/Logon")
public class Logon extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	
    protected void getJSFlush(HttpServletResponse response, String inf) throws IOException { //java输出js弹窗
   	 response.setContentType("text/html; charset=UTF-8"); //转码
   	   PrintWriter out = response.getWriter();
   	   String temp = "alert('" + inf + "');";
   	   out.flush();
   	   out.println("<script>");
   	   out.println(temp);
   	   out.println("history.back();");
   	   out.println("</script>");
   	   out.close();
   	   return;
   }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doPost(request, response);
		//request.getRequestDispatcher("/success.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//编码问题
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String username = request.getParameter("name");
		String password = request.getParameter("pwd");
		//PrintWriter out = response.getWriter();
		//out.println("账号：" + name + "<br> 密码：" + pwd);
		if(username == null || password == null) {
			getJSFlush(response, "账号或密码出现问题！");
			System.out.println("账号或密码出现问题！");
			//response.sendRedirect("login.html");
		}
		else {
			//System.out.println("[信息]: 用户" + username +"登入！");
			//request.getRequestDispatcher("success.html").forward(request,response);
			//response.sendRedirect("success.html");
			String url =
                    "jdbc:mysql://localhost:3306/jdbc?serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
            String SQLid = "root";
            String SQLpsw = "322345";
            try {
            	Class.forName ("com.mysql.cj.jdbc.Driver"); //注册驱动
                Connection conn = DriverManager.getConnection(url, SQLid, SQLpsw);
                Statement stmt = conn.createStatement();
                String sql = "select password from webusers where username=\""+username+"\""; //查询语句 获取key值(id)
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.next()) { 
                	String curretPassword = rs.getString("password");
                	if(curretPassword.equals(password)) { //登录成功
                	System.out.println("[信息]: 用户" + username +"登入！");
                	response.sendRedirect("success.html"); 
                } 
                	else {
                		System.out.println("[信息]: 用户" + username +"密码错误！");
                		getJSFlush(response, "密码错误！");
                	}
                }
                else {
                	System.out.println("[信息]: 用户名不存在！");
                	getJSFlush(response, "用户名不存在！");
           
                }
            
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		}
	}


