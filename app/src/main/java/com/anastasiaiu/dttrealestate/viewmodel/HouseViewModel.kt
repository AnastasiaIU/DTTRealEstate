package com.anastasiaiu.dttrealestate.viewmodel

import android.location.Location
import androidx.lifecycle.*
import com.anastasiaiu.dttrealestate.DttRealEstateApplication
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.fragments.FavouritesFragment
import com.anastasiaiu.dttrealestate.view.fragments.HouseDetailFragment
import com.anastasiaiu.dttrealestate.view.fragments.OverviewFragment
import com.anastasiaiu.dttrealestate.view.utilities.HouseApiStatus
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

// Amsterdam coordinates as the default location of the device.
private const val DEFAULT_LATITUDE = 52.3547925
private const val DEFAULT_LONGITUDE = 4.7638773

/**
 * The shared [HouseViewModel] holds data and methods for [OverviewFragment],
 * [FavouritesFragment] and [HouseDetailFragment].
 */
class HouseViewModel(private val application: DttRealEstateApplication) : ViewModel() {

    // The MutableLiveData of the status of the most recent request.
    val status: MutableLiveData<HouseApiStatus> by lazy { MutableLiveData<HouseApiStatus>() }

    // The LiveData of the list of houses objects from the database.
    val housesList = application.repository.getAllHousesFromDatabase().asLiveData()

    // The LiveData of the list of bookmarked houses objects from the database.
    val bookmarkedHousesList = application.repository.getAllBookmarkedHouses().asLiveData()

    // The MutableLiveData of the query string from the search field.
    val searchQuery: MutableLiveData<String> by lazy { MutableLiveData() }

    // The MutableLiveData of the list of houses objects from the database search.
    val searchHousesList: MutableLiveData<List<House>> by lazy { MutableLiveData() }

    // The MutableLiveData to track if the empty state needed to be shown.
    val emptyState: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    // The house to be displayed in the details screen.
    lateinit var currentHouse: House

    // Latitude of the device. Set to default value initially.
    var deviceLatitude = DEFAULT_LATITUDE

    // Longitude of the device. Set to default value initially.
    var deviceLongitude = DEFAULT_LONGITUDE

    init {
        getHouses()
        setDeviceLocation(application)
    }

    /**
     * Gets houses from the server.
     */
    private fun getHouses() {

        viewModelScope.launch {

            status.postValue(HouseApiStatus.LOADING)

            try {

                application.repository.addAllHousesToDatabase(
                    application.repository.getHousesFromServer()
                )

                status.postValue(HouseApiStatus.SUCCESS)

            } catch (e: Exception) {

                status.postValue(HouseApiStatus.ERROR)
            }
        }
    }

    /**
     * Sets last known position of the device.
     * If the position is unavailable the coordinates will remain at the default value.
     */
    private fun setDeviceLocation(application: DttRealEstateApplication) {

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            location?.let {
                deviceLatitude = location.latitude
                deviceLongitude = location.longitude
            }
        }
    }

    /**
     * Sets a house object that will be used to display
     * the details of a house when a list item is clicked.
     */
    fun onHouseCardClicked(house: House) {
        currentHouse = house
    }

    /**
     * Inserts a house to the database with a changed bookmark state.
     */
    fun addBookmarkedHouseToDatabase(house: House) {
        viewModelScope.launch { application.repository.addHouseToDatabase(house) }
    }

    /**
     * Searches for houses with the provided value at the database and returns the result.
     */
    suspend fun search(searchValue: String): List<House> {

        val searchQuery = "%${searchValue.replace(" ", "")}%"

        return application.repository.searchAtDatabase(searchQuery)
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