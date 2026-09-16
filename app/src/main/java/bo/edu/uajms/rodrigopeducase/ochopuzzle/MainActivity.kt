package bo.edu.uajms.rodrigopeducase.ochopuzzle

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var BTNButtons: Array<Button>
    private lateinit var TXVMessage: TextView
    private lateinit var BTNRestart: Button
    private lateinit var BTNDisorder: Button
    private lateinit var BTNVerify: Button

    private lateinit var Tablero: Array<Array<String>>

    private val estadoFinal = arrayOf(
        arrayOf("1", "2", "3", "4"),
        arrayOf("12", "13", "14", "5"),
        arrayOf("11", "0", "15", "6"),
        arrayOf("10", "9", "8", "7")
    )

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

        restaurarMatriz()
        mostrarMatriz()

        BTNButtons.forEachIndexed { posicion, boton ->
            boton.setOnClickListener {
                moverFicha(posicion)
            }
        }

        BTNRestart.setOnClickListener {
            restaurarJuego()
        }

        BTNDisorder.setOnClickListener {
            mezclarJuego()
        }

        BTNVerify.setOnClickListener {
            comprobarJuego()
        }
    }

    private fun restaurarMatriz() {
        Tablero = Array(4) { fila ->
            Array(4) { columna ->
                estadoFinal[fila][columna]
            }
        }
    }

    private fun mostrarMatriz() {
        BTNButtons.forEachIndexed { posicion, boton ->
            val fila = posicion / 4
            val columna = posicion % 4
            val valor = Tablero[fila][columna]

            boton.text = if (valor == "0") {
                ""
            } else {
                valor
            }
        }
    }

    private fun moverFicha(posicion: Int) {
        val fila = posicion / 4
        val columna = posicion % 4

        val posicionesVecinas = arrayOf(
            Pair(fila - 1, columna),
            Pair(fila + 1, columna),
            Pair(fila, columna - 1),
            Pair(fila, columna + 1)
        )

        for (vecino in posicionesVecinas) {
            val filaVecina = vecino.first
            val columnaVecina = vecino.second

            if (
                filaVecina in 0..3 &&
                columnaVecina in 0..3 &&
                Tablero[filaVecina][columnaVecina] == "0"
            ) {
                val ficha = Tablero[fila][columna]

                Tablero[fila][columna] = "0"
                Tablero[filaVecina][columnaVecina] = ficha

                mostrarMatriz()
                return
            }
        }
    }

    private fun restaurarJuego() {
        restaurarMatriz()
        mostrarMatriz()
        TXVMessage.text = "Juego Reiniciado"
    }

    private fun mezclarJuego() {
        val elementos = mutableListOf<String>()

        for (fila in Tablero) {
            for (valor in fila) {
                elementos.add(valor)
            }
        }

        for (posicion in elementos.lastIndex downTo 1) {
            val posicionAleatoria = Random.nextInt(posicion + 1)

            val auxiliar = elementos[posicion]
            elementos[posicion] = elementos[posicionAleatoria]
            elementos[posicionAleatoria] = auxiliar
        }

        var posicion = 0

        for (fila in 0..3) {
            for (columna in 0..3) {
                Tablero[fila][columna] = elementos[posicion]
                posicion++
            }
        }

        mostrarMatriz()
        TXVMessage.text = "Completado"
    }

    private fun comprobarJuego() {
        TXVMessage.text = if (Tablero.contentDeepEquals(estadoFinal)) {
            "Juego Ordenado"
        } else {
            "Juego Desordenado"
        }
    }
}


