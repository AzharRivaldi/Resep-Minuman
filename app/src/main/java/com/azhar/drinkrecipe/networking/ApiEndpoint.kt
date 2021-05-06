package com.azhar.drinkrecipe.networking

/**
 * Created by Azhar Rivaldi on 17-04-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * Linkedin : https://www.linkedin.com/in/azhar-rivaldi
 */

object ApiEndpoint {
    var BASEURL = "https://www.thecocktaildb.com/api/json/v1/1/"
    var URL_CATEGORIES = "list.php?c=list"
    var URL_FILTER = "filter.php?c={strCategory}"
    var URL_RECIPE = "lookup.php?i={idDrink}"
}