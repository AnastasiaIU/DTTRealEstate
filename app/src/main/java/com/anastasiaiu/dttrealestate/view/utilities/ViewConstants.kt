package com.anastasiaiu.dttrealestate.view.utilities

/**
 * View layer constants.
 */
object ViewConstants {

    /**
     * The permission request code.
     */
    const val REQUEST_CODE = 101

    /**
     * The constant for converting meters to kilometers.
     */
    const val M_TO_KM = 1000

    /**
     * The constant value represents no flags value for the soft input.
     */
    const val SOFT_INPUT_NO_FLAGS = 0

    /**
     * The delay for the search field.
     */
    const val SEARCH_FIELD_DELAY = 500L

    /**
     * Zoom value for the google map.
     */
    const val GOOGLE_MAP_ZOOM = 17.0f

    /**
     * Google maps package.
     */
    const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"

    /**
     * Google maps navigation string intent.
     */
    private const val GOOGLE_MAPS_NAVIGATION_INTENT = "google.navigation:"

    /**
     * Google maps query string parameter.
     */
    private const val GOOGLE_MAPS_QUERY_PARAMETER = "q="

    /**
     * Google maps query string separator.
     */
    private const val GOOGLE_MAPS_QUERY_SEPARATOR = ","

    /**
     * Returns Google maps navigation URI.
     */
    fun getGoogleNavigationUri(latitude: Double, longitude: Double): String {
        return GOOGLE_MAPS_NAVIGATION_INTENT +
                GOOGLE_MAPS_QUERY_PARAMETER +
                latitude +
                GOOGLE_MAPS_QUERY_SEPARATOR +
                longitude
    }
}