package com.anastasiaiu.dttrealestate

import com.anastasiaiu.dttrealestate.model.remote.HouseService
import com.anastasiaiu.dttrealestate.model.remote.RetrofitService
import com.anastasiaiu.dttrealestate.view.House
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent {

    /*fun houseView(): House

    fun houseList(): List<House>*/
}

@Module
object AppModule {

    /*@Provides
    fun provideHouse(house: com.anastasiaiu.dttrealestate.model.remote.House): House {

        return House(
            house.id,
            house.image,
            house.price,
            house.bedrooms,
            house.bathrooms,
            house.size,
            house.description,
            house.zip,
            house.city,
            house.latitude,
            house.longitude,
            house.createdDate)
    }

    @Provides
    suspend fun provideHouseList(): List<House> {
        val retrofit = RetrofitService.retrofit
        val houseService = retrofit.create(HouseService::class.java)
        val remoteHouses = houseService.getHouses().houses
        val localHouses = mutableListOf<House>()

        remoteHouses.forEach {
            localHouses.add(
                House(
                    it.id,
                    it.image,
                    it.price,
                    it.bedrooms,
                    it.bathrooms,
                    it.size,
                    it.description,
                    it.zip,
                    it.city,
                    it.latitude,
                    it.longitude,
                    it.createdDate
                )
            )
        }

        return localHouses
    }*/
}