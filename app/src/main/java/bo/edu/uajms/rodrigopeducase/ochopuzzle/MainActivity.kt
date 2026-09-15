package bo.edu.uajms.rodrigopeducase.ochopuzzle

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var BTNButtons: Array<Button>
    private lateinit var TXVMessage: TextView
    private lateinit var BTNRestart: Button
    private lateinit var BTNDisorder: Button
    private lateinit var BTNVerify: Button

    private lateinit var Tablero: Array<Array<String>>

    private val rows = 4
    private val cols = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BTNButtons = arrayOf(
            findViewById(R.id.BTN00),
            findViewById(R.id.BTN01),
            findViewById(R.id.BTN02),
            findViewById(R.id.BTN03),

            findViewById(R.id.BTN10),
            findViewById(R.id.BTN11),
            findViewById(R.id.BTN12),
            findViewById(R.id.BTN13),

            findViewById(R.id.BTN20),
            findViewById(R.id.BTN21),
            findViewById(R.id.BTN22),
            findViewById(R.id.BTN23),

            findViewById(R.id.BTN30),
            findViewById(R.id.BTN31),
            findViewById(R.id.BTN32),
            findViewById(R.id.BTN33)
        )

        TXVMessage = findViewById(R.id.TXVMessage)
        BTNRestart = findViewById(R.id.BTNRestart)
        BTNDisorder = findViewById(R.id.BTNShuffle)
        BTNVerify = findViewById(R.id.BTNVerify)

        Tablero = arrayOf(
            arrayOf("1", "2", "3", "4"),
            arrayOf("5", "6", "7", "8"),
            arrayOf("9", "10", "11", "12"),
            arrayOf("13", "14", "15", "0")
        )

        updateBoard()

        for (i in BTNButtons.indices) {
            val row = i / cols
            val col = i % cols

            BTNButtons[i].setOnClickListener {
                moveTile(row, col)
            }
        }
    }

    private fun updateBoard() {
        for (i in BTNButtons.indices) {
            val row = i / cols
            val col = i % cols
            val value = Tablero[row][col]

            if (value == "0") {
                BTNButtons[i].text = ""
            } else {
                BTNButtons[i].text = value
            }
        }
    }

    private fun moveTile(row: Int, col: Int) {
        val blankPosition = findBlank()
        val blankRow = blankPosition.first
        val blankCol = blankPosition.second

        val distance =
            abs(row - blankRow) + abs(col - blankCol)

        if (distance == 1) {
            swapTiles(
                row,
                col,
                blankRow,
                blankCol
            )

            updateBoard()
        }
    }

    private fun findBlank(): Pair<Int, Int> {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (Tablero[i][j] == "0") {
                    return Pair(i, j)
                }
            }
        }

        return Pair(rows - 1, cols - 1)
    }

    private fun swapTiles(
        firstRow: Int,
        firstCol: Int,
        secondRow: Int,
        secondCol: Int
    ) {
        val value = Tablero[firstRow][firstCol]

        Tablero[firstRow][firstCol] =
            Tablero[secondRow][secondCol]

        Tablero[secondRow][secondCol] = value
    }
}