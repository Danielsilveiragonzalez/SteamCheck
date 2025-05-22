import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers


fun main() {


    val client: HttpClient = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://www.cheapshark.com/api/1.0/games?id=100"))
        .build()
    val response = client.send(request, BodyHandlers.ofString())
    val json = response.body()

    //println(json)

    val gson = Gson()
    val myInfoGame = gson.fromJson(json, InfoGame::class.java)


    val myGame = Game(
        title = myInfoGame.info.title,
        cover_art = myInfoGame.info.thumb,
        lower_price = myInfoGame.cheapestPriceEver.price,
        actual_price = myInfoGame.steamPrice
    )

    println(myGame)

}
