package myredis

class AnalyticsData {

    String country
    Integer population
//    static mapWith = "redis"
    static mapping = {
        country index: true
    }
}
