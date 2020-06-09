package book.store.models

data class Cart<T>(
    val products: List<Products>,
    val totalPrice:Int,
    val status:String?
)