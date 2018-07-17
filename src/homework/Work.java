package homework;

import java.sql.*;
import java.util.Scanner;

import static java.lang.Class.forName;

/**
 * Created by hasee on 2018/7/17.
 */
public class Work {
    private Connection getConnection() {
        //1.加载驱动
        try {
            forName("com.mysql.cj.jdbc.Driver");
            //2.创建数据库连接字符串
            String dbURl = "jdbc:mysql://localhost:3306/hnb11?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            //3.创立数据库链接

            Connection connection = DriverManager.getConnection(dbURl, "root", "9420");
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void practiceInsertData(int id, String name, String publishers,String author ){
        Connection connection=null;
        Statement statement=null;
        try {
       Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hnb11?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                "root", "9420");
            String sql = "insert into work (id,book_name,book_publishers,book_author) values "+"(" + id + ",'" + name + "','" + publishers + "','"+author+"')";
            statement=connection.createStatement();
            int rows = statement.executeUpdate(sql);
            System.out.println("所影响的行数为：" + rows);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
         } catch (SQLException e) {
        e.printStackTrace();
    }

    finally {
        try {
            if (statement !=null) {

                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    private void practiceDeleteData(int id) {
        //1.创建数据库链接


        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hnb11?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "9420");
                //2.构建删除数据的sql语句
                String sql = "delete from work where id=" + id;
                // 执行删除语句
                Statement statement = connection.createStatement();
                //4.得到执行所影响的行数，判断是否执行成功
                int rows = statement.executeUpdate(sql);
                System.out.println("有" + rows + "行被删除成功！");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    private void practiceUpdateData(int id, String name, String publishers,String author) {
        //1.创建数据库链接
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hnb11?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "9420");
                //2.创建update sql 语句
                String sql = "Update work set book_name ='" + name + "',book_publishers='" + publishers + "'," +
                        "book_author ='"+author+"'where id=" + id;
                //3.执行update 语句
                Statement statement = connection.createStatement();
                //4.得到执行结果，确定是否成功
                int rows = statement.executeUpdate(sql);
                System.out.println("有" + rows + "行修改成功！");
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private  void  findAddData(){

        //1.获取数据库链接
        Connection connection = getConnection();
        //2.构建查询的sql语句
        String sql ="select * from work";
        //3.执行sql语句，并获得结婚集
        try {
            Statement  statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            //4.遍历结果集，输出每条记录的信息
            StringBuffer buffer = new StringBuffer();
            buffer.append("---------------------------------------------------------"+System.lineSeparator());
            buffer.append("id\t\t\t\t name\t\t\tpublishers\t\t\tauthor\t\t\tTime\t\t\t"+System.lineSeparator());
            buffer.append("---------------------------------------------------------"+System.lineSeparator());
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name =resultSet.getString(2);
                String publishers=resultSet.getString(3);
                String author = resultSet.getString(4);
                String time =resultSet.getString(5);
                buffer.append(id+ "\t\t|" + name +"\t\t\t\t|" + publishers +"\t\t\t\t\t|"+author+"\t\t\t\t|"+time+"\t\t\t"+System.lineSeparator());
            }
            System.out.println(buffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        Work work= new Work();
        Scanner scanner = new Scanner(System.in);
        work.findAddData();
        while (true){
            System.out.println("=================================================");
            System.out.println("|          欢迎使用书籍查询系统                   |");
            System.out.println("| 1.添加书籍      2.修改数据    3.删除数据        |");
            System.out.println("| 4.查询所有书籍               5. 退出系统       |");
            System.out.println("=================================================");

            System.out.println("请选择你要进行的操作...");
            int select = 0;//接收用户选择的选项.
            select = scanner.nextInt();

            while (select <1 || select >5) {
                System.out.println("选择的操作不能被识别，请重新选择：");
                select = scanner.nextInt();
            }
            if (select>1||select<5){
                System.out.println("正在加载中...");

            }
            String value =null;

            if( select==1) {//添加数据
                System.out.println("请输入要添加的书名、出版商和作者，中间用逗号分隔，举例：红楼梦，chine，曹雪芹");
                value = scanner.next();
                String[] values = value.split(",");
                work.practiceInsertData((int) System.currentTimeMillis(), values[0], values[1],values[2]);
                System.out.println("是否继续加入");
                System.out.println("1.继续   2.取消");
                int count;
                count = Integer.parseInt(scanner.next());

            } else if  (select==2){//修改数据
                System.out.println("请输入您要修改的书名、出版商和作者。逗号分隔。系统将根据id进行数据的更新。id本身不会修改。");
                value = scanner.next();
                String[] values = value.split(",");
             work.practiceUpdateData(Integer.parseInt(values[0]), values[1],values[2],values[3]);
                System.out.println("是否继续修改");
                System.out.println("1.继续   2.取消");
                int count;
                count = Integer.parseInt(scanner.next());

            } else if   (select==3) {//删除数据
                System.out.println("请选择你要删除的id");
                value = scanner.next();
                String[] values = value.split(",");
                work.practiceDeleteData(Integer.parseInt(values[0]));
                System.out.println("是否继续删除");
                System.out.println("1.继续   2.取消");
                int count;
                count = Integer.parseInt(scanner.next());
            } else if (select==4) {//查询所有书籍


                }
            else if   (select==5){//退出系统
                System.exit(0);

            }
    }




}}
//字段名  book_name    book_publishers    book_auther   creat_time





