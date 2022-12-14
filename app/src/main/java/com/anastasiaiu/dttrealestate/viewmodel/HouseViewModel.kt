package com.anastasiaiu.dttrealestate.viewmodel

import android.location.Location
import androidx.lifecycle.*
import com.anastasiaiu.dttrealestate.DttRealEstateApplication
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.fragments.FavouritesFragment
import com.anastasiaiu.dttrealestate.view.fragments.HouseDetailFragment
import com.anastasiaiu.dttrealestate.view.fragments.OverviewFragment
import com.anastasiaiu.dttrealestate.view.utilities.HouseApiStatus
import com.anastasiaiu.dttrealestate.viewmodel.ViewModelConstants.FORMAT_ZIP_NEW_VALUE
import com.anastasiaiu.dttrealestate.viewmodel.ViewModelConstants.FORMAT_ZIP_OLD_VALUE
import com.anastasiaiu.dttrealestate.viewmodel.ViewModelConstants.SEARCH_DELIMITER
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The shared [HouseViewModel] holds data and methods for [OverviewFragment],
 * [FavouritesFragment] and [HouseDetailFragment].
 */
class HouseViewModel(private val application: DttRealEstateApplication) : ViewModel() {

    /**
     * The MutableLiveData of the status of the most recent request.
     */
    val status: MutableLiveData<HouseApiStatus> by lazy { MutableLiveData<HouseApiStatus>() }

    /**
     * The LiveData of the list of house objects from the database.
     */
    val housesList = application.repository.getAllHousesFromDatabase().asLiveData()

    /**
     * The LiveData of the list of bookmarked houses objects from the database.
     */
    val bookmarkedHousesList = application.repository.getAllBookmarkedHouses().asLiveData()

    /**
     * The MutableLiveData of the query string from the search field.
     */
    val searchQuery: MutableLiveData<String> by lazy { MutableLiveData() }

    /**
     * The MutableLiveData of the list of house objects from the database search.
     */
    val searchHousesList: MutableLiveData<List<House>> by lazy { MutableLiveData() }

    /**
     * The MutableLiveData to track if the empty state needed to be shown.
     */
    val emptyState: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    /**
     * The house to be displayed in the details screen.
     */
    lateinit var currentHouse: House

    /**
     * Latitude of the device.
     */
    var deviceLatitude: Double? = null

    /**
     * Longitude of the device.
     */
    var deviceLongitude: Double? = null

    init {
        getHouses()
        setDeviceLocation()
    }

    /**
     * Gets houses from the server.
     */
    private fun getHouses() {

        viewModelScope.launch(Dispatchers.IO) {

            status.postValue(HouseApiStatus.LOADING)

            try {

                application.repository.apply { addAllHousesToDatabase(getHousesFromServer()) }

                status.postValue(HouseApiStatus.SUCCESS)

                formatZipCodes()

            } catch (e: Exception) {

                status.postValue(HouseApiStatus.ERROR)
            }
        }
    }

    /**
     * Formats zip codes of all houses in the database to be in the format 1034BN.
     */
    private suspend fun formatZipCodes() {

        val houses = application.repository.getAllHousesFromDatabaseList()

        houses.forEach { house ->

            house.zip = house.zip.replace(FORMAT_ZIP_OLD_VALUE, FORMAT_ZIP_NEW_VALUE)

            application.repository.addHouseToDatabase(house)
        }
    }

    /**
     * Sets the last known position of the device.
     */
    private fun setDeviceLocation() {

        val fusedLocationClient = LocationServices
            .getFusedLocationProviderClient(application.applicationContext)

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            location?.let {
                deviceLatitude = location.latitude
                deviceLongitude = location.longitude
            }
        }
    }

    /**
     * Sets the [house] object that will be displayed at the details fragment.
     */
    fun applyCurrentHouse(house: House) {
        currentHouse = house
    }

    /**
     * Inserts the [house] to the database with a changed bookmark state.
     */
    fun addBookmarkedHouseToDatabase(house: House) {
        viewModelScope.launch(Dispatchers.IO) { application.repository.addHouseToDatabase(house) }
    }

    /**
     * Searches for houses with [searchValue] at the database
     * and posts results to the [HouseViewModel].
     */
    suspend fun search(searchValue: String) {

        // List of values to search.
        val searchValueList = mutableListOf<String>()

        // Result list of the search.
        val searchResultList = mutableListOf<House>()

        searchValueList.apply {

            // Add all values to be searched.
            addAll(searchValue.split(SEARCH_DELIMITER))

            forEachIndexed { index, string ->

                if (string.isNotBlank()) {

                    if (index == 0) {

                        // Add search result for the first string to the results.
                        searchResultList.addAll(
                            application.repository.searchAtDatabase(string)
                        )

                    } else {

                        // Search result for the current string.
                        val currentSearchResult = application.repository.searchAtDatabase(string)

                        // List of result of already performed searches.
                        val previousSearchResult = mutableListOf<House>()

                        previousSearchResult.apply {

                            // Add all existed values.
                            addAll(searchResultList)

                            // Remove all not matching results.
                            forEach { house ->
                                if (!currentSearchResult.contains(house)) {
                                    searchResultList.remove(house)
                                }
                            }
                        }
                    }
                }
            }
        }

        searchHousesList.postValue(searchResultList)
    }
}

/**
 * Custom [ViewModelProvider.Factory] interface to instantiate [HouseViewModel].
 */
class HouseViewModelFactory(private val application: DttRealEstateApplication) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HouseViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return HouseViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}