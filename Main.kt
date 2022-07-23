package tictactoe

class TicTacToe(private var board: String = "".padEnd(9)) {
    private val win = { c: String -> "012345678036147258048246".map { it.digitToInt() }.chunked(3).map { board.slice(it) }.count { it == c } }

    private fun free(pos: Pair<Int, Int>) = board[(pos.first - 1) * 3 + pos.second - 1] == ' '

    fun turn(p: Int) {
        val pl: Pair<Int, Int>
        while (true) {
            val c = readln().split(" ")
            when {
                c.size != 2 || !Regex("\\d").matches(c.first()) || !Regex("\\d").matches(c.last()) -> println("You should enter numbers!")
                c.first().toInt() !in 1..3 || c.last().toInt() !in 1..3 -> println("Coordinates should be from 1 to 3!")
                !free(Pair(c.first().toInt(), c.last().toInt())) -> println("This cell is occupied! Choose another one!")
                else -> { pl = Pair(c.first().toInt(), c.last().toInt()); break }
            }
        }
        board = board.take((pl.first - 1) * 3 + pl.second - 1) + listOf('X', 'O')[p % 2] + board.takeLast(9 - (pl.first - 1) * 3 - pl.second)
    }

    fun gameOver(): Boolean {
        return when {
            win("XXX") > 0 && win("OOO") == 0 -> { println("X wins"); true }
            win("XXX") == 0 && win("OOO") > 0 -> { println("O wins"); true }
            board.count { it == ' ' } == 0 -> { println("Draw"); true }
            else -> false
        }
    }

    override fun toString() = "---------\n|${board.chunked(3).joinToString("|\n|") { it.replace("", " ") }}|\n---------"
}

fun main() {
    val game = TicTacToe()
    println(game)
    var player = 0
    while (!game.gameOver()) {
        game.turn(player++)
        println(game)
    }
}