import com.example.unihub.uniclub.domain.CategoryModel
import kotlinx.serialization.Serializable


@Serializable
data class ProductModel(
    val id: Int,
    val name: String,
    val link: String,
    val price: Double,
    val categories: List<String>
)