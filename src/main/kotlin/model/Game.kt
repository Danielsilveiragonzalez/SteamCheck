package model

class Game(
    val title: String,
    val cover_art: String,
    val lower_price: String,
    val actual_price: String,
    var description: String = ""
) {
    override fun toString(): String {
        return "Game: \n" +
                " title= $title \n" +
                " Cover_art= $cover_art \n" +
                " lower_price= U$ $lower_price \n" +
                " actual_price= U$ $actual_price \n" +
                " description= $description"
    }
}
