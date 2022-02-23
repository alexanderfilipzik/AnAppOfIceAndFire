package de.mobilecompass.anappoficeandfire.modules.houses.network

import de.mobilecompass.anappoficeandfire.modules.houses.network.models.HousesData
import de.mobilecompass.anappoficeandfire.modules.houses.network.models.HousesRemoteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Headers
import javax.inject.Inject

class HousesRemoteDataSourceImpl @Inject constructor(
    private val housesApi: HousesApi
) : HousesRemoteDataSource {

    // ----------------------------------------------------------------------------
    // region Inner types
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    companion object {
        // ----------------------------------------------------------------------------
        // region Constants
        // ----------------------------------------------------------------------------

        val LOG_TAG: String = HousesRemoteDataSourceImpl::class.java.simpleName

        // ----------------------------------------------------------------------------
        // endregion
        // ----------------------------------------------------------------------------

        // ----------------------------------------------------------------------------
        // region Convenience methods
        // ----------------------------------------------------------------------------

        // ----------------------------------------------------------------------------
        // endregion
        // ----------------------------------------------------------------------------
    }

    // ----------------------------------------------------------------------------
    // region Public properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Internal properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Protected properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Private properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Init
    // ----------------------------------------------------------------------------

    init {

    }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region System/Overridden methods
    // ----------------------------------------------------------------------------

    override suspend fun getHouses(url: String): Result<HousesData> =
        withContext(Dispatchers.IO) {
            val housesResponse = housesApi.getHousesByURL(url)
            val housesList = housesResponse.body()

            if (!housesResponse.isSuccessful || housesList.isNullOrEmpty()) {
                return@withContext Result.failure(HousesRemoteException(housesResponse.message()))
            }

            val headers = housesResponse.headers()
            val linkHeaderEntries = getLinkHeaderValues(headers)

            val previousUrl = linkHeaderEntries?.firstNotNullOfOrNull {
                if (it.key == "prev") it.value else null
            }

            val nextUrl = linkHeaderEntries?.firstNotNullOfOrNull {
                if (it.key == "next") it.value else null
            }

            Result.success(HousesData(housesList, previousUrl, nextUrl))
        }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Public methods
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Protected methods
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Private methods
    // ----------------------------------------------------------------------------

    /**
     * Tries to parse the link header and map the entries into a map.
     * This map should consist of the "prev", "next", "current" keys with their
     * corresponding values.
     *
     * @param headers the headers of the response
     *
     * @return the parsed map of the link header
     */
    private fun getLinkHeaderValues(headers: Headers): Map<String, String>? {
        val linkHeader = headers["link"]
        val linkHeaderRegExString = ".*<(.+)>;.*rel=\"(.+)\".*"
        val linkHeaderRegEx = Regex(linkHeaderRegExString)
        val linkHeaderEntries = linkHeader?.split(",")
        val parsedLinkHeaderEntries = linkHeaderEntries
            ?.map {
                val match = linkHeaderRegEx.find(it) ?: return@map null
                val (url, linkType) = match.destructured
                linkType to url
            }
            ?.filterNotNull()
            ?.toMap()

        return parsedLinkHeaderEntries
    }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Interfaces/Listener
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

}