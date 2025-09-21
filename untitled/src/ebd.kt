import kotlin.random.Random
import kotlin.math.cos
import kotlin.math.sin
import kotlin.system.measureTimeMillis

data class Point(var x: Double, var y: Double)

class Human(var fullName: String, var age: Int, var currentSpeed: Double)
{
    private var position = Point(0.0, 0.0)

    fun move() {
        // Генерируем случайный угол для движения
        val angle: Double = Random.nextDouble(0.0, 2 * Math.PI)
        // Обновляем позицию в зависимости от текущей скорости
        position.x += currentSpeed * cos(angle)
        position.y += currentSpeed * sin(angle)
    }

    fun get_Position(): Point {
        return position
    }

    // Геттеры и сеттеры
    fun get_FullName(): String {
        return fullName
    }

    fun set_FullName(name: String) {
        fullName = name
    }

    fun get_Age(): Int {
        return age
    }

    fun set_Age(age: Int) {
        this.age = age
    }

    fun get_CurrentSpeed(): Double {
        return currentSpeed
    }

    fun set_CurrentSpeed(speed: Double) {
        currentSpeed = speed
    }
}

fun main() {
    val humans = Array(17) {
        Human("Human ${it + 1}", Random.nextInt(18, 60), Random.nextDouble(1.0, 5.0))
    }

    val simulationTime = 10_000 // 10 секунд в миллисекундах
    val timeStep = 100 // шаг времени в миллисекундах

    val totalIterations: Int = simulationTime / timeStep

    // Запускаем симуляцию
    val elapsedTime: Long = measureTimeMillis()
    {
        for (iteration in 1..totalIterations)
        {
            // Каждый человек делает шаг
            for (human in humans)
            {
                human.move()
                val position = human.get_Position()
                println("${human.get_FullName()} (Возраст: ${human.get_Age()}, Скорость: ${human.get_CurrentSpeed()}) " +
                        "текущая позиция: (${position.x}, ${position.y})")
            }
            Thread.sleep(timeStep.toLong())
        }
    }

    println("Симуляция завершена за $elapsedTime мс.")
}
