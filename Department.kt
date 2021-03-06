abstract class Employee (
    val firstName: String,
    val secondName: String,
    val baseSalary: Double,
    val experience: Int,
    var manager: Manager ? = null
) {
    open fun calcSalary () : Double {
        var salary: Double
        if (experience > 5) {
            salary =  baseSalary*1.2 + 500
        } else if (experience > 2) {
            salary =  baseSalary + 200
        } else {
            salary =  baseSalary
        }
        return salary;
    }

    fun giveSalary () {
        println("$firstName $secondName got salary: ${ Math.round(calcSalary()).toDouble() }")
    }

    fun assignToManager(assignedManager : Manager) {
        manager = assignedManager
        assignedManager.team.add(this)
    }
}

class Developer (
    firstName: String,
    secondName: String,
    baseSalary: Double,
    experience: Int
) : Employee (firstName, secondName, baseSalary, experience)  {

}

class Designer (
    firstName: String,
    lastName: String,
    baseSalary: Double,
    experience: Int,
    var effCoeff : Double
) : Employee (firstName, lastName, baseSalary, experience)  {
    override fun calcSalary() : Double {
        return super.calcSalary() * effCoeff
    }
}



class Manager (
    firstName: String,
    lastName: String,
    baseSalary: Double,
    experience: Int,
    var team : MutableList<Employee> = mutableListOf()
) : Employee (firstName, lastName, baseSalary, experience)  {
    override fun calcSalary() : Double {
        var salary = super.calcSalary()
        val teamNum = team.count()
        val devNum = team.filterIsInstance<Developer>().count()
        if (teamNum > 10) {
            salary = salary + 300
        } else if (teamNum > 5) {
            salary = salary + 200
        }
        if (devNum > teamNum / 2) {
            salary = salary * 1.1
        }
        return salary;
    }

    fun addEmployee (employee : Employee) {
        team.add(employee)
        employee.manager = this
    }
}



class Department (
    var managers : MutableList<Manager> = mutableListOf()
) {

    fun addManager(manager: Manager) {
        managers.add(manager)
    }

    fun giveAllSalaries() {
        managers.forEach{
                manager ->
            manager.giveSalary()
            manager.team.forEach{
                    employee ->  employee.giveSalary()
            }
        }
    }
}



fun main () {
    var Dev1 =  Developer("Give","You",1400.00, 1);
    var Dev2 =  Developer("Up", "Never",1500.00, 2);
    var Dev3 =  Developer("Gonna","Let",1500.00, 3)
    var Dev4 =  Developer("You","Down",1500.00, 7);
    var Dev5 =  Developer("Never","Gonna",1500.00, 5);
    var Dev6 =  Developer("Gonna","Make",1400.00, 4);
    var Dev7 =  Developer("You","Cry",1500.00, 4);

    var Des1 =  Designer("Run","Around",1400.00, 2, 0.6);
    var Des2 =  Designer("You","Never",1400.00, 1, 0.7);
    var Des3 =  Designer("Never","Gonna",1400.00, 4, 0.8);
    var Des4 =  Designer("Say","Goodbye",1400.00, 6, 0.9);

    var Man1 =  Manager("Never","Gonna",2000.00, 6);
    var Man2 =  Manager("And","Desert",2000.00, 4);

    Dev1.assignToManager(Man1)
    Dev2.assignToManager(Man1)
    Dev3.assignToManager(Man1)
    Dev4.assignToManager(Man1)
    Dev5.assignToManager(Man1)
    Des1.assignToManager(Man1)
    Des2.assignToManager(Man2)

    Dev6.assignToManager(Man2)
    Dev7.assignToManager(Man2)
    Des3.assignToManager(Man2)
    Des4.assignToManager(Man2)

    var production = Department()

    production.addManager(Man1)
    production.addManager(Man2)

    production.giveAllSalaries()

}