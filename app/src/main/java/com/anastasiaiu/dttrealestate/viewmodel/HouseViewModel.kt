package com.anastasiaiu.dttrealestate.viewmodel

import androidx.lifecycle.*
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.model.remote.HouseApiStatus
import com.anastasiaiu.dttrealestate.model.repository.HouseRepository
import kotlinx.coroutines.launch

/**
 * The [HouseViewModel] holds the status of the most recent request,
 * a list of house objects and a single house object
 * that will be used to display the details of an house when a list item is clicked.
 */
class HouseViewModel(
    private val houseRepository: HouseRepository
) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request.
    private val _status = MutableLiveData<HouseApiStatus>()

    // The external immutable LiveData for the request status.
    val status: LiveData<HouseApiStatus> = _status

    // The internal MutableLiveData that stores a list of house objects.
    private val _housesList = MutableLiveData<List<House>>()

    // The external immutable LiveData for the list of house objects.
    val housesList: LiveData<List<House>> = _housesList

    // The internal MutableLiveData for a single house object.
    // This will be used to display the details of an house when a list item is clicked.
    private val _house = MutableLiveData<House>()

    // The external immutable LiveData for the single house object.
    val house: LiveData<House> = _house

    init {
        getHouses()
    }

    fun getHouses() {

        viewModelScope.launch {

            _status.postValue(HouseApiStatus.LOADING)

            try {

                val data = houseRepository.getHouses()

                _housesList.postValue(data)

                _status.postValue(HouseApiStatus.SUCCESS)

            } catch (e: Exception) {

                _housesList.postValue(listOf())

                _status.postValue(HouseApiStatus.ERROR)
            }
        }
    }

    /**
     * Sets a house object that will be used to display
     * the details of a house when a list item is clicked.
     */
    fun onHouseCardClicked(house: House) {
        _house.value = house
    }
}

class HouseViewModelFactory(private val repository: HouseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HouseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HouseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}