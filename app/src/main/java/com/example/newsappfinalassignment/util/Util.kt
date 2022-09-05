package com.example.newsappfinalassignment.util

import com.example.newsappfinalassignment.model.Data
import java.util.*

object Util {

    //"2022-09-03T14:36:55.000000Z" to "14:36-03-09-2022"
    fun formatDate(date: String): String
            = "${date.substring(11,16)}-${date.substring(8, 10)}-${date.substring(5, 7)}-${date.take(4)}"

    //for testing and previews
    fun previewNewsData(uuid: String = "1") = Data(
        listOf("A" ,"B"),
        "A primeira-ministra de Barbados mostrou-se confiante de que dentro de alguns meses seja possível concretizar \\\"o sonho\\\" de ter ligações aéreas diretas entre ...",
//    "/Users/arenwang/AndroidStudioProjects/NewsAppfinalassignment/app/src/main/res/drawable",
        "https://media-manager.noticiasaominuto.com/1280/naom_6312f6d20c8aa.jpg",
        "general",
        "en",
        "2022-09-03T07:41:34.000000Z",
        "Análise: Demorámos a adaptar-nos ao jogo do Sporting. Devíamos ter feito um pouco mais daquilo que fizemos na segunda parte, apertar um pouco mais com o Spor...",
        "noticiasaominuto.com",
        "Primeira-ministra de Barbados confiante em ligações diretas para África",
        "www.v3ihf3498fhvbieqvb.com/waf3io4qjf40jv43/3fq34j",
        uuid,
    )

    fun previewNewsDataList(uuid: Int = 1, count: Int = 5): List<Data> {
        val list = mutableListOf<Data>()
        for (i in 1..count){
            list.add(previewNewsData((uuid + i - 1).toString()))
        }
        return list
    }
}