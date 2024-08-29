import com.google.gson.annotations.SerializedName

data class SearchHomeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: SearchResult
)

data class SearchResult(
    @SerializedName("performances") val performances: Performances,
    @SerializedName("actors") val actors: Actors
)

data class Performances(
    @SerializedName("performanceCount") val performanceCount: Int,
    @SerializedName("performanceList") val performanceList: List<Performance>
)

data class Performance(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster") val poster: String
)

data class Actors(
    @SerializedName("actorCount") val actorCount: Int,
    @SerializedName("actorList") val actorList: List<Actor>
)

data class Actor(
    @SerializedName("id") val id: Int,
    @SerializedName("actorName") val actorName: String,
    @SerializedName("actorProfile") val actorProfile: String,
    @SerializedName("isFavoriteActor") var isFavoriteActor: String
)
