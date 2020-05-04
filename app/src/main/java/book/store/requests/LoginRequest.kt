package book.store.requests

data class LoginRequest (
    val email: String,
    val password: String
)