package principal

import service.buscarJogoPorId
import java.util.Scanner

fun main() {
    val leitura = Scanner(System.`in`)
    print("Digite o id do jogo a ser buscado: ")
    val search = leitura.nextLine()

    val resultado = buscarJogoPorId(search)

    resultado.onFailure {
        println("Erro ao buscar o jogo: ${it.message}")
    }

    resultado.onSuccess { game ->
        println("Colocar uma descrição ? S/N")
        val option = leitura.nextLine()
        if (option.equals("s", true)) {
            println("Insira a descrição para o jogo:")
            game.description = leitura.nextLine()
        } else {
            game.description = game.title
        }
        println(game)
    }
}
