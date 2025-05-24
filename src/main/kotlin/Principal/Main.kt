package Principal

import model.Game
import com.google.gson.Gson
import model.InfoGame
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.util.Scanner

fun main() {
    val leitura = Scanner(System.`in`)
    print("Digite o id do jogo a ser buscado: ")
    val search = leitura.nextLine()
    val url = "https://www.cheapshark.com/api/1.0/games?id=$search"

    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder().uri(URI.create(url)).build()

    val resultado = runCatching {
        val response = client.send(request, BodyHandlers.ofString())
        val json = response.body()

        if (json.trim().startsWith("[")) {
            throw IllegalStateException("Nenhum jogo encontrado com esse ID.")
        }

        val gson = Gson()
        val infoGame = gson.fromJson(json, InfoGame::class.java)

        val game = Game(
            title = infoGame.info.title,
            cover_art = infoGame.info.thumb,
            lower_price = infoGame.cheapestPriceEver.price,
            actual_price = infoGame.steamPrice,
        )
        game
    }


    resultado.onFailure {
            println("Erro ao buscar o jogo: ${it.message}")
    }

    resultado.onSuccess { game ->
        println("Colocar uma descrição ? S/N")
        val option = leitura.nextLine()
        if(option.equals("s",true)){
            println("Insira a descrição para o jogo:")
            game.description = leitura.nextLine()
            println(game)
        }else{
            game.description = game.title
            println(game)
        }
    }
}