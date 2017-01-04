package myredis

import grails.plugin.redis.Memoize
import grails.plugin.redis.MemoizeDomainList
import grails.plugin.redis.RedisService

class GetDataService {
    static transactional = false
    def redisService
  /*  @Memoize(key = "#{analyticsData.country}",expire = "60000")
    def serviceMethod(country) {
    def data=AnalyticsData.list()
        return [analytics:data]
    }*/
}
