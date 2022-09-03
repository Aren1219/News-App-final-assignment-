package com.example.newsappfinalassignment.util

import com.example.newsappfinalassignment.model.Data

object Util {

    //"2022-09-03T14:36:55.000000Z" to "14:36-03-09-2022"
    fun formatDate(date: String): String
            = "${date.substring(11,16)}-${date.substring(8, 10)}-${date.substring(5, 7)}-${date.take(4)}"

    val previewNewsData = Data(
        listOf("A" ,"B"),
        "A primeira-ministra de Barbados mostrou-se confiante de que dentro de alguns meses seja possível concretizar \\\"o sonho\\\" de ter ligações aéreas diretas entre ...",
//    "/Users/arenwang/AndroidStudioProjects/NewsAppfinalassignment/app/src/main/res/drawable",
        "https://media-manager.noticiasaominuto.com/1280/naom_6312f6d20c8aa.jpg",
        "",
        "",
        "2022-09-03T07:41:34.000000Z",
        "Análise: Demorámos a adaptar-nos ao jogo do Sporting. Devíamos ter feito um pouco mais daquilo que fizemos na segunda parte, apertar um pouco mais com o Spor...",
        "noticiasaominuto.com",
        "Primeira-ministra de Barbados confiante em ligações diretas para África",
        "",
        "",
    )

    val previewNewsDataList = listOf<Data>(
        previewNewsData,
        previewNewsData,
        previewNewsData,
        previewNewsData,
        previewNewsData,
    )
}