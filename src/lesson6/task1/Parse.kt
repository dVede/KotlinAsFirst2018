@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence", "UNUSED_EXPRESSION")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    val months = mapOf("января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4, "мая" to 5, "июня" to 6,
            "июля" to 7, "августа" to 8, "сентября" to 9, "октября" to 10, "ноября" to 11, "декабря" to 12)
    val year = parts[2].toIntOrNull()
    val month = months[parts[1]]
    val day = parts[0].toIntOrNull()
    if (day == null || year == null || month == null) return ""
    if ((day !in 1..daysInMonth(month, year)) || (month !in 1..12) || (parts.size != 3)) return ""
    return String.format("%02d.%02d.%d", day, month, year)
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря")
    val year = parts[2].toIntOrNull()
    val monthInt = parts[1].toIntOrNull()
    val day = parts[0].toIntOrNull()
    var monthStr: String
    if (year == null || monthInt == null || day == null) return ""
    if ((day !in 1..daysInMonth(monthInt, year)) || (monthInt !in 1..12) || (parts.size != 3)) return ""
    monthStr = months[monthInt - 1]
    return String.format("%d %s %d", day, monthStr, year)
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String =
        if (phone.filter { it != ' ' && it != '-' }.matches(Regex("""(\+\d*)?(\(\d+\))?\d*""")))
            Regex("""\(|\)|-| """).replace(phone, "")
        else ""


/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int =
        if (jumps.contains(Regex("""[^\d\s-%]"""))) -1
        else jumps.split(" ").filter { Regex("""\d+""").matches(it) }.map { it.toInt() }.max() ?: -1


/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if ((jumps.contains(Regex("""[^\d\s-%+]"""))) || (jumps.isEmpty())) return -1
    val jump = jumps.split(" ")
    val high = mutableListOf<String>()
    (0 until jump.size step 2).forEach { i ->
        if ((jump[i + 1].contains("+"))) {
            high.add(jump[i])
        }
    }
    return high.map { it.toInt() }.max() ?: -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    return if (Regex("""\d+(?:[\s]*[-+][\s]*\d+)*""").matches(expression))
        expression.replace(" ", "")
                .split(Regex("""(?=[-+])"""))
                .map { it.toInt() }
                .sum()
    else
        throw IllegalArgumentException()
}


/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val strSpl = str.toLowerCase().split(" ")
    var position = 0
    (1 until strSpl.size).forEach { i ->
        if (strSpl[i] == strSpl[i - 1]) return position
        else position += strSpl[i - 1].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (!Regex("""(?:\S+ \d+(?:\.\d+)?)(?:; \S+ \d+(?:\.\d+)?)*""").matches(description))
        return ""
    var name = ""
    var price = -1.0
    description.split(Regex("; ")).map { it.split(Regex(" ")) }.forEach { it ->
        if (it[1].toDouble() > price) {
            price = it[1].toDouble()
            name = it[0]
        }
    }
    return name
}


/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val romanToInt = mapOf("I" to 1, "IV" to 4, "V" to 5, "IX" to 9, "X" to 10, "XL" to 40,
            "L" to 50, "XC" to 90, "C" to 100, "CD" to 400, "D" to 500, "CM" to 900, "M" to 1000)
    if ((Regex("[^MCDXLIV]").containsMatchIn(roman)) || roman.isEmpty()) return -1
    return Regex("CM|CD|XC|XL|IX|IV|I|V|X|L|C|D|M").findAll(roman)
            .map { romanToInt[it.value] }.sumBy { it ?: 0 }

}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val commandsList = commands.split(Regex("")).filter { (it != "") }
    val end = mutableListOf<Int>()
    var commandsDone = 0
    if (commandsList.count { it == "]" } != commandsList.count { it == "[" }) throw IllegalArgumentException()
    var position = cells / 2
    for (j in 0 until cells) end.add(0)
    var j = 0
    while ((commandsDone < limit) && (commandsList.isNotEmpty())) {
        when (commandsList[j]) {
            ">" -> position++
            "<" -> position--
            "+" -> end[position]++
            "-" -> end[position]--
            " " -> ""
            "[" -> if (end[position] == 0) {
                var bc1 = 1
                while (bc1 > 0) {
                    j++
                    when (commandsList[j]) {
                        "[" -> bc1++
                        "]" -> if (bc1 > 0) bc1--
                    }
                }
            }
            "]" -> if (end[position] != 0) {
                var bc2 = 1
                while (bc2 > 0) {
                    j--
                    when (commandsList[j]) {
                        "]" -> bc2++
                        "[" -> if (bc2 > 0) bc2--
                    }
                }
            }
            else -> throw IllegalArgumentException()
        }
        j++
        if ((position >= cells) || (position < 0)) throw IllegalStateException()
        if (j == commandsList.size) break
        commandsDone++
    }

    return end
}

/**
 *ОЧЕНЬ СЛОЖНОЕ ТЕСТИРОВАНИЕ
 *
 *
 */

fun main2(a: Complex, b: Complex, c: Complex): String {
    val multiply: Complex = a * b * c
    println("x * y =  ${a * b * c}")
    return multiply.toString()

}


fun complex(str: String): String {
    val a = mutableListOf<Pair<Double, Double>>()
    val input = str.split(";")
    (0 until input.size)
            .forEach { i ->
                if (!Regex("""([-]?\d+ [+-] \d*i)||([-]?\d+)||([-]?\d*i)""").matches(input[i]))
                    throw Exception()
            }
    (0 until input.size).forEach { i ->
        if (input[i].split(" ").size == 1) {
            a += if ("i" in input[i]) {
                when {
                    input[i] == "i" -> 0.0 to 1.0
                    input[i] == "-i" -> 0.0 to -1.0
                    else -> 0.0 to input[i].filter { it != 'i' }.toDouble()
                }
            } else
                input[i].toDouble() to 0.0
        } else {
            val sp = input[i].split(" ")
            a += sp[0].toDouble() to when {
                sp[1] + sp[2] == "+i" -> 1.0
                sp[1] + sp[2] == "-i" -> -1.0
                else -> (sp[1] + sp[2].filter { it != 'i' }).toDouble()
            }
        }
    }
    return complexTime(a).toString()
}

fun complexTime(str: List<Pair<Double, Double>>): Complex {
    var times = Complex(str[0].first, str[0].second)
    for (i in 1 until str.size) {
        times *= Complex(str[i].first, str[i].second)
    }
    return times
}

class Complex(private val real: Double, private val imag: Double) {
    operator fun times(other: Complex) = Complex(
            real * other.real - imag * other.imag,
            real * other.imag + imag * other.real
    )

    override fun toString() =
            if (imag >= 0.0) "$real + ${imag}i"
            else "$real - ${-imag}i"
}

