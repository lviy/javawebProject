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
    
    protected void getJSFlush(HttpServletResponse response, String inf) throws IOException { //java���js����
    	 response.setContentType("text/html; charset=UTF-8"); //ת��
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
			getJSFlush(response, "������Ϣû����д��");
			System.out.println("[��Ϣ]: ������Ϣû����д��");
			//response.sendRedirect("index.html");
		} 
		else if(!password.equals(Confirmed_password)){
			getJSFlush(response, "�����������벻һ�£�");
			System.out.println("[��Ϣ]: �����������벻һ�£�>>> " + password + "///" + Confirmed_password );
			//response.sendRedirect("register.html");
		}
		else { //System.out.println("[��Ϣ]: �û�ע��ɹ�>>"+username+"\t"+password+"\t"+brithday+"\t"+sex+"\t"+Email);
            String url =
                    "jdbc:mysql://localhost:3306/jdbc?serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
            String SQLid = "root";
            String SQLpsw = "322345";
            try {
            	Class.forName ("com.mysql.cj.jdbc.Driver"); //ע������
                Connection conn = DriverManager.getConnection(url, SQLid, SQLpsw);
                Statement stmt = conn.createStatement();
                PreparedStatement pp = null;
                String sql = "select id from webusers where username=\""+username+"\""; //��ѯ��� ��ȡkeyֵ(id)
                ResultSet rs = stmt.executeQuery(sql);
                // 1.��Ѱ�����ݿ����Ƿ�����ͬusername �еĻ��򷵻�register.html û�������
                if(rs.next()) {
                	System.out.println("[��Ϣ]: �û����ظ���");
                	//response.setContentType("text/html; charset=UTF-8"); //ת��
/*                    PrintWriter out = response.getWriter();  //ֱ�Ӵ�ӡ��js������ʾ
                    out.flush();
                    out.println("<script>");
                    out.println("alert('���û����Ѵ��ڣ����������룡');");
                    out.println("history.back();");
                    out.println("</script>");
 * */				
                	getJSFlush(response, "���û����Ѵ��ڣ����������룡");
                	//response.sendRedirect("register.html"); //����ע��ҳ��
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
                	getJSFlush(response, "ע��ɹ���");
                	System.out.println("[��Ϣ]: �û�ע��ɹ�>>"+username+"\t"+password+"\t"+_birthday+"\t"+sex+"\t"+Email);
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

