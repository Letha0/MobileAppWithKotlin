package book.store.requests

data class RegisterRequest (
    val email: String,
    val password: String,
    val passwordConfirmation: String,
    val name: String,
    val surname: String
)