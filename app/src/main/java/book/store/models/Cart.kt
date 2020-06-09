package book.store.models

data class Cart<T>(
    val products: List<Products>,
    val totalPrice:Float,
    val status:String?
)