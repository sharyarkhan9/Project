import java.util.Scanner
import scala.collection.mutable.ArrayBuffer
import java.util.Calendar
import java.sql.DriverManager
import java.sql.Connection

object Project0 {
    def main(arg:Array[String]): Unit = {   
        val driver = "com.mysql.cj.jdbc.Driver"
        val url = "jdbc:mysql://localhost:3306/project"
        val username = "root"
        val password = "F@ith6193" 
        var connection:Connection = null
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)
        val statement = connection.createStatement()   
        var scanner = new Scanner(System.in)
        var choice = 0
        var arrBuff = new ArrayBuffer[String]()
        while (choice != 4) {
            println("Pleaes Choose an option using number keys 1 - 4: ")
            println("1: Add a to-do list\n2: Remove a list\n3: Print all to do lists\n4: Update a to do list\n5: Exit")
            try {
                choice = scanner.nextInt()
               
            }catch {
                case e: Exception => print("Exception has been thrown. A non numeric key was entered. ")
            }
            scanner.nextLine()
                if (choice == 1){
                    println("Please enter an item for you to do list:")
                    var item = scanner.nextLine()
                    arrBuff += item
                }
                else if (choice ==2){
                    arrBuff.foreach(println)
                    println("Please enter an id corresponding to the to do list you want removed:")
                    var item = scanner.nextInt()
                    statement.executeUpdate("DELETE FROM project0 WHERE id=("+item+")")
                    log.write(Calendar.getInstance().getTimeInMillis + "- Executing - 'DELETE FROM project0 WHERE id=("+item+")'\n")
                }
                else if (choice ==3){
                    val resultSet = statement.executeQuery("SELECT * FROM project0")
                    while ( resultSet.next() ) {
                        print(resultSet.getString(1) + " " + resultSet.getString(2))
                        println()
                    }
                }
                else if (choice ==4){
                    println("Please enter an id corresponding to the to do list you want to update:")
                    var todo = scanner.nextLine()
                    var str = ""
                    str += ("'"+todo+"'")
                    println("Please enter an id corresponding to the to do list you want to update:")
                    var item = scanner.nextInt()
                    scanner.nextLine()
                    statement.executeUpdate("update project0 set todo = ("+str+") where id =("+item+")")
              
                }

                else if (choice ==5){
                    println("Your TO-DO list has been saved. Thank you")
                    
                    var str = ""
                    for (x <- 0 until arrBuff.length) {
                    var quer = ("'"+arrBuff(x)+"'")
                    str += quer
                    //statement.executeUpdate("create table project0 (id int NOT NULL AUTO_INCREMENT, todo CHAR(255), PRIMARY KEY (id));")
                    statement.executeUpdate("INSERT INTO project0 (todo) Values ("+str+")")
                    val resultSet = statement.executeQuery("SELECT * FROM project0")
                } 
                    connection.close()
                }
                else {
                    println("Please only enter numbers 1 - 4")
                    
                }
                 
             
        }
    }
}