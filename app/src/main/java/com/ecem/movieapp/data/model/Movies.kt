package com.ecem.movieapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(tableName = "movies")
data class Movies(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int? = null,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String? = null,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String? = null,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double? = null
): Parcelable