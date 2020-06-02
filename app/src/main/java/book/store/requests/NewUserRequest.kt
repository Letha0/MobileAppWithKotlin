package book.store.requests

data class NewUserRequest(
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val roles: String
)