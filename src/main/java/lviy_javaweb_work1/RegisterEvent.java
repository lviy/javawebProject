package lviy_javaweb_work1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
public class RegisterEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterEvent() {
        super();
        // TODO Auto-generated constructor stub
    }
    
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
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String Confirmed_password = request.getParameter("Confirmed_password");
		String _birthday = request.getParameter("birthday");
		String sex = request.getParameter("sex");
		String Email = request.getParameter("Email");
		
		if(username == null || password == null || Confirmed_password == null) {
			getJSFlush(response, "必填信息没有填写！");
			System.out.println("[信息]: 必填信息没有填写！");
			//response.sendRedirect("index.html");
		} 
		else if(!password.equals(Confirmed_password)){
			getJSFlush(response, "两次密码输入不一致！");
			System.out.println("[信息]: 两次密码输入不一致！>>> " + password + "///" + Confirmed_password );
			//response.sendRedirect("register.html");
		}
		else { //System.out.println("[信息]: 用户注册成功>>"+username+"\t"+password+"\t"+brithday+"\t"+sex+"\t"+Email);
            String url =
                    "jdbc:mysql://localhost:3306/jdbc?serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
            String SQLid = "root";
            String SQLpsw = "322345";
            try {
            	Class.forName ("com.mysql.cj.jdbc.Driver"); //注册驱动
                Connection conn = DriverManager.getConnection(url, SQLid, SQLpsw);
                Statement stmt = conn.createStatement();
                PreparedStatement pp = null;
                String sql = "select id from webusers where username=\""+username+"\""; //查询语句 获取key值(id)
                ResultSet rs = stmt.executeQuery(sql);
                // 1.先寻找数据库中是否有相同username 有的话则返回register.html 没有则入表
                if(rs.next()) {
                	System.out.println("[信息]: 用户名重复！");
                	//response.setContentType("text/html; charset=UTF-8"); //转码
/*                    PrintWriter out = response.getWriter();  //直接打印出js弹窗提示
                    out.flush();
                    out.println("<script>");
                    out.println("alert('此用户名已存在，请重新输入！');");
                    out.println("history.back();");
                    out.println("</script>");
 * */				
                	getJSFlush(response, "此用户名已存在，请重新输入！");
                	//response.sendRedirect("register.html"); //返回注册页面
                } 
                else {
                	sql = "insert into webusers(username,password,birthday,sex,Email) value(?,?,?,?,?);";
                	pp = conn.prepareStatement(sql);
                	pp.setString(1, username);
                	pp.setString(2, password);
                	pp.setString(3, _birthday);
                	pp.setString(4, sex);
                	pp.setString(5, Email);
                	pp.executeUpdate();
                	getJSFlush(response, "注册成功！");
                	System.out.println("[信息]: 用户注册成功>>"+username+"\t"+password+"\t"+_birthday+"\t"+sex+"\t"+Email);
                	//response.sendRedirect("login.html");
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

