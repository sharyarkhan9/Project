import java.util.Scanner
import scala.collection.mutable.ArrayBuffer
import java.util.Calendar
import java.sql.DriverManager
import java.sql.Connection
import java.io.PrintWriter
import java.io.File

object Project0 {
    def main(arg:Array[String]): Unit = {   
        val driver = "com.mysql.cj.jdbc.Driver"
        val url = "jdbc:mysql://localhost:3306/project"
        val username = "root"
        val password = "F@ith6193" 
        val log = new PrintWriter(new File("query.log"))
        var connection:Connection = null
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)
        val statement = connection.createStatement()   
        var scanner = new Scanner(System.in)
        var choice = 0
        while (choice != 5) {
            println("Pleaes Choose an option using number keys 1 - 5: ")
            println("1: Add a to-do list\n2: Remove a to-do list\n3: Print all to-do lists\n4: Update a to-do list\n5: Exit")
            try {
                choice = scanner.nextInt()
               
            }catch {
                case e: Exception => print("Exception has been thrown. ")
            }
            scanner.nextLine()
                if (choice == 1){
                    println("Please enter an item for you to do list:")
                    var item = scanner.nextLine()
                    var str2 = ""
                    str2 += ("'"+item+"'")
                    statement.executeUpdate("create table project0 (id int NOT NULL AUTO_INCREMENT, todo CHAR(255), PRIMARY KEY (id));")
                    statement.executeUpdate("INSERT INTO project0 (todo) Values ("+str2+")")
                    log.write(Calendar.getInstance().getTimeInMillis + ": Executing - INSERT INTO project0 (todo) Values ("+str2+")\n")
                }
                else if (choice ==2){
                    println("Please enter an id corresponding to the to do list you want removed:")
                    var item = scanner.nextInt()
                    statement.executeUpdate("DELETE FROM project0 WHERE id=("+item+")")
                    log.write(Calendar.getInstance().getTimeInMillis + "- Executing - DELETE FROM project0 WHERE id=("+item+")\n")
                }
                else if (choice ==3){
                    val resultSet = statement.executeQuery("SELECT * FROM project0")
                    while ( resultSet.next() ) {
                        print(resultSet.getString(1) + " " + resultSet.getString(2))
                        println()
                    log.write(Calendar.getInstance().getTimeInMillis + ": Executing - SELECT * FROM project0\n")
                    }
                }
                else if (choice ==4){
                    println("Please enter an id corresponding to the to do list you want to update:")
                    var item = scanner.nextInt()
                    var str = ""
                    scanner.nextLine()
                    println("Please enter the new to-do List: ")
                    var todo = scanner.nextLine()
                    str += ("'"+todo+"'")
                    statement.executeUpdate("update project0 set todo = ("+str+") where id =("+item+")")
                    log.write(Calendar.getInstance().getTimeInMillis + ": Executing - update project0 set todo = ("+str+") where id =("+item+")\n")
              
                }

                else if (choice ==5){
                    println("Your TO-DO list has been saved. Thank you") 
                    connection.close()
                    log.close()
                    
                }
                else {
                    println("Please only enter numbers 1 - 4")
                    
                }
                 
             
        }
    }
}