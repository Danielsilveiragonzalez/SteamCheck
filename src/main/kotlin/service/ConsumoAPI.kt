package service

import com.google.gson.Gson
import model.Game
import model.InfoGame
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun buscarJogoPorId(id: String): Result<Game> {
    return runCatching { fazerRequisicao(id) }
}

private fun fazerRequisicao(id: String): Game {
    val url = "https://www.cheapshark.com/api/1.0/games?id=$id"
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    val json = response.body()

    if (json.trim().startsWith("[")) {
        throw IllegalStateException("Nenhum jogo encontrado com esse ID.")
    }

    val gson = Gson()
    val infoGame = gson.fromJson(json, InfoGame::class.java)

    return Game(
        title = infoGame.info.title,
        cover_art = infoGame.info.thumb,
        lower_price = infoGame.cheapestPriceEver.price,
        actual_price = infoGame.steamPrice
    )
}
