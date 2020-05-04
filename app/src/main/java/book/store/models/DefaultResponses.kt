package book.store.models

import java.io.ObjectOutput
import java.util.*

data class RegisterResponse(val status: String, val errors: ObjectOutput)