// 1. Data class model dengan 4 properti dan 1 properti nullable (discountCode)
data class Course(
    val id: Int,
    val title: String,
    val price: Double,
    val isAvailable: Boolean,
    val discountCode: String?
)

// 2. Fungsi validasi input harga dan kupon
fun validateAndCalculateFinalPrice(course: Course): Double {
    require(course.price >= 0) { "Harga tidak boleh negatif" }
    
    // Penanganan null safety dengan Safe Call (?.) dan Elvis Operator (?:)
    val discountPercent = when (course.discountCode?.uppercase()) {
        "HEMAT10" -> 10
        "SUPER20" -> 20
        else -> 0
    }
    
    return course.price * (100 - discountPercent) / 100.0
}

fun main() {
    // 3. Dataset minimal 8 item
    val courses = listOf(
        Course(1, "Kotlin Essentials", 150000.0, true, "SUPER20"),
        Course(2, "Jetpack Compose Basic", 200000.0, true, null),
        Course(3, "Android State Management", 180000.0, false, "HEMAT10"),
        Course(4, "Room Database Local", 120000.0, true, "HEMAT10"),
        Course(5, "REST API Integration", 250000.0, true, null),
        Course(6, "Advanced Clean Architecture", 300000.0, false, "SUPER20"),
        Course(7, "Git & Github Workflow", 80000.0, true, "HEMAT10"),
        Course(8, "Unit Testing in Android", 100000.0, true, null)
    )

    println("=== DAFTAR COURSE TERSEDIA (URUT HARGA TERMURAH) ===")

    // 4. Processing Pipeline: Filter, Sort, Transform (Map)
    val processedCourses = courses
        .filter { it.isAvailable } // Filter status tersedia
        .sortedBy { validateAndCalculateFinalPrice(it) } // Sort berdasarkan harga akhir
        .map { course ->
            val finalPrice = validateAndCalculateFinalPrice(course)
            val promoInfo = course.discountCode?.let { "Kupon: $it" } ?: "Tanpa Kupon"
            "${course.title} | Rp${finalPrice.toInt()} ($promoInfo)"
        }

    // Output terformat
    processedCourses.forEach { println("- $it") }
}
