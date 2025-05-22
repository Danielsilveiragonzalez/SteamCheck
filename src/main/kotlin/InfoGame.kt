data class InfoGame(
    val info: InfoApiShark,
    val cheapestPriceEver: CheapestPriceEver,
    val deals: List<Deal>
) {
    val steamPrice: String
        get() = deals.firstOrNull { it.storeID == "1" }?.price ?: ""
}
